package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;

import com.home.timemanagment.Customer;
import com.home.timemanagment.DeveloperTaskModel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/editcustomer" })
public class EditCustomerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        
    	try {
        	Customer customer = DeveloperTaskModel.selectCustomerFields(request.getParameter("id"));
            
        	if (customer != null) {
        		
        		request.setAttribute("customer", customer);
        		                
                this.getServletContext().getRequestDispatcher("/editcustomer.jsp").forward((ServletRequest)request, (ServletResponse)response);
            }
            else {
                this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
            }
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
 
        	DeveloperTaskModel.editCustomer(request.getParameter("id"), request.getParameter("name"));

            response.sendRedirect(String.valueOf(request.getContextPath()) + "/customers");
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}
