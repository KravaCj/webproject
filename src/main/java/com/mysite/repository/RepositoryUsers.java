package com.mysite.repository;

import com.mysite.connection.ConnectionToDB;
import com.mysite.model.Registration;

import java.sql.*;

public class RepositoryUsers {

    private static ConnectionToDB connectToDB = new ConnectionToDB();

    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS register(" +
            "id MEDIUMINT NOT NULL AUTO_INCREMENT," +
            " fullname VARCHAR(40) NOT NULL," +
            " email VARCHAR(40) NOT NULL," +
            " username VARCHAR(40) NOT NULL," +
            " password VARCHAR(40) NOT NULL," +
            " PRIMARY KEY (id))";

    private static final String INSERT_REGISTRATION = "INSERT INTO register (fullname,email,username, password) VALUES(?,?,?,?)";
    private static final String SELECT_USER_INFO = "SELECT username,password FROM register WHERE username = ? AND password = ?";
    private static final String CHECK_LOGIN = "SELECT username FROM register WHERE username = ?";

    public void createRegistrationTable() throws SQLException, ClassNotFoundException {

        Connection cnDB = null;
        Statement state = null;
        boolean commit = false;

        try {

            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            state = cnDB.createStatement();
            state.execute(CREATE_TABLE_USERS);

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

    public int saveRegistrationDetails(final Registration userInfo) throws SQLException, ClassNotFoundException {

        int resultOfReg;
        Connection cnDB = null;
        PreparedStatement ps = null;
        boolean commit = false;

        try {
            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            ps = cnDB.prepareStatement(INSERT_REGISTRATION);
            ps.setString(1, userInfo.getFullname());
            ps.setString(2, userInfo.getEmail());
            ps.setString(3, userInfo.getUsername());
            ps.setString(4, userInfo.getPassword());
            resultOfReg = ps.executeUpdate();

            commit = true;

        } finally {

            if (commit) {
                cnDB.commit();
            } else {
                assert cnDB != null;
                cnDB.rollback();
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

        return resultOfReg;

    }

    public boolean checkUsernameInDB(final String loginID) throws SQLException, ClassNotFoundException {

        Connection cnDB = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean checkUser = false;
        boolean commit = false;

        try {

            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            ps = cnDB.prepareStatement(CHECK_LOGIN);
            ps.setString(1, loginID);
            rs = ps.executeQuery();

            checkUser = rs.next();

            commit = true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        return checkUser;
    }

    public boolean getUserDetails(final String loginID, final String password) throws SQLException, ClassNotFoundException {

        Connection cnDB = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean checkDetails = false;
        boolean commit = false;

        try {

            cnDB = connectToDB.getConnection();
            cnDB.setAutoCommit(commit);
            ps = cnDB.prepareStatement(SELECT_USER_INFO);
            ps.setString(1, loginID);
            ps.setString(2, password);
            rs = ps.executeQuery();

            checkDetails = rs.next();

            commit = true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        return checkDetails;
    }

}
