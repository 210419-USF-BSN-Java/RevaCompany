package com.revature.daos;

import java.io.IOException;
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
	DepartmentPostgres dp = new DepartmentPostgres();

	@Override
	public Employee add(Employee t) {
		Employee employee = null;
		String sql = "insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values (?,?,?,?,?) returning empl_id;";
		
		try(Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			
			ResultSet rs = ps.executeQuery();
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				employee = t;
				employee.setId(rs.getInt(1));
			}
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public Employee getById(Integer id) {
		Employee em = new Employee();
		String sql = "select * from employees where empl_id = ?";
		try (Connection con = ConnectionUtil.getConnectionFromFile()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Employee manager = new Employee();
				manager.setId(rs.getInt("manager_id"));
				
				em.setId(id);
				em.setName(rs.getString("empl_name"));
				em.setMonthlySalary(rs.getFloat("monthly_salary"));
				em.setPosition(rs.getString("empl_position"));
				em.setManager(manager);
				em.setDepartment(dp.getById(rs.getInt("dept_id")));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return em;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		String sql = "select * from employees;";

		try {
			Connection c = ConnectionUtil.getConnectionFromFile();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				int empl_id = rs.getInt("empl_id");
				String empl_name = rs.getString("empl_name");
				String empl_position = rs.getString("empl_position");
				float monthly_salary = rs.getFloat("monthly_salary");
				Employee manager = new Employee();
				manager.setId(rs.getInt("manager_id"));
				Department department = dp.getById(rs.getInt("dept_id"));
				employees.add(new Employee(empl_id, empl_name, empl_position, monthly_salary, department, manager));
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public Integer update(Employee t) {
		String sql = "update employees set empl_name = ?, monthly_salary = ?, empl_position = ?, manager_id = ?, dept_id = ? where empl_id = ?";
		int a = -1;
		
		try(Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setFloat(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			ps.setInt(6, t.getId());
			
			a = ps.executeUpdate();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public Integer delete(Employee t) {
		String sql = "delete from employees where empl_id = ?";
		int a = -1;
		
		try(Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			
			a = ps.executeUpdate();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public List<Employee> getByName(String name) {
		String sql = "select * from employees where empl_name = ?";
		List<Employee> employees = new ArrayList<>();
		
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromFile().prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee manager = new Employee();
				manager.setId(rs.getInt("manager_id"));
				employees.add(new Employee(
								rs.getInt("empl_id"),
								rs.getString("empl_name"),
								rs.getString("empl_position"),
								rs.getFloat("monthly_salary"),
								dp.getById(rs.getInt("dept_id")),
								manager
							));
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employees;
	}
}