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
//    request.getRequestDispatcher("static/index.html").forward(request, response);
      System.out.println("Service method for " + request.getMethod() + " was called.");
      
      String deptParam = request.getParameter("monthly_budget");
      
      List<Department> depts;
      
      if(deptParam != null) {
      	depts = ds.getDepartmentsByMonthlyBudget(Double.parseDouble(deptParam));
      }
      else {
      	depts = ds.getDepartments();
      }
      
      ObjectMapper om = new ObjectMapper();
      PrintWriter pw = response.getWriter();
		pw.write(om.writeValueAsString(depts));
      
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	String id = request.getParameter("id");
     	String name = request.getParameter("name");
     	String monthlyBudget = request.getParameter("monthlyBudget");
     	Double mb;
     	Integer i;
     	
     	Department d = new Department();
     	
     	try {
     		i = Integer.parseInt(id);
     		
     	}catch(NullPointerException | NumberFormatException e) {
     		i = 0;
     	}
     	
     	try {
     		mb = Double.parseDouble(monthlyBudget);
     	}catch(NullPointerException | NumberFormatException e) {
     		mb = 1.0;
     	}
     	
     	d.setId(i);
     	d.setName(name);
     	d.setMonthlyBudget(mb);
     	
     	ds.addDepartment(d);
     	if(id == null || name == null) {
     		response.setStatus(400);
     	}
     	else {
     		response.setStatus(201);
     	}
    }
}
