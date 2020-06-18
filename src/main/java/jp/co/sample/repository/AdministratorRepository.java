package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;


/**
 * 管理者情報を操作するレポジトリです.
 * 
 * @author ryosuke.nakanishi
 *
 */

@Repository
public class AdministratorRepository {

	/**
	 * administrator型のRowMapperを定義します.
	 *
	 */
	public static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		
		return administrator;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 管理者情報を登録するメソッドです.
	 * 
	 * @param administrator
	 * 						登録する管理者情報
	 */	
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mail_address, :password);";
		
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("name", administrator.getName())
																 .addValue("mail_address", administrator.getMailAddress())
																 .addValue("password", administrator.getPassword());
		
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得するメソッドです.
	 * 
	 * @param mailAddress
	 * 						メールアドレス
	 * @param password
	 * 						パスワード
	 * @return 管理者情報
	 */		
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address=:mailAddress AND password=:password;";
		
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress)
																 .addValue("password", password);
		
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		
		if (administratorList.size() == 0) {
			return null;
		}
		
		return administratorList.get(0);
	}
	
	
}
