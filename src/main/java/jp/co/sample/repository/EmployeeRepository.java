package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZip_code(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		
		return employee;
	};
	
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees ORDER BY hire_date;";
		
		return template.query(sql, EMPLOYEE_ROW_MAPPER);
	}
	
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id=:id;";
		
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
	}
	
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
