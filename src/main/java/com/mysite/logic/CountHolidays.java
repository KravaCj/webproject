package com.mysite.logic;

import com.mysite.connection.ConnectionToDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeSet;

public class CountHolidays {

    private final GregorianCalendar firstDate;
    private final GregorianCalendar secondDate;

    private static final TreeSet<GregorianCalendar> holidays = new TreeSet<>();

    private static ConnectionToDB connectToDB = new ConnectionToDB();

    private static final String SELECT_ALL_HOLIDAYS = "SELECT date_of_celebration FROM holidays";

    public CountHolidays(final GregorianCalendar firstDate, final GregorianCalendar secondDate) {
        if (firstDate.compareTo(secondDate) <= 0) {
            this.firstDate = firstDate;
            this.secondDate = secondDate;
        } else {
            this.firstDate = secondDate;
            this.secondDate = firstDate;
        }
    }

    public int calculateFreeDays() {

        GregorianCalendar firstDate = (GregorianCalendar) this.firstDate.clone();

        int freeDays = 0;
        while (firstDate.compareTo(secondDate) <=0) {

            if (firstDate.get(Calendar.DAY_OF_WEEK) > 5) {
                freeDays++;
            }

            firstDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return freeDays;
    }

    //TODO: test2

    private static void loadHolidaysToCollection() throws SQLException {

        Connection cnDB = null;
        Statement state = null;
        ResultSet result = null;
        boolean commit = false;

        try {
            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            state = cnDB.createStatement();
            result = state.executeQuery(SELECT_ALL_HOLIDAYS);

            while (result.next()) {

                GregorianCalendar gregCalendar = convertToGregorianCalendar
                        (result.getString("date_of_celebration"));
                holidays.add(gregCalendar);
            }

            commit = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            if (commit) {
                cnDB.commit();
            } else {
                assert cnDB != null;
                cnDB.rollback();
            }

            try {
                assert result != null;
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                cnDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public int countOffWeekends() throws SQLException {

        loadHolidaysToCollection();

        int holidayCount = 0;
        int leftBorder = 0;

        for (GregorianCalendar holiday : holidays) {
            if ((holiday.compareTo(secondDate)) > 0) {
                break;
            }
            leftBorder++;
        }

        for (GregorianCalendar holiday : holidays) {
            if (leftBorder-- <= 0) {
                break;
            }

            if ((holiday.compareTo(firstDate)) < 0) {
                holiday.set(Calendar.YEAR, firstDate.get(Calendar.YEAR));

                if ((holiday.compareTo(firstDate)) < 0) {
                    holiday.add(Calendar.YEAR, 1);
                }
            }

            while ((holiday.compareTo(secondDate)) <= 0) {

                if (holiday.get(Calendar.DAY_OF_WEEK) <= 5) {
                    holidayCount++;
                }
                holiday.add(Calendar.YEAR, 1);
            }
        }

        holidays.clear();

        int weekend = calculateFreeDays();

        return holidayCount + weekend;
    }

    public static GregorianCalendar convertToGregorianCalendar(final String date) {

        String separator = "-";

        String resultSplit[] = date.split(separator);

        int day = Integer.valueOf(resultSplit[2]);
        int month = Integer.valueOf(resultSplit[1]);
        int year = Integer.valueOf(resultSplit[0]);

        return new GregorianCalendar(year, --month, --day);
    }

}