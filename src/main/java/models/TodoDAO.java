package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mvc_todo";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            // Explicitly load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found!");
        }
    }

    public static List<Todo> getTodos() {
        List<Todo> todoList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM todos")) {

            while (rs.next()) {
                todoList.add(new Todo(rs.getInt("id"), rs.getString("task")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoList;
    }

    public static void addTodo(String task) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos (task) VALUES (?)")) {

            stmt.setString(1, task);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
