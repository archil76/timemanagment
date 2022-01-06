package com.home.servlets;


import com.home.timemanagment.DeveloperTaskModel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/startstop" })
public class MainServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj?currentTab_id=" + request.getParameter("currentTab_id"));
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
        	String id = request.getParameter("id");
            boolean state = DeveloperTaskModel.startOrStopTiming(id);
            
            if (state != true) {
            	this.getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
            
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
        
        this.doGet(request, response);
    }
}