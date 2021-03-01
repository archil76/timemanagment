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
 * Servlet implementation class CreateCustomerServlet
 */
@WebServlet("/createcustomer")
public class CreateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/createcustomer.jsp").forward((ServletRequest)request, (ServletResponse)response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
        	
        	DeveloperTaskModel.createDeveloperTask(request.getParameter("name"));
            response.sendRedirect(String.valueOf(request.getContextPath()) + "/edit"); // Или назад
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/createcustomer.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }

}
