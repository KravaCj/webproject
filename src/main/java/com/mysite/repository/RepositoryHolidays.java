package com.mysite.repository;

import com.mysite.connection.ConnectionToDB;

import java.io.*;
import java.sql.*;

public class RepositoryHolidays {

    private static ConnectionToDB connectToDB = new ConnectionToDB();

    public static final String CREATE_TABLE_HOLIDAYS = "CREATE TABLE IF NOT EXISTS holidays(" +
            "id MEDIUMINT NOT NULL AUTO_INCREMENT," +
            " holiday_name VARCHAR(50) NOT NULL," +
            " date_of_celebration DATE NOT NULL," +
            " date_of_introduction DATE NOT NULL," +
            " PRIMARY KEY (id))";

    public static final String CHECKING_FOR_DATA = "SELECT id FROM holidays WHERE id > 1;";

    public void createHolidaysTable() throws SQLException, ClassNotFoundException {

        Connection cnDB = null;
        Statement state = null;
        boolean commit = false;

        try {

            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            state = cnDB.createStatement();
            state.executeUpdate(CREATE_TABLE_HOLIDAYS);

            commit = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (commit) {
                cnDB.commit();
            } else {
                assert cnDB != null;
                cnDB.rollback();
            }

            try {
                assert state != null;
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

    private static boolean checkDataOnDBhHolidays() throws SQLException, ClassNotFoundException {

        Connection cnDB = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean commit = false;
        boolean dataCheck = false;

        try {

            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            ps = cnDB.prepareStatement(CHECKING_FOR_DATA);
            rs = ps.executeQuery();
            dataCheck = rs.next();

            commit = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (commit) {
                cnDB.commit();
            } else {
                assert cnDB != null;
                cnDB.rollback();
            }
            try {
                assert rs != null;
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                assert ps != null;
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                cnDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return dataCheck;

    }

    public void fillSqlTable() throws SQLException, ClassNotFoundException {

        boolean dataCheck = false;
        dataCheck = checkDataOnDBhHolidays();

        if (!dataCheck) {
            Connection cnDB = null;
            Statement state = null;
            boolean commit = false;

            try {

                cnDB = connectToDB.getConnection();
                cnDB.setAutoCommit(commit);
                state = cnDB.createStatement();

                String insertSqlQuery;

                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("\\holidays.sql").getFile());

                BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while ((insertSqlQuery = input.readLine()) != null) {
                    state.executeUpdate(insertSqlQuery);

                }

                commit = true;

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (commit) {
                    cnDB.commit();
                } else {
                    assert cnDB != null;
                    cnDB.rollback();
                }

                try {
                    assert state != null;
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
    }

}