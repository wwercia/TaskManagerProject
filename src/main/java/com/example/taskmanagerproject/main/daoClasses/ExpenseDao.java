package com.example.taskmanagerproject.main.daoClasses;

import java.sql.*;
import java.util.ArrayList;

public class ExpenseDao {

    private final Connection connection;

    public ExpenseDao() {
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

    public ArrayList<Expense> getEverything() {
        ArrayList<Expense> data = new ArrayList<>();
        final String sql = "SELECT * FROM months.expenses;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                Integer amount = resultSet.getInt("amount");
                String plusOrMinus = resultSet.getString("plus_or_minus");
                String spentOrGetFrom = resultSet.getString("spent_on_or_get_from");
                data.add(new Expense(id, date, amount, plusOrMinus, spentOrGetFrom));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public void addExpense(Expense expense) {

    }

    public void addMoney() {

    }

    public void updateExpense(Expense expense) {

    }

}
