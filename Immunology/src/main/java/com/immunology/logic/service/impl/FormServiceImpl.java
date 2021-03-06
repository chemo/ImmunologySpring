package com.immunology.logic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immunology.logic.dao.CrudDao;
import com.immunology.logic.dao.SurveyDao;
import com.immunology.logic.service.FormServive;
import com.immunology.model.Survey;
import com.immunology.model.ui.Form;

@Service
public class FormServiceImpl implements FormServive {

	@Autowired
	private SurveyDao surveyDao;
	
	@Autowired
	private CrudDao crudDao;

	public Form createForm(Form form) {
		return crudDao.create(form);
	}

	public Form updateForm(Form form) {
		return crudDao.saveOrUpdate(form);
	}

	public List<Form> getAllForms() {
		return crudDao.getAll(Form.class);
	}

	public Form getFormById(long formId) {
		return crudDao.find(Form.class, formId);
	}
	
	public void saveTemplate(Survey survey) {
		surveyDao.createSurveyTemplate(survey);
	}
	
	public List<Survey> getUserSurveyTemplates(long id) {
		return surveyDao.getSurveyTemplatesByUserId(id);
	}
}
