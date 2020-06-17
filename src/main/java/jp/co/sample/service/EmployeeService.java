package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービスクラスです.
 * 
 * @author ryosuke.nakanishi
 *
 */
@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得するメソッドです.
	 * 
	 * @return 従業員情報リスト
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}
	
	
	/**
	 * 従業員情報を一人分取得するメソッドです.
	 * 
	 * @param id
	 * 			取得したい従業員のid
	 * @return
	 * 			取得した従業員情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}
}
