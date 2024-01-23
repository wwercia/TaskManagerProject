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
                String description = resultSet.getString("description");
                data.add(new Expense(id, date, amount, plusOrMinus, description));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public void addExpense(Expense expense) {
        final String sql = String.format("INSERT INTO `months`.`expenses` (`date`, `amount`, `plus_or_minus`, `description`) VALUES ('%s', '%d', '%s', '%s');",
                expense.getDate(), expense.getAmount(), expense.getPlusOrMinus(), expense.getDescription());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                expense.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with the query: " + sql);
        }
    }

    public void addMoney() {

    }

    public boolean updateExpense(Expense expense) {
        return false;
    }

}
