package com.home.servlets;

import com.home.timemanagment.TimeInterval;
import com.home.timemanagment.DeveloperTask;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;
import com.home.timemanagment.DeveloperTaskDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/startstop" })
public class MainServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(String.valueOf(request.getContextPath()) + "/indexj");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            DeveloperTask developerTask = DeveloperTaskDB.selectOne(id);
            
            if (developerTask != null) {
            	
            	boolean isActual = developerTask.isActual();
            	developerTask.setActual(!isActual);
                
            	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
                TimeInterval timeInterval = developerTask.getLastTimeInterval();
                
                if (timeInterval == null) {
                    developerTask.addTimeInterval(timestamp, null);
                    timeInterval = developerTask.getLastTimeInterval();
                    DeveloperTaskDB.insertTimeInterval(developerTask, timeInterval);
                }
                else if (timeInterval.getEndTime() == null) {
                    timeInterval.setEndTime(timestamp);
                    DeveloperTaskDB.updateTimeInterval(developerTask, timeInterval);
                }
                else {
                    developerTask.addTimeInterval(timestamp, null);
                    timeInterval = developerTask.getLastTimeInterval();
                    DeveloperTaskDB.insertTimeInterval(developerTask, timeInterval);
                }
            }
            else {
                this.getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        }
        catch (Exception ex) {
            this.getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
        this.doGet(request, response);
    }
}