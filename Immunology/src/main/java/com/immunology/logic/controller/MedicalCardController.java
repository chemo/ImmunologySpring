package com.immunology.logic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.immunology.logic.service.FormServive;
import com.immunology.logic.service.MedicalCardFormService;
import com.immunology.model.Patient;
import com.immunology.model.ui.Form;
import com.immunology.model.ui.MedicalCardForm;

@Controller
@RequestMapping(value = "/patients")
public class MedicalCardController {
	private static final Logger LOG = LoggerFactory.getLogger(MedicalCardController.class);

	@Autowired
	private FormServive formServive;
	@Autowired
	private MedicalCardFormService medicalCardService;
	
	@RequestMapping(value = "/{id}/medical_card", method = RequestMethod.GET)
	public @ResponseBody MedicalCardForm getMedicalForm(@PathVariable("id") long id) throws JsonProcessingException {
		MedicalCardForm form = medicalCardService.getMedicalCardByPatientId(id);
		return form;
	}

	@RequestMapping(value = "/medical_card/update", method = RequestMethod.POST)
	public @ResponseBody Form updateMedicalCard(@RequestBody MedicalCardForm medicalCard) {
		LOG.info("Updating\n" + medicalCard);
		Patient patient = medicalCardService.getById(medicalCard.getId()).getPatient();
		medicalCard.setPatient(patient);
		return formServive.updateForm(medicalCard);
	}
	
	@RequestMapping(value = "/medical_card", method = RequestMethod.POST)
	public @ResponseBody boolean saveMedicaldCard(@RequestBody MedicalCardForm medicalCardForm){
		LOG.info("Saving\n" + medicalCardForm.toString());
		return medicalCardService.updateMedicalCardTemplate(medicalCardForm);
	}

}