package com.mysite.servlet;


import com.mysite.model.Registration;
import com.mysite.repository.RepositoryHolidays;
import com.mysite.repository.RepositoryUsers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationServlet extends HttpServlet {

    private static final RepositoryUsers REPOSITORY_USERS = new RepositoryUsers();
    private static final RepositoryHolidays REPOSITORY_HOLIDAYS = new RepositoryHolidays();
    private static RepositoryUsers repUsers = new RepositoryUsers();

    public void init() {

    }

    static {

        try {
            REPOSITORY_USERS.createRegistrationTable();
            REPOSITORY_HOLIDAYS.createHolidaysTable();
            REPOSITORY_HOLIDAYS.fillSqlTable();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("fullname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        String passVerification = request.getParameter("passVerification");


        boolean userInDB = false;

        try {

            userInDB = repUsers.checkUsernameInDB(username);

            String message;

            if (pass.equals(passVerification) && !userInDB) {

                Registration reg = new Registration();
                reg.setFullname(name);
                reg.setEmail(email);
                reg.setUsername(username);
                reg.setPassword(pass);

                repUsers.saveRegistrationDetails(reg);

                message = "User " + username + " is registered";
                request.getSession().setAttribute("info", message);

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/successful.jsp");
                rd.forward(request, response);

            } else {

                if (userInDB) {

                    message = "Username " + username + " already exists!";

                } else {

                    message = "passwords did not match! Please try again";

                }

                request.getSession().setAttribute("info", message);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/unsuccessful.jsp");
                rd.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
