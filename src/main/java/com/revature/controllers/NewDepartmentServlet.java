package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewDepartmentServlet extends HttpServlet{
    
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        //response.sendRedirect("static/index.html");
        
        request.getRequestDispatcher("static/index.html").forward(request, response);
    }
}
