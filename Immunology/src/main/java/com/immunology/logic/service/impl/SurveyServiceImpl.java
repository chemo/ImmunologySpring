package com.immunology.logic.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.immunology.logic.dao.CrudDao;
import com.immunology.logic.service.SurveyService;
import com.immunology.model.Survey;

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private CrudDao crudDao;
	
	public Survey saveOrUpdateSurvey(Survey survey) {
		if(survey.getId() == 0) {
			survey.setCreationDate(new Date());
		}
		return crudDao.saveOrUpdate(survey);
	}

}