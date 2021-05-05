package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.daos.EmployeeDao;
import com.revature.daos.EmployeePostgres;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    
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
        // TODO: Retrieve all departments
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // TODO: Create a department
    }
}
