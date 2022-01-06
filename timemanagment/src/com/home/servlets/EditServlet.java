package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;

import java.util.Map;


import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
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
        	Map<String, Object> attributes = DeveloperTaskModel.selectDeveloperTaskFields(request.getParameter("id"));
            
        	if (attributes.get("developerTask") != null) {
        		
        		request.setAttribute("developerTask", attributes.get("developerTask"));
        		request.setAttribute("customersList", attributes.get("customersList"));
                request.setAttribute("taskStateList", attributes.get("taskStateList"));
                request.setAttribute("currentTab_id", request.getParameter("currentTab_id"));
                
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
 
        	DeveloperTaskModel.editDeveloperTask(request.getParameter("id"), request.getParameter("name"), request.getParameter("state"), request.getParameter("customer"));

            response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj?currentTab_id=" + request.getParameter("currentTab_id"));
            
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}
