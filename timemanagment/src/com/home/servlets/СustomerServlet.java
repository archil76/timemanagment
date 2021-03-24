package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import com.home.timemanagment.Customer;
import com.home.timemanagment.DeveloperTaskModel;



@WebServlet("/customers")
public class ÑustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		try {
			List<Customer> customersLists = DeveloperTaskModel.selectCustomersList();
			if (customersLists == null) {
				customersLists = new ArrayList<Customer>();
				return;
			}
			request.setAttribute("customersLists", customersLists);
			
			getServletContext().getRequestDispatcher("/customers.jsp").forward(request, response);
		
		} catch (Exception e) {
			this.getServletContext().getRequestDispatcher("/editsettings").forward(request, response);
		}

	}
}