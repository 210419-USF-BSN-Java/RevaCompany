package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.EmployeeDao;
import com.revature.daos.EmployeePostgres;
import com.revature.models.Department;
import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    
    private h2Util h2= new h2Util();
    private DepartmentService ds = new DepartmentServiceImplementation();

    public void init(ServletConfig config) {
		try {
            h2.setup();
        } catch (FileNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	
	public void destroy() {
		try {
            h2.teardown();
        } catch (FileNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	System.out.println("service method was called: " + request.getMethod() + " to " + request.getRequestURI());
    	
		
		List<Department> department;
		
		System.out.println("get request to /departments");
		department = ds.getDepartments();
		
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		pw.write(om.writeValueAsString(department));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	Integer id = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	Double budget = Double.parseDouble(request.getParameter("monthlyBudget"));
    	
    	Department d = new Department();
    	d.setId(id);
    	d.setName(name);
    	d.setMonthlyBudget(budget);
    	
    	if(id == null||name == null||budget == null|| ds.addDepartment(d) == 0) {
    		response.setStatus(400);
    	} else {
    		response.setStatus(201);
    	}
    
    }
}
