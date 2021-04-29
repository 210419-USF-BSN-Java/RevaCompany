package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Department;

import util.ConnectionUtil;

public class DepartmentPostgres implements DepartmentDao{

	@Override
	public Department add(Department t) {
		/*
		 * Department is being passed in
		 * Take in the fields
		 * add a record to the department table
		 * Insert into tableName (col1, col2) values(x,y);
		 * insert into departments (dept_name, monthly budget) values (t.getName(), t.getBudget());
		 * get connection
		 * create prepared statement from connection
		 * setting the variables
		 * execute
		 */
		Department department = null;
		String sql = "insert into departments (dept_name, monthly_budget) values (?,?) returning dept_id;";
//		String[] keys = {"dept_id"};
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,t.getName());
			ps.setDouble(2, t.getMonthlyBudget());
			
			ResultSet rs = ps.executeQuery();
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				department = t;
				department.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public Department getById(Integer id) 
	{
		//given an ID, return a department object
		return Department;
	}

	@Override
	public List<Department> getAll() {
		List<Department> departments = new ArrayList<>();
		String sql = "select * from departments;";

		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				int deptId = rs.getInt("dept_id");
				String deptName = rs.getString("dept_name");
				double budget = rs.getDouble("monthly_budget");
				departments.add(new Department(deptId, deptName, budget));
			}
		}	catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return departments;
	}

	@Override
	//update method by Rick Ricci
	public Integer update(Department t) 
	{	//initialize execution status variable
		int executionStatus = 0;
		//SQL script to add new department to the Departments table
		String newDeptRecord = "UPDATE departments SET(dept_name, monthly_budget) = (?, ?) WHERE dept_id = ?;";
		try(Connection con = ConnectionUtil.getConnectionFromEnv())
		{	//leverage DB connection utility singleton
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(newDeptRecord);
			//set fields in new department record
			ps.setString(1,t.getName());
			ps.setDouble(2,t.getMonthlyBudget());
			ps.setInt(3,t.getId());
			//get execution status
			executionStatus = ps.executeUpdate();
		}
		catch(SQLException e) 
		{	//check for more SQL exceptions
		    while(e != null) 
		    {   //return actionable/useful exception info
		    	System.out.println("Error: " + e.getErrorCode());
		    	System.out.println("Message:  " + e.getMessage());      
		    	System.out.println("State: " + e.getSQLState()); 
		    	//retrieve next SQL exception
		    	e = e.getNextException();
		    }
		}
		//return execution status; i.e., number of rows/records affected
		return executionStatus;
	}

	@Override
	//delete method edited by Rick Ricci
	public Integer delete(Department t)
	{	//initialize execution status variable
		int executionStatus = 0;
		//SQL script to delete a department from the Departments table
		String delDeptRecord = "DELETE FROM departments WHERE dept_id = ?";
		try
		{	//leverage DB connection utility singleton
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(delDeptRecord);
			//set fields in new department record
			ps.setInt(1,t.getId());
			//get execution status
			executionStatus = ps.executeUpdate();
		}
		catch(SQLException e) 
		{	//check for more SQL exceptions
		    while(e != null) 
		    {   //return actionable/useful exception info
		    	System.out.println("Error: " + e.getErrorCode());
		    	System.out.println("Message:  " + e.getMessage());      
		    	System.out.println("State: " + e.getSQLState()); 
		    	//retrieve next SQL exception
		    	e = e.getNextException();
		    }
		}
		//return execution status; i.e., number of rows/records affected
		return executionStatus;
	}

	@Override
	public List<Department> getDepartmentsByMonthlyBudget(Double budget) {
		String sql = "SELECT * FROM departments where monthly_budget = ?";
		List<Department> departments = new ArrayList<>();
		
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setDouble(1, budget);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				departments.add(new Department(rs.getInt("dept_id"), rs.getString("dept_name"), rs.getDouble("monthly_budget")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return departments;
	}

}