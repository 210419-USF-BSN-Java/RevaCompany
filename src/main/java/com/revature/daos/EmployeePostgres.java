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

	@Override
	public Employee add(Employee t) {
		Employee employee = null;
		String sql = "INSERT INTO public.employees(" + "empl_name, monthly_salary, empl_position,"
				+ " manager_id, dept_id)" + "	VALUES (?, ?, ?, ?, ?)returning empl_id;";
		String[] keys = { "empl_id" };

		Employee manager = t.getManager();
		int managerId = manager.getId();

		Department dept = t.getDepartment();
		int deptId = dept.getId();

		try (Connection con = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = con.prepareStatement(sql, keys);
//			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, managerId);
			ps.setInt(5, deptId);

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				employee = t;
				employee.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;

	}

	@Override
	public Employee getById(Integer id) {
		DepartmentDao deptDao = new DepartmentPostgres();

		String sql = "select * from employee where empl_id = ?";
		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery(sql);
			if (rs.next()) {
				String name = rs.getString("empl_name");
				String position = rs.getString("empl_position");
				Float monthlySalary = rs.getFloat("monthly_salary");
				int managerId = rs.getInt("manager_id");
				int deptId = rs.getInt("dept_id");

				Employee manager = getById(managerId);
				Department dept = deptDao.getById(deptId);

				Employee employee = new Employee();
				employee.setId(id);
				employee.setName(name);
				employee.setPosition(position);
				employee.setMonthlySalary(monthlySalary);
				employee.setManager(manager);
				employee.setDepartment(dept);
				return employee;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> getAll() {

		List<Employee> employeeList = new ArrayList<>();
		DepartmentDao deptDao = new DepartmentPostgres();

		String sql = "select * from employee";
		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("empl_id");
				String name = rs.getString("empl_name");
				String position = rs.getString("empl_position");
				Float monthlySalary = rs.getFloat("monthly_salary");
				int managerId = rs.getInt("manager_id");
				int deptId = rs.getInt("dept_id");

				Employee manager = getById(managerId);
				Department dept = deptDao.getById(deptId);

				Employee employee = new Employee();
				employee.setId(id);
				employee.setName(name);
				employee.setPosition(position);
				employee.setMonthlySalary(monthlySalary);
				employee.setManager(manager);
				employee.setDepartment(dept);

				employeeList.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return employeeList;
	}

	@Override
	public Integer update(Employee t) {
		String sql = "UPDATE public.employees SET " + " empl_name = ?, monthly_salary = ?," + " empl_position = ?,"
				+ " manager_id = ?, dept_id = ? WHERE empl_id = ?";

		Employee manager = t.getManager();
		int managerId = manager.getId();

		Department dept = t.getDepartment();
		int deptId = dept.getId();

		try (Connection con = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, managerId);
			ps.setInt(5, deptId);
			ps.setInt(6, t.getId());

			int count = ps.executeUpdate();

			return count;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Integer delete(Employee t) {
		String sql = "DELETE FROM public.employees  " + " WHERE empl_id = ?";

		try (Connection con = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, t.getId());

			int count = ps.executeUpdate();

			return count;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Employee getByName(String name) {
		DepartmentDao deptDao = new DepartmentPostgres();

		String sql = "select * from employee where empl_name = ?";
		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("empl_id");
				String position = rs.getString("empl_position");
				Float monthlySalary = rs.getFloat("monthly_salary");
				int managerId = rs.getInt("manager_id");
				int deptId = rs.getInt("dept_id");

				Employee manager = getById(managerId);
				Department dept = deptDao.getById(deptId);

				Employee employee = new Employee();
				employee.setId(id);
				employee.setName(name);
				employee.setPosition(position);
				employee.setMonthlySalary(monthlySalary);
				employee.setManager(manager);
				employee.setDepartment(dept);
				return employee;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
