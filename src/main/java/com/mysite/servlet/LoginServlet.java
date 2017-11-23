package com.mysite.servlet;

import com.mysite.repository.RepositoryUsers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("Username");
        String pass = request.getParameter("Password");
        String message;

        RepositoryUsers repUsers = new RepositoryUsers();

        boolean userReg = false;

        try {

            userReg = repUsers.getUserDetails(username, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (userReg) {

            message = "User: " + username;
            request.getSession().setAttribute("name", message);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF\\site.jsp");
            rd.forward(request, response);

        } else {

            message = "Incorrect login or password. Please try again";
            request.getSession().setAttribute("info", message);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF\\incorrectLogin.jsp");
            rd.forward(request, response);

        }

    }
}