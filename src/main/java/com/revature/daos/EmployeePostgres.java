package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Department;
import com.revature.models.Employee;

import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao{

	@Override
	public Employee add(Employee t) {
		Employee employee = null;
		String sql = "insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values (?,?,?,default,default) returning empl_id;";
		String[] keys = {"empl_id"};
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				employee = t;
				employee.setId(rs.getInt(1)); //generates an id for passed in object
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public Employee getById(Integer id) {
		Employee empl = null;
		String sql = "select * from employees where empl_id = ?";
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("empl_name");
				float salary = rs.getFloat("monthly_salary");
				String position = rs.getString("empl_position");
				
				empl = new Employee(emplId, emplName, position, salary, null, null);
			}
					
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return empl;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> emplList = new ArrayList<>();
		String sql = "select * from employees";
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("empl_name");
				float salary = rs.getFloat("monthly_salary");
				String position = rs.getString("empl_position");
				
				emplList.add(new Employee(emplId, emplName, position, salary, null, null));
			}
					
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return emplList;
	}

	@Override
	public Integer update(Employee t) {
		Integer result = 0;
		String sql = "update employees set monthly_salary = ? where empl_id = ?";
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, t.getMonthlySalary());
			ps.setInt(2, t.getId());
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Integer delete(Employee t) {
		Integer result = 0;
		String sql = "delete from employees where empl_name = ?";
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			//ps.setInt(1, t.getId());
			ps.setString(1, t.getName());
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Employee getByName(String name) {
		Employee empl = null;
		String sql = "select * from employees where empl_name = ?";
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("empl_name");
				float salary = rs.getFloat("monthly_salary");
				String position = rs.getString("empl_position");
				
				empl = new Employee(emplId, emplName, position, salary, null, null);
			}
					
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return empl;
	}

}
