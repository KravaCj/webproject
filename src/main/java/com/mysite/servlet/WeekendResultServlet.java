package com.mysite.servlet;

import com.mysite.logic.CountHolidays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class WeekendResultServlet extends HttpServlet {

    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String beforeDate = request.getParameter("beforeDate");
        String afterDate = request.getParameter("afterDate");
        String validation = "";
        String message;

        if (beforeDate.equals(validation) || afterDate.equals(validation)) {

            message = "Incorrect entry of dates";
            request.getSession().setAttribute("weekend", message);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/result.jsp");
            rd.forward(request, response);

        } else {

            GregorianCalendar firstDateInterval = CountHolidays.convertToGregorianCalendar(beforeDate);
            GregorianCalendar secondDateInterval = CountHolidays.convertToGregorianCalendar(afterDate);

            CountHolidays countHolidays = new CountHolidays(firstDateInterval, secondDateInterval);

            int weekend = 0;
            try {

                weekend = countHolidays.countOffWeekends();

                request.getSession().setAttribute("weekend", " The number of weekends is: " + weekend);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/result.jsp");
                rd.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
}