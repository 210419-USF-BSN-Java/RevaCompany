package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.h2Util;

public class DepartmentServlet extends HttpServlet{
    
    public void init(ServletConfig config) {
		try {
            h2Util.setup();
        } catch (FileNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	
	public void destroy() {
		try {
            h2Util.teardown();
        } catch (FileNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // TODO: Return all departments
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // TODO: Create a department
    }
}
