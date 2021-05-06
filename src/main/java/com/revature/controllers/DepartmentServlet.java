package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.Department;
import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;

// import com.revature.services.DepartmentService;

// import com.revature.services.DepartmentServiceImplementation;

import util.h2Util;

public class DepartmentServlet extends HttpServlet {

    private h2Util h2 = new h2Util();
    DepartmentService ds = new DepartmentServiceImplementation();

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // TODO: Retrieve all departments
        List<Department> depts = ds.getDepartments();

        for (Department d : depts) {
            System.out.println(d.toString());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // TODO: Create a department
    }
}