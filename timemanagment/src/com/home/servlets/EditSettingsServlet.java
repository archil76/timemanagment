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

@WebServlet("/editsettings")
public class EditSettingsServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
    		Map<String, Object> settings =  DeveloperTaskModel.getSettings();
            
        	request.setAttribute("url", settings.get("url"));
            request.setAttribute("username",  settings.get("username"));
            request.setAttribute("password",  settings.get("password"));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
        this.getServletContext().getRequestDispatcher("/editsettings.jsp").forward((ServletRequest)request, (ServletResponse)response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        
    	
    	Map<String, Object> result;
    	try {
            
    		String url = request.getParameter("url");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            result = DeveloperTaskModel.setSettings(url, username, password);
            
        }
        catch (Exception ex) {
            requestMessegePage(request, response, "Settings not saved", "editsettings");
            return;
        }
    	
    	if(result.containsKey("error")) {
    		requestMessegePage(request, response, result.get("message"), "editsettings");		 	
    	} else {
    		response.sendRedirect(String.valueOf(request.getContextPath()) + "/index");
    	}
    	
    }
    
    private void requestMessegePage(HttpServletRequest request, HttpServletResponse response, Object message, Object backpage) throws ServletException, IOException {
        
    	request.setAttribute("message", message);
        request.setAttribute("backpage", backpage);
        
        this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }
    

}