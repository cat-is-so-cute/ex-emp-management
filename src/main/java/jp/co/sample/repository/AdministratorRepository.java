package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
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
	
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO administrators VALUES (:name, :mail_address, :password);";
		
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("name", administrator.getName())
																 .addValue("mail_address", administrator.getMailAddress())
																 .addValue("password", administrator.getPassword());
		
		template.update(sql, param);
	}
	
	
}
