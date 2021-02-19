package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import java.util.List;
import java.util.Map;

import com.home.timemanagment.DeveloperTask;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.util.Arrays;
import com.home.timemanagment.TaskState;
import com.home.timemanagment.DeveloperTaskDB;
import com.home.timemanagment.DeveloperTaskModel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/edit" })
public class EditServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
        	Map<String, Object> attributes = DeveloperTaskModel.selectDeveloperTaskField(request.getParameter("id"));
            
        	if (attributes.get("developerTask") != null) {
        		
        		request.setAttribute("developerTask", attributes.get("developerTask"));
                request.setAttribute("taskStateList", attributes.get("taskStateList"));
                
                this.getServletContext().getRequestDispatcher("/edit.jsp").forward((ServletRequest)request, (ServletResponse)response);
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
 
        	DeveloperTaskModel.editDeveloperTask(request.getParameter("id"), request.getParameter("name"), request.getParameter("state"));

            response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj");
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}
