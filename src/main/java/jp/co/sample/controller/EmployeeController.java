package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を扱うためのコントローラーです.
 * 
 * @author ryosuke.nakanishi
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@ModelAttribute
	private UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 従業員一覧を表示するためのメソッドです.
	 * 
	 * @param model
	 * 				従業員一覧を遷移先で表示するためのリクエストスコープです
	 * @return
	 * 				従業員一覧表示画面へフォワードします
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		
		model.addAttribute("employeeList", employeeList);
		
		return "employee/list";
	}
	
	/**
	 * 従業員情報の詳細を表示するためのメソッドです.
	 * 
	 * @param id
	 * 				詳細を表示したい従業員のid
	 * @param model
	 * 				表示する従業員の情報を遷移先で表示するためのリクエストスコープ
	 * @return
	 * 				詳細表示画面へフォワード
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		
		model.addAttribute("employee", employee);
		
		return "employee/detail";
	}
	
	/**
	 * 従業員の扶養人数を更新するメソッドです.
	 * 
	 * @param form
	 * 				更新したい従業員のidと更新後の扶養人数が含まれるフォームです
	 * @return
	 * 				従業員一覧のページへリダイレクトします
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		
		employeeService.update(employee);
		
		return "redirect:/employee/showList";
	}
}
