package com.revature.services;
import java.util.List;
import com.revature.models.Employee;

public interface EmployeeService {
    
    public Employee getByName(String name);
    public Integer addEmployee(Employee e);
	public Employee getEmployeeById(Integer id);
	public List<Employee> getEmployees();
	public boolean updateEmployee(Employee e);
	public boolean deleteEmployee(Employee e);

}
