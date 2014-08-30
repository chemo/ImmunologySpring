package com.immunology.logic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.immunology.logic.dao.SyndromeDao;
import com.immunology.logic.dao.UserDao;
import com.immunology.logic.service.SyndromeService;
import com.immunology.logic.utils.UserUtils;
import com.immunology.model.Syndrome;

@Service
public class SyndromeServiceImpl implements SyndromeService {

	@Autowired
	private SyndromeDao syndromeDao;
	
	@Autowired
	private UserDao userDao;
	
	public Syndrome getPatientSyndrome(Long patientId, String syndromeName) {
		Syndrome syndrome = syndromeDao.getPatientSyndrome(patientId, syndromeName);
		if(syndrome == null) {
			User user = UserUtils.getCurrentUser();
			syndrome = syndromeDao.getUserSyndromeTemplate(userDao.findByLogin(user.getUsername()).getId(), syndromeName);
		}
		return syndrome;
	}

	public Syndrome saveSyndrome(Syndrome syndrome) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getPatientSyndromeNames(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Syndrome> getUserSyndromeTemplates(Long userId) {
		return syndromeDao.getUserSyndromeTemplates(userId);
	}

	public List<String> getUserSyndromeTemplateNames(Long userId) {
		return syndromeDao.getUserSyndromeTemplatesNames(userId);
	}

}