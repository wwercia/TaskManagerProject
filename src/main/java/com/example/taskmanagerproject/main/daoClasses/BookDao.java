package com.example.taskmanagerproject.main.daoClasses;

import java.sql.*;
import java.util.ArrayList;

public class BookDao {

    private final Connection connection;

    public BookDao() {
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

    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> days = new ArrayList<>();
        final String sql = "SELECT * FROM months.books;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int isRead = resultSet.getInt("is_read");
                String opinion = resultSet.getString("opinion");
                boolean isReadd = isRead > 0;
                days.add(new Book(id, name, isReadd, opinion));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return days;
    }

    public boolean updateIsReadField(Book book){
        int isdone = 0;
        if (book.isRead()) {
            isdone = 1;
        }
        final String sql = String.format("UPDATE `months`.`books` SET `is_read` = '%d' WHERE (`id` = '%d');",
                isdone,
                book.getId());
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book){
        final String sql = String.format("INSERT INTO `months`.`books` (`name`, `is_read`) VALUES ('%s', '0');",
                book.getName());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generetedKeys = statement.getGeneratedKeys();
            if (generetedKeys.next()) {
                book.setId(generetedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with the query: " + sql);
        }
    }

    public boolean deleteBook(Book book){
        final String sql = "DELETE FROM `months`.`books` WHERE (`id` = '" + book.getId() + "');";
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateNameField(Book book){
        final String sql = String.format("UPDATE `months`.`books` SET `name` = '%s' WHERE (`id` = '%d');",
                book.getName(),
                book.getId());
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateOpinion(Book book){
        final String sql = String.format("UPDATE `months`.`books` SET `opinion` = '%s' WHERE (`id` = '%d');",
                book.getOpinion(),
                book.getId());
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
