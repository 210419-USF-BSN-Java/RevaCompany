package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Department;
import com.revature.models.Employee;

import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao {
	DepartmentPostgres dep = new DepartmentPostgres();
	
	@Override
	public Employee add(Employee t) {
		Employee e = null;
		String sql = "Insert into employee (empl_name, monthly_salary, empl_position, manager_id, dept_id) values (?,?,?,?,?)";
		try(Connection c = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			ps.execute();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return e;
	}

	@Override
	public Employee getById(Integer id) {
		Employee man = new Employee();
		String sql = "Select * from employees where empl_id = ?";
		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				Employee manager = new Employee();
				
				manager.setId(rs.getInt("manager_id"));
				
				man.setId(id);
				man.setName(rs.getString("empl_name"));
				man.setMonthlySalary(rs.getFloat("monthly_budget"));
				man.setPosition(rs.getString("empl_position"));
				man.setManager(manager);
				man.setDepartment(dep.getById(rs.getInt("dep_id")));
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return man;
	}
	
	@Override
	public List<Employee> getAll() {
		List <Employee> emp = new ArrayList<>();
		String sql ="select * from employees;";
		try (Connection con = ConnectionUtil.getConnectionFromEnv();){
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				int emplid = rs.getInt("empl_id");
				String name = rs.getString("empl_name");
				String pos = rs.getString("empl_position");
				float money = rs.getFloat("monthly_salary");
				Employee man = new Employee();
				man.setId(rs.getInt("manager_id"));
				Department depart = dep.getById(rs.getInt("dept_id"));
				emp.add(new Employee(emplid, name, pos, money, depart, man));
				
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return emp;
	}

	@Override
	public Integer update(Employee t) {
		String sql = "update Employees set empl_name = ?, monthly_salary = ?, empl_position = ?, manager_id = ?, dept_id =? where empl_id = ?;";
		int e = 0;
		try (Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1,t.getId());
			e = ps.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return e;
	}

	@Override
	public Integer delete(Employee t) {
		int emp = 0;
		String sql ="select e.empl_id, e.empl_name empl_name, "
				+ "e.monthly_salary monthly_salary,"
				+ "e.empl_position empl_position,"
				+ "e.manager_id manager_id,"
				+ "e.dept_id dept_id,"
				+ "m.empl_name manager,"
				+ "d.dept_name dept_name, "
				+ "d.monthly_budget monthly_budget"
				+ "from employees e"
				+ "join employees m "
				+ "on e.manager_id = m.manager_id"
				+ "join departments d"
				+ "on e.dept_id = d.dept_id where e.empl_id = ?;";
		try(Connection c = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, t.getId());
			emp = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public Employee getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
