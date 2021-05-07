package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.DepartmentPostgres;
import com.revature.models.Department;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    
    private h2Util h2= new h2Util();
    private DepartmentPostgres ds = new DepartmentPostgres();
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
        // TODO: Retrieve all departments
    	List<Department> d = new ArrayList<>();
		System.out.println("service method was called: " + request.getMethod() + " to " + request.getRequestURI());
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		pw.write(om.writeValueAsString(ds.getAll()));
		
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // TODO: Create a department
    	int id = Integer.parseInt(request.getParameter("id"));
    	String name = request.getParameter("name");
    	double budget = Double.parseDouble(request.getParameter("monthlyBudget"));
    	Department d = new Department();
    	d.setId(id);
    	d.setMonthlyBudget(budget);
    	d.setName(name);
		if(name == null || id == 0 || budget == 0.0 ) {
		response.setStatus(400);
		}else {
		response.setStatus(201);
		}
    }
}
