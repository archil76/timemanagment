package com.home.servlets;

import com.home.timemanagment.DatabaseState;
import java.io.IOException;
import javax.servlet.ServletException;
import java.util.Properties;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import com.home.timemanagment.DeveloperTaskDB;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/editsettings")
public class EditSettingsServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Properties properties = DeveloperTaskDB.getProperties();
        String url = "";
        String username = "";
        String password = "";
        try {
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        }
        catch (Exception ex) {}
        request.setAttribute("url", (Object)url);
        request.setAttribute("username", (Object)username);
        request.setAttribute("password", (Object)password);
        final String errorMessage = request.getParameter("error");
        if (errorMessage != null) {
            request.setAttribute("error", (Object)errorMessage);
        }
        else {
            request.setAttribute("error", (Object)"");
        }
        this.getServletContext().getRequestDispatcher("/editsettings.jsp").forward((ServletRequest)request, (ServletResponse)response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        
    	boolean isSettingSaved = false;
    	
    	try {
            String url = request.getParameter("url");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Properties properties = new Properties();
            properties.setProperty("url", url);
            properties.setProperty("username", username);
            properties.setProperty("password", password);
            boolean propertiesIsSet = DeveloperTaskDB.setProperties(properties);
            if (propertiesIsSet) {
                isSettingSaved = true;
            }
            else {
                this.requestMessegePage(request, response, "config.properties", "editsettings");
            }
        }
        catch (Exception ex) {
            this.requestMessegePage(request, response, "Settings not saved", "editsettings");
        }
        if (isSettingSaved) {
            this.checkDatabase(request, response);
        }
    }
    
    private void checkDatabase(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        
    	DatabaseState databaseState = DeveloperTaskDB.checkDatabase();
        if (databaseState == DatabaseState.OK) {
            response.sendRedirect(String.valueOf(request.getContextPath()) + "/index");
        }
        else if (databaseState == DatabaseState.TableNotExist) {
            final boolean tablesIsCreated = this.createTables();
            if (tablesIsCreated) {
                response.sendRedirect(String.valueOf(request.getContextPath()) + "/index");
            }
            else {
                this.requestMessegePage(request, response, "Tables is not created", "editsettings");
            }
        }
        else {
            this.requestMessegePage(request, response, databaseState.toString(), "editsettings");
        }
    }
    
    private void requestMessegePage(HttpServletRequest request, HttpServletResponse response, String message, String backpage) throws ServletException, IOException {
        request.setAttribute("message", (Object)message);
        request.setAttribute("backpage", (Object)backpage);
        this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
    
    public boolean createTables() {
        return DeveloperTaskDB.createTables();
    }
}