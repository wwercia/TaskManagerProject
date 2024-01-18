package com.example.taskmanagerproject.main.daoClasses;

import java.sql.*;

public class TodayDao {

    private final Connection connection;

    public TodayDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/months?serverTimezone=UTC", "root", "wwercia1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Today getLastDay() {
        final String sql = "SELECT * FROM tablefortrackingdays ORDER BY id DESC LIMIT 1";
        Today today = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int day = resultSet.getInt("day");
                today = new Today(id, day);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return today;
    }

    public void addNewDay(Today today){
        final String sql = String.format("INSERT INTO `months`.`tablefortrackingdays` (`day`) VALUES ('%d');",
                today.getDay());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generetedKeys = statement.getGeneratedKeys();
            if (generetedKeys.next()) {
                today.setId(generetedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with the query: " + sql);
        }
    }

}
