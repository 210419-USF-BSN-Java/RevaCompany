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
import com.revature.daos.DepartmentArrayList;
import com.revature.models.Department;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    
    private h2Util h2= new h2Util();
    private DepartmentArrayList dal = new DepartmentArrayList();

    public void init(ServletConfig config) {
		try {
			System.out.println("init");
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
        // TODO: Retrieve all departments
    	System.out.println("GET");
        //request.getRequestDispatcher("static/index.html").forward(request, response);
        System.out.println("~~~~~~~~~~~~~~~~~~~~Get request in Departments~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("service method was called: " + request.getMethod() + " to " + request.getRequestURI());

        List<Department> departments;

        departments = dal.getAll();

        ObjectMapper om = new ObjectMapper();
        PrintWriter pw = response.getWriter();
        pw.write(om.writeValueAsString(departments));
        System.out.println("end of GET");
        //pw.write("<a href=\"../\" class=\"btn btn-primary\">View Departments</a>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // TODO: Create a department
    	System.out.println("POST");
    	String name = request.getParameter("name");
    	String budgetStr = request.getParameter("budget");
    	Double budget = Double.parseDouble(budgetStr);
    	Department d = new Department();
    	Department d1 = new Department();
    	
    	d.setName(name);
    	d.setMonthlyBudget(budget);
    	d1 = dal.add(d);
    	d.setId(d1.getId());
    
    	System.out.println("department added");
    	PrintWriter pw = response.getWriter();
    	pw.write("Department Added");
    	if(name == null||budget == null) {
    		response.setStatus(400);
    	} else {
    		response.setStatus(201);
    		//request.getRequestDispatcher("../").forward(request, response);
    	}
    }
}
