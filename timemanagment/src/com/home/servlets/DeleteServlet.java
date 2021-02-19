package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import com.home.timemanagment.DeveloperTaskDB;
import com.home.timemanagment.DeveloperTaskModel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/delete" })
public class DeleteServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
           
        	DeveloperTaskModel.deleteDeveloperTask(request.getParameter("id"));
            response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj");
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}
