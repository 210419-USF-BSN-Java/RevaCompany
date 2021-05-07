package com.revature.controllers;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Department;

import com.revature.services.DepartmentServiceImplementation;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    
    private h2Util h2= new h2Util();
    private DepartmentServiceImplementation dsi = new DepartmentServiceImplementation();
    
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
	
    	ObjectMapper om = new ObjectMapper();
    
		PrintWriter pw = response.getWriter();
		pw.write(om.writeValueAsString(dsi.getDepartments()));
    	//pw.write(dsi.getDepartments().toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	System.out.println("service method was called: " + request.getMethod() + " to " + request.getRequestURI());
    	String deptName = request.getParameter("deptName");
		String monthlyBudget = request.getParameter("monthlyBudget");
		double budget = Integer.valueOf(monthlyBudget);
		Department d = new Department();
		d.setId(99);
		d.setName(deptName);
		d.setMonthlyBudget(budget);
		

		if(deptName == null || monthlyBudget == null || dsi.addDepartment(d) == 0 ) {
		response.setStatus(400);
		}else {
		response.setStatus(201);
    
		}
    }
}
