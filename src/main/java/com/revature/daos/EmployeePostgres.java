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

public class EmployeePostgres implements EmployeeDao
{

	@Override
	//method by Rick Ricci
	public Employee add(Employee t) 
	{
		Employee employee = null;
		String newEmployee = "INSERT into EMPLOYEES (empl_name, monthly_salary, empl_position, manager_id, dept_id) VALUES (?, ?, ?, ?, ?) WHERE empl_id = ?;";
		String[] keys = {"empl_id"};
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv())
		{
			PreparedStatement ps = con.prepareStatement(newEmployee);
			ps.setString(1,t.getName());
			ps.setFloat(2,t.getMonthlySalary());
			ps.setString(3,t.getPosition());
			ps.setInt(4,t.getManager().getId());
			ps.setInt(5,t.getDepartment().getId());
			ps.setInt(6,t.getId());
			//initialize and generate result set for executed SQL query 
			ResultSet rs = ps.executeQuery();
			//confirm record was added
			if(rs.next()) 
			{
				employee = t;
				employee.setId(rs.getInt(1));
			}
			
		} 
		catch(SQLException e) 
		{	//check for more SQL exceptions
		    	while(e != null) 
			{   	//return actionable/useful exception info
				System.out.println("Error: " + e.getErrorCode());
				System.out.println("Message:  " + e.getMessage());      
				System.out.println("State: " + e.getSQLState()); 
				//retrieve next SQL exception
				e = e.getNextException();
			}
		}
		return employee;
	}

	@Override
	//method by Rick Ricci
	public Employee getById(Integer id) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//method by Rick Ricci
	public List<Employee> getAll() 
	{
		//initialize arraylist for employee objects
		List<Employee> employees = new ArrayList<>();
		//SQL script to get all employees from the employee table
		String sql = "SELECT * FROM employees;";
		try //gremlin's favorite word 
		{	//leverage DB connection utility singleton
			Connection c = ConnectionUtil.getConnectionFromEnv();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			//iterate through the employee records and load the arraylist
			while(rs.next()) 
			{
				//get employee object from result set
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("emp_name");
				float emplSal = rs.getFloat("monthly_salary");
				String emplPos = rs.getString("empl_position");
				Department emplDept = DepartmentPostgres.getById(rs.getInt("dept_id"));
				Employee mngrId = getById(rs.getInt("manager_id"));
				//add employee object to the arraylist
				employees.add(new Employee(emplId, emplName, emplPos, emplSal, emplDept, mngrId));
			}
		}
		catch(SQLException e) 
		{	//check for more SQL exceptions
		    	while(e != null) 
			{   	//return actionable/useful exception info
				System.out.println("Error: " + e.getErrorCode());
				System.out.println("Message:  " + e.getMessage());      
				System.out.println("State: " + e.getSQLState()); 
				//retrieve next SQL exception
				e = e.getNextException();
			}
		}
		//return the list of all employees
		return employees;
	}

	@Override
	//method by Rick Ricci
	public Integer update(Employee t) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//method by Rick Ricci
	public Integer delete(Employee t) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//method by Rick Ricci
	public Employee getByName(String name) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
