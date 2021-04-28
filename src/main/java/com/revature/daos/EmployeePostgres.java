package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.models.Employee;

import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao {

	@Override
	public Employee add(Employee t) {
		Employee employee = null;
		String sql = "insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values(?, ?, ?, ?, ?) returning dept_id;";

		try (Connection con = ConnectionUtil.getHardCodedConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				employee = t;
				employee.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public Employee getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
