package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;


/**
 * 管理者情報を操作するコントローラです.
 * 
 * @author ryosuke.nakanishi
 *
 */


 
@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@ModelAttribute
	private LoginForm setUpLoginForm () {
		return new LoginForm();
	}
	@ModelAttribute
	private InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;
	
	
	/**
	 * ログイン画面を表示するメソッドです.
	 * 
	 * @return ログイン画面へフォワードする
	 */
	@RequestMapping("")
	public String toLogin() {
		return "/administrator/login";
	}
	
	/**
	 * ログイン処理を行うメソッドです.
	 * 
	 * @param form
	 * 				ログイン画面からの入力
	 * @param model
	 * 				エラーメッセージを格納するリクエストスコープ
	 * 
	 * @return
	 * 				入力情報が管理者情報と一致しない場合: ログイン画面へフォワード
	 * 				入力情報が管理者情報と一致した場合: 従業員一覧の画面へフォワード
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		
		if (administrator == null) {
			model.addAttribute("error", "メールアドレスまたはパスワードが不正です。");
			return "/administrator/login";
		}
		
		session.setAttribute("administratorName", administrator.getName());
		
		return "forward:/employee/showList";
	}
	
	/**
	 * 管理者登録画面を表示するメソッドです.
	 * 
	 * @return 管理者登録画面へフォワード
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "/administrator/insert";
	}
	
	/**
	 * 管理者登録を行うメソッドです.
	 * 
	 * @param form
	 * 				登録する管理者情報が含まれるフォームです。
	 * @return
	 * 				ログイン画面へリダイレクトします。
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		administratorService.insert(administrator);
		
		return "redirect:/";
	}
	
	/**
	 * ログアウト処理を行うためのメソッドです.<br>
	 * セッションを破棄し、ログイン画面へ遷移します。
	 * 
	 * @return
	 * 				ログイン画面へリダイレクトします
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		
		return "redirect:/";
	}
}
