package com.immunology.logic.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.immunology.logic.utils.RoleUtils;
import com.immunology.logic.utils.UserUtils;

@Controller
public class StartPageController {

	@RequestMapping(value = "/index")
	public String getIndex() {
		User user = UserUtils.getCurrentUser();
		if(RoleUtils.isAdmin(user))
			return "redirect:admin";
		else {
			return "redirect:cabinet";
		}
	}
	
}
