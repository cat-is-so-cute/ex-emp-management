package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員情報を操作するレポジトリです.
 * 
 * @author ryosuke.nakanishi
 *
 */
@Repository
public class EmployeeRepository {
	/**
	 * Employee型のRowMapperを定義します.
	 *
	 */	
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		
		return employee;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 全件検索を行うメソッドです.<br>
	 * 入社日の降順で並び替えられます。
	 * 
	 * @return 検索結果のリスト(従業員が存在しない場合はサイズ0のリストが返る)
	 */		
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC;";
		
		return template.query(sql, EMPLOYEE_ROW_MAPPER);
	}
	
	/**
	 * 主キー検索を行うメソッドです.
	 * 
	 * @param id
	 * 				主キー
	 * 
	 * @return 検索結果(従業員が存在しない場合は例外が発生します)
	 */		
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id=:id;";
		
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
	}
	
	/**
	 * 従業員情報を変更するメソッドです.
	 * 
	 * @param employee
	 * 					従業員情報
	 */			
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE employees SET ");
		sql.append("name=:name ");
		sql.append("image=:image ");
		sql.append("gender=:gender ");
		sql.append("hire_date=:hireDate ");
		sql.append("mail_address=:mailAddress ");
		sql.append("zip_code=:zipCode ");
		sql.append("address=:address ");
		sql.append("telephone=:telephone ");
		sql.append("salary=:salary ");
		sql.append("characteristics=:characteristics ");
		sql.append("dependents_count=:dependentsCount ");
		sql.append("WHERE id=:id");
		
		template.update(sql.toString(), param);
	}
}
