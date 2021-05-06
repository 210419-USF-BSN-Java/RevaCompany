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

public class EmployeePostgres implements EmployeeDao {

	@Override
	public Employee add(Employee t) {
		String sql = "INSERT INTO test.employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) VALUES (?,?,?,?,?) RETURNING empl_id;";
		Employee emp = null;

		try (Connection con = ConnectionUtil.getConnectionH2()) {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setFloat(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				emp = t;
				emp.setId(rs.getInt("empl_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public Employee getById(Integer id) {
		Employee emp = new Employee();
		String sql = "SELECT * FROM test.employees e INNER JOIN test.departments d ON e.dept_id = d.dept_id WHERE e.empl_id = ?";
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionH2().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Employee boss = new Employee();
				Department dep = new Department();
				boss.setId(rs.getInt("manager_id"));
				dep.setId(rs.getInt("dept_id"));
				dep.setName(rs.getString("dept_name"));
				dep.setMonthlyBudget(rs.getDouble("monthly_budget"));

				emp.setId(id);
				emp.setName(rs.getString("empl_name"));
				emp.setMonthlySalary(rs.getFloat("monthly_salary"));
				emp.setPosition(rs.getString("empl_position"));
				emp.setDepartment(dep);
				emp.setManager(boss);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> emplist = new ArrayList<>();
		String sql = "SELECT * from test.employees e INNER JOIN test.departments d ON e.dept_id = d.dept_id";

		try {
			PreparedStatement ps = ConnectionUtil.getConnectionH2().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int deptId = rs.getInt("dept_id");
				String deptName = rs.getString("dept_name");
				double deptBudget = rs.getDouble("monthly_budget");

				int empId = rs.getInt("empl_id");
				String empName = rs.getString("empl_name");
				float empSalary = rs.getFloat("monthly_salary");
				String empPosition = rs.getString("empl_position");
				Employee mana = new Employee();
				mana.setId(rs.getInt("manager_id"));
				emplist.add(new Employee(empId, empName, empPosition, empSalary,
						(new Department(deptId, deptName, deptBudget)), mana));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emplist;
	}

	@Override
	public Integer update(Employee t) {
		Integer result = null;
		String sql = "UPDATE test.employees SET (empl_name, empl_position, monthly_salary, dept_id, manager_id) = (?,?,?,?,?) WHERE empl_id = ?";

		try {
			PreparedStatement ps = ConnectionUtil.getConnectionH2().prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setString(2, t.getPosition());
			ps.setFloat(3, t.getMonthlySalary());
			ps.setInt(4, t.getDepartment().getId());
			ps.setInt(5, t.getManager().getId());
			ps.setInt(6, t.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Integer delete(Employee t) {
		Integer result = null;
		String sql = "DELETE FROM test.employees where empl_id = ?";

		try {
			PreparedStatement ps = ConnectionUtil.getConnectionH2().prepareStatement(sql);

			ps.setInt(1, t.getId());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Employee getByName(String name) {
		Employee emp = new Employee();
		String sql = "SELECT * FROM test.employees e INNER JOIN test.departments d ON e.dept_id = d.dept_id WHERE e.empl_name = ?";
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionH2().prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Employee boss = new Employee();
				Department dep = new Department();
				boss.setId(rs.getInt("manager_id"));
				dep.setId(rs.getInt("dept_id"));
				dep.setName(rs.getString("dept_name"));
				dep.setMonthlyBudget(rs.getDouble("monthly_budget"));

				emp.setId(rs.getInt("empl_id"));
				emp.setName(rs.getString(name));
				emp.setMonthlySalary(rs.getFloat("monthly_salary"));
				emp.setPosition(rs.getString("empl_position"));
				emp.setDepartment(dep);
				emp.setManager(boss);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}

}
