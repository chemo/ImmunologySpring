package com.immunology.logic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.immunology.logic.service.PatientService;
import com.immunology.logic.service.SyndromeService;
import com.immunology.logic.service.UserService;
import com.immunology.logic.service.calculation.impl.SurveyCalculatorService;
import com.immunology.logic.utils.URIUtils;
import com.immunology.logic.utils.UserUtils;
import com.immunology.logic.utils.calculation.CalculationHelper;
import com.immunology.logic.utils.enums.FormulaType;
import com.immunology.model.Patient;
import com.immunology.model.Survey;
import com.immunology.model.Syndrome;
import com.immunology.model.calculation.Formula;

@Controller
@RequestMapping(value = "/syndromes")
public class SyndromeController {

	private static final Logger LOG = LoggerFactory.getLogger(SyndromeController.class);
	
	@Autowired
	private SyndromeService syndromeService;
	@Autowired
	private UserService userService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private SurveyCalculatorService surveyCalculatorService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Syndrome> getUserSyndromes() {
		User user = UserUtils.getCurrentUser();
		return syndromeService.getUserSyndromeTemplates(userService.getUserByLogin(user.getUsername()).getId());
	}
	
	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET)
	public @ResponseBody List<String> getPatientSyndromes(@PathVariable("id") Long id) {
		LOG.info("getPatientSyndromes");
		return syndromeService.getPatientSyndromeNames(id);
	}
	
	@RequestMapping(value = "/patient/{id}/{name}", method = RequestMethod.GET)
	public @ResponseBody Syndrome getPatientSyndrome(@PathVariable("id") Long id,  @PathVariable("name") String syndromeName, HttpServletRequest request, Model model) {
		
		LOG.info("getPatientSyndrome");
		model.addAttribute("surveyId", null);
		model.addAttribute("survey", null);
		
		Syndrome syndrome = syndromeService.getPatientSyndrome(id, URIUtils.decodePathVariable(request.getRequestURI(), 3));
		Survey surveyTemplate = syndromeService.getSyndromeByName(syndrome.getName()).getSurveys().get(0);
		
		for(Survey survey: syndrome.getSurveys()) {
			surveyCalculatorService.calculateSurveyValues(survey, surveyTemplate, syndrome.getName());
		}
		
		return syndrome;
	}
	
	@RequestMapping(value = "/patient/{id}", method = RequestMethod.POST)
	public @ResponseBody Boolean savePatientSyndrome(@PathVariable("id") Long id, @RequestBody Syndrome syndrome) {
		LOG.info("savePatientSyndrome");
		Patient patient = patientService.getPatientById(id);
		syndrome.setPatient(patient);
		syndrome = syndromeService.saveSyndrome(syndrome);
		return syndrome != null ? true : false;
	}
	
	@RequestMapping(value = "/patient/{patientId}/syndrome/{syndromeId}", method = RequestMethod.POST)
	public @ResponseBody Boolean updatePatientSyndrome(@PathVariable("patientId") Long patientId, 
			@PathVariable("syndromeId") Long syndromeId, @RequestBody Syndrome syndrome) {
		LOG.info("updatePatientSyndrome");
		Patient patient = patientService.getPatientById(patientId);
		syndrome.setPatient(patient);
		syndrome = syndromeService.saveSyndrome(syndrome);
		return syndrome != null ? true : false;
	}
	
	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public @ResponseBody List<String> getSyndromesNames() {
		LOG.info("get syndromes names");
		List<String> names = syndromeService.getSyndromeNames(); 
		return names;
	}
	
	@RequestMapping(value = "/template", method = RequestMethod.POST)
	public @ResponseBody Boolean saveSyndromeTemplate(@RequestBody Syndrome syndrome) {
		return syndromeService.saveSyndromeTemplate(syndrome);
	}
	
	@RequestMapping(value = "/template/{name}", method = RequestMethod.POST)
	public @ResponseBody Boolean updateSyndromeTemplate(@PathVariable("name") String name, @RequestBody Syndrome syndrome, HttpServletRequest request) {
		return syndromeService.updateSyndromeTemplate(URIUtils.decodePathVariable(request.getRequestURI(), 2), syndrome);
	}
	
	@RequestMapping(value = "/template/{name}/user/{id}", method = RequestMethod.POST)
	public @ResponseBody Boolean wireUserToSyndromeTemplate(@PathVariable("name") String syndromeName, @PathVariable("id") Long userId, HttpServletRequest request) {
		return syndromeService.wireUserToSyndromeTemplate(URIUtils.decodePathVariable(request.getRequestURI(), 2), userId);
	}
	
	@RequestMapping(value = "template/{name}", method = RequestMethod.GET)
	public @ResponseBody Syndrome getSyndromeByName(@PathVariable("name") String templateName, HttpServletRequest request) {
		return syndromeService.getSyndromeByName(URIUtils.decodePathVariable(request.getRequestURI(), 2));
	}

	@RequestMapping(value = "/template/{name}/user/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean removeSyndromeTemplateFromUser(@PathVariable("name") String syndromeName, @PathVariable("id") Long userId, HttpServletRequest request) {
		LOG.info("Removing user id {} from {}", userId, syndromeName);
		syndromeService.removeSyndromeTemplateFromUser(URIUtils.decodePathVariable(request.getRequestURI(), 2), userId);
		return true;
	}
	
	@RequestMapping(value = "/template/{name}/{formulaType}", method = RequestMethod.GET)
	public @ResponseBody String getSyndromeFormula(@PathVariable("formulaType") String formulaType, HttpServletRequest request) {
		String decodedSyndromeName = URIUtils.decodePathVariable(request.getRequestURI(), 2);
		Formula result = syndromeService.getSyndromeFormula(decodedSyndromeName, FormulaType.getByName(formulaType));
		if(result != null) {
			return result.getFormulaExpression();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/template/{name}/{formulaType}", method = RequestMethod.POST)
	public @ResponseBody Boolean saveSyndromeFormula(@RequestParam("formula") String formula, @PathVariable("formulaType") String formulaType, HttpServletRequest request) {
		boolean result = false;
		if(CalculationHelper.validateFormula(formula)) {
			String decodedSyndromeName = URIUtils.decodePathVariable(request.getRequestURI(), 2);
			syndromeService.saveSyndromeFormula(decodedSyndromeName, FormulaType.getByName(formulaType), formula);
			result = true;
		}
		return result;
	}

	@RequestMapping(value = "/template/{name}/drug", method = RequestMethod.POST)
	public @ResponseBody Boolean wireDrugToSyndromeTemplate(@RequestParam(value="type") String drugType, @RequestParam(value="species") String drugSpecies, @RequestParam(value="name") String drugName, HttpServletRequest request) {
		String syndromeName = URIUtils.decodePathVariable(request.getRequestURI(), 2);
		return syndromeService.wireDrugToSyndromeTemplate(syndromeName, drugType, drugSpecies, drugName);
	}
}
