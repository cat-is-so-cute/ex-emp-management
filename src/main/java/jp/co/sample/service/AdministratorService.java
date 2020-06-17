package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;



/**
 * Administratorの業務処理を行うサービスクラスです.
 * @author ryosuke.nakanishi
 *
 */
@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;
	
	/**
	 * 管理者情報を登録するメソッドです.
	 *
	 * @param administrator
	 * 						管理者情報
	 */
	public void insert (Administrator administrator) {
		administratorRepository.insert(administrator);
	}

	/**
	 * ログイン処理を行うメソッドです.<br>
	 * 
	 * @return 照合結果
	 * 				メールアドレスとパスワードが一致した場合は管理者情報が、一致しない場合はNULLが返ります
	 */
	public Administrator login(String mailAddress, String password) {
		return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
	}
}
