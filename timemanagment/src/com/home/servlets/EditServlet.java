package com.home.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import java.util.List;
import com.home.timemanagment.DeveloperTask;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.util.Arrays;
import com.home.timemanagment.TaskState;
import com.home.timemanagment.DeveloperTaskDB;
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
            final String id = request.getParameter("id");
            final DeveloperTask developerTask = DeveloperTaskDB.selectOne(id);
            final List<TaskState> taskStateList = Arrays.asList(TaskState.values());
            if (developerTask != null) {
                request.setAttribute("developerTask", developerTask);
                request.setAttribute("taskStateList", taskStateList);
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
            final String id = request.getParameter("id");
            final String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");;
            final String stateString = request.getParameter("state");
            final TaskState state = TaskState.valueOf(stateString);
            final DeveloperTask developerTask = new DeveloperTask(id, name, state);
            DeveloperTaskDB.updateTask(developerTask);
            response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj");
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward((ServletRequest)request, (ServletResponse)response);
        }
    }
}
