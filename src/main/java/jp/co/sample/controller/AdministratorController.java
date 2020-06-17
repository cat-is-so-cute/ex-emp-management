package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {
	@ModelAttribute
	private InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@Autowired
	private AdministratorService administratorService;
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "/administrator/insert";
	}
	
	
//	@RequestMapping("/toInsert")
//	public String insert(InsertAdministratorForm form) {
//		Administrator administrator = new Administrator();
//		BeanUtils.copyProperties(form, administrator);
//		
//		administratorService.insert(administrator);
//		
//		return "login";
//	}
}
