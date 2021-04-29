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
		Employee employee = null;
		String sql = "insert into revcomp_schema.employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values (?,?,?,?,?) returning empl_id;";
//		String sql = "insert into departments (dept_name, monthly_budget) values (?,?);";
		String[] keys = { "dept_id" };

		try (Connection con = ConnectionUtil.getConnectionFromEnv()) {
//			PreparedStatement ps = con.prepareStatement(sql,keys);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());

			ResultSet rs = ps.executeQuery();
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();

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
		// TODO Auto-generated method stub
		Employee employee = null;
		String sql = "select * from revcomp_schema.employees where empl_id = ?;";

		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			// empl_name, monthly_salary, empl_position, manager_id, dept_id
			if (rs.next()) {
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("empl_name");
				float salary = rs.getFloat("monthly_salary");
				String position = rs.getString("empl_position");
				int manager_id = rs.getInt("manager_id");
				int dept_id = rs.getInt("dept_id");

				// get the department_id
				DepartmentPostgres deptP = new DepartmentPostgres();
				Department dept = deptP.getById(dept_id);

				Employee manager = new Employee();
				manager.setId(manager_id);

				employee = new Employee(emplId, emplName, position, salary, dept, manager);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		List<Employee> employees = new ArrayList<>();
		String sql = "select * from revcomp_schema.employees;";

		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			ResultSet rs = s.executeQuery();

			// empl_name, monthly_salary, empl_position, manager_id, dept_id
			while (rs.next()) {
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("empl_name");
				float salary = rs.getFloat("monthly_salary");
				String position = rs.getString("empl_position");
				int manager_id = rs.getInt("manager_id");
				int dept_id = rs.getInt("dept_id");

				// get the department_id
				DepartmentPostgres deptP = new DepartmentPostgres();
				Department dept = deptP.getById(dept_id);

				Employee manager = new Employee();
				manager.setId(manager_id);

				Employee employee = new Employee(emplId, emplName, position, salary, dept, manager);
				employees.add(employee);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public Integer update(Employee t) {
		// empl_name, monthly_salary, empl_position, manager_id, dept_id
		String sql = "UPDATE revcomp_schema.employees SET empl_name = ?, monthly_salary = ?, empl_position = ?, manager_id = ?, dept_id = ? WHERE empl_id = ?";

		int affectedrows = 0;

		try (Connection c = ConnectionUtil.getConnectionFromEnv(); PreparedStatement pstmt = c.prepareStatement(sql)) {

			pstmt.setString(1, t.getName());
			pstmt.setDouble(2, t.getMonthlySalary());
			pstmt.setString(3, t.getPosition());
			pstmt.setInt(4, t.getManager().getId());
			pstmt.setInt(5, t.getDepartment().getId());
			pstmt.setInt(6, t.getId());

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows;
	}

	@Override
	public Integer delete(Employee t) {
		String SQL = "DELETE FROM revcomp_schema.employees WHERE empl_id = ?";

		int affectedrows = 0;

		try (Connection conn = ConnectionUtil.getConnectionFromEnv();
				PreparedStatement pstmt = conn.prepareStatement(SQL)) {

			pstmt.setInt(1, t.getId());

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows;
	}

	@Override
	public List<Employee> getByName(String name) {
		// TODO Auto-generated method stub
		List<Employee> employees = new ArrayList<>();
		String sql = "select * from revcomp_schema.employees where empl_name = ?;";

		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			PreparedStatement s = c.prepareStatement(sql);
			s.setString(1, name);
			ResultSet rs = s.executeQuery();

			// empl_name, monthly_salary, empl_position, manager_id, dept_id
			while (rs.next()) {
				int emplId = rs.getInt("empl_id");
				String emplName = rs.getString("empl_name");
				float salary = rs.getFloat("monthly_salary");
				String position = rs.getString("empl_position");
				int manager_id = rs.getInt("manager_id");
				int dept_id = rs.getInt("dept_id");

				// get the department_id
				DepartmentPostgres deptP = new DepartmentPostgres();
				Department dept = deptP.getById(dept_id);

				Employee manager = new Employee();
				manager.setId(manager_id);

				Employee employee = new Employee(emplId, emplName, position, salary, dept, manager);
				employees.add(employee);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}

}
