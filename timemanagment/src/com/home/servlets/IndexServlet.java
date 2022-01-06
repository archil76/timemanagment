package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import java.util.ArrayList;
import com.home.timemanagment.DeveloperTaskModel;
import com.home.timemanagment.DeveloperTasksListsList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		try {
			ArrayList<DeveloperTasksListsList> developerTasksListsLists = DeveloperTaskModel.getDeveloperTasksLists();
			if (developerTasksListsLists == null) {
				this.getServletContext().getRequestDispatcher("/editsettings").forward(request, response);
				return;
			}
			request.setAttribute("developerTasksListsLists", developerTasksListsLists);
			request.setAttribute("currentTab_id", request.getParameter("currentTab_id"));
			

			getServletContext().getRequestDispatcher("/indexj.jsp").forward(request, response);
		
		} catch (Exception e) {
			this.getServletContext().getRequestDispatcher("/editsettings").forward(request, response);
		}

	}
}