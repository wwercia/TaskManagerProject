package com.example.taskmanagerproject.main.daoClasses;

import java.sql.*;
import java.util.ArrayList;

public class EventDao {

    private final Connection connection;

    public EventDao() {
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

    public ArrayList<Event> getEventsInSpecifiedDay(int dayToFind, String monthToFind) {
        ArrayList<Event> events = new ArrayList<>();
        final String sql = String.format("SELECT * FROM months.events WHERE month = \"%s\" AND day = %d;", monthToFind, dayToFind);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                int day = resultSet.getInt("day");
                String month = resultSet.getString("month");
                String event = resultSet.getString("event");
                events.add(new Event(id, startTime, endTime, day, month, event));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public void addEvent(Event event) {
        final String sql = String.format("INSERT INTO months.events (start_time, end_time, day, month, event) VALUES ('%s', '%s', %d, '%s', '%s')",
                event.getStartTime(),
                event.getEndTime(),
                event.getDay(),
                event.getMonth(),
                event.getEvent());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generetedKeys = statement.getGeneratedKeys();
            if (generetedKeys.next()) {
                event.setId(generetedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with the query: " + sql);
        }
    }

    public boolean deleteEventByID(int id) {
        final String sql = "DELETE FROM months.events WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteEvent(Event event) {
        ArrayList<Event> events = getEventsInSpecifiedDay(event.getDay(), event.getMonth());
        Event eventToDelete = null;
        for (Event e : events) {
            if (e.getDay().equals(event.getDay()) && e.getStartTime().equals(event.getStartTime()) && e.getEndTime().equals(event.getEndTime())) {
                eventToDelete = e;
            }
        }
        if(eventToDelete != null){
            deleteEventByID(eventToDelete.getId());
            return true;
        }
        return false;
    }
}
