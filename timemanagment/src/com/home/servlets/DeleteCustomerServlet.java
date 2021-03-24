package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.timemanagment.DeveloperTaskModel;

/**
 * Servlet implementation class DeleteCustomerServlet
 */
@WebServlet("/deletecustomer")
public class DeleteCustomerServlet extends HttpServlet {
	
	    private static final long serialVersionUID = 1L;
	    
	    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
	        try {
	           
	        	DeveloperTaskModel.deleteCustomer(request.getParameter("id"));
	            response.sendRedirect(String.valueOf(request.getContextPath()) + "/customers");
	        }
	        catch (Exception ex) {
	            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
	        }
	    }
	    
	    
	}
