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
import com.revature.daos.DepartmentDao;
import com.revature.daos.DepartmentPostgres;
import com.revature.models.Department;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    private DepartmentDao dp = new DepartmentPostgres();
    private h2Util h2= new h2Util();

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
        System.out.println("get");
        
        List<Department> departments = dp.getAll();
        
        ObjectMapper om = new ObjectMapper();
        PrintWriter pw = response.getWriter();
        pw.write(om.writeValueAsString(departments));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	System.out.println("post");
        String dept_name = request.getParameter("name");
        String dept_budget = request.getParameter("budget");
        
        Department d = new Department();
        d.setName(dept_name);
        d.setMonthlyBudget(Double.parseDouble(dept_budget));
        
        if (dept_name == null || dept_budget == null) {
        	response.setStatus(400);
        } else {
        	dp.add(d);
        	response.setStatus(201);
        }
    }
}
