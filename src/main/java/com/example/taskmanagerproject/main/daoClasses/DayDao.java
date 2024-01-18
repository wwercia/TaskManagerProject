package com.example.taskmanagerproject.main.daoClasses;

import java.sql.*;
import java.util.ArrayList;

public class DayDao {

    private final Connection connection;

    public DayDao() {
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

    public ArrayList<Day> findAllDaysForSpecifiedMonth(String nameOfMonth){
        ArrayList<Day> days = new ArrayList<>();
        final String sql = String.format("SELECT * FROM months.days WHERE month = \"%s\";", nameOfMonth);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int name = resultSet.getInt("number");
                String x = resultSet.getString("name");
                String y = resultSet.getString("month");
                int week = resultSet.getInt("week");
                days.add(new Day(id, name, x,y, week));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return days;
    }

    public ArrayList<Day> getEverything(){
        ArrayList<Day> days = new ArrayList<>();
        final String sql = "SELECT * FROM months.days;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int name = resultSet.getInt("number");
                String x = resultSet.getString("name");
                String y = resultSet.getString("month");
                int week = resultSet.getInt("week");
                days.add(new Day(id, name, x,y, week));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return days;
    }

    public ArrayList<Day> getFirstWeek(String month){
        return getWeek(1, month);
    }

    public ArrayList<Day> getSecondWeek(String month){
        return getWeek(2, month);
    }
    public ArrayList<Day> getThirdWeek(String month){
        return getWeek(3, month);
    }
    public ArrayList<Day> getFourthWeek(String month){
        return getWeek(4, month);
    }
    public ArrayList<Day> getFifthWeek(String month){
        return getWeek(5, month);
    }

    private ArrayList<Day> getWeek(int numberOfWeek, String monthName){
        ArrayList<Day> days = new ArrayList<>();
        final String sql = String.format("SELECT * FROM months.days WHERE week = %d AND month = '%s';", numberOfWeek, monthName);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int name = resultSet.getInt("number");
                String x = resultSet.getString("name");
                String y = resultSet.getString("month");
                int week = resultSet.getInt("week");
                days.add(new Day(id, name, x,y, week));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return days;
    }

}
