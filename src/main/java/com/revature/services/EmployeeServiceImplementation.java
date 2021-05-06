package com.revature.services;

import java.util.List;

import com.revature.daos.DAOFactory;
import com.revature.daos.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeServiceImplementation implements EmployeeService {

    private EmployeeDao ed;

    public EmployeeServiceImplementation() {
        ed = DAOFactory.getDAOFactory().getEmployeeDao();
    }

    @Override
    public Employee getByName(String name) {
        return ed.getByName(name);
    }

    @Override
    public Integer addEmployee(Employee e) {
        return ed.add(e).getId();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return ed.getById(id);
    }

    @Override
    public List<Employee> getEmployees() {
        return ed.getAll();
    }

    @Override
    public boolean updateEmployee(Employee e) {
        if (ed.update(e) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(Employee e) {
        if (ed.delete(e) > 0) {
            return true;
        } else {
            return false;
        }
    }

}
