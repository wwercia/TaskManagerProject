package com.example.taskmanagerproject.main.daoClasses;

import java.sql.*;
import java.util.ArrayList;

public class TaskDao {

    private final Connection connection;

    public TaskDao() {
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

    public ArrayList<Task> getEverything() {
        ArrayList<Task> tasks = new ArrayList<>();
        final String sql = "SELECT * FROM months.tasks;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String task = resultSet.getString("task");
                int importanceLevel = resultSet.getInt("importance_level");
                int isdone = resultSet.getInt("is_done");
                boolean isDone = isdone > 0;
                tasks.add(new Task(id, task, importanceLevel, isDone));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public void addTask(Task task) {
        int isDone = 0;
        if (task.isDone()) {
            isDone = 1;
        }
        final String sql = String.format("INSERT INTO months.tasks (task, importance_level, is_done) VALUES ('%s', %d, %d)",
                task.getTask(),
                task.getImportanceLevel(),
                isDone);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generetedKeys = statement.getGeneratedKeys();
            if (generetedKeys.next()) {
                task.setId(generetedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with the query: " + sql);
        }
    }

    public boolean updateIsDoneField(Task task) {
        int isdone = 0;
        if (task.isDone()) {
            isdone = 1;
        }
        final String sql = String.format("UPDATE `months`.`tasks` SET `is_done` = '%d' WHERE (`id` = '%d');",
                isdone,
                task.getId());
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTask(Task task) {
        final String sql = String.format("UPDATE `months`.`tasks` SET `task` = '%s' WHERE (`id` = '%d');",
                task.getTask(),
                task.getId());
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteTask(int id) {
        final String sql = "DELETE FROM `months`.`tasks` WHERE (`id` = '" + id + "');";
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateImportanceLevel(Task task) {
        final String sql = "UPDATE `months`.`tasks` SET `importance_level` = '" + task.getImportanceLevel() + "' WHERE (`id` = '" + task.getId() + "');";
        System.out.println(sql);
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
