package com.home.servlets;

import com.home.timemanagment.DeveloperTaskDB;
import com.home.timemanagment.DeveloperTask;
import java.util.UUID;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/create" })
public class CreateServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/create.jsp").forward((ServletRequest)request, (ServletResponse)response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = UUID.randomUUID().toString();
            String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
            DeveloperTask developerTask = new DeveloperTask(id, name);
            DeveloperTaskDB.insertTask(developerTask);
            response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj");
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/create.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}