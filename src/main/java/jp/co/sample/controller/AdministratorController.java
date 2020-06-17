package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@ModelAttribute
	private LoginForm setUpLoginForm () {
		return new LoginForm();
	}
	private InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@Autowired
	private AdministratorService administratorService;
	
	@RequestMapping("")
	public String toLogin() {
		return "/administrator/login";
	}
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "/administrator/insert";
	}
	
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		administratorService.insert(administrator);
		
		return "redirect:/";
	}
}
