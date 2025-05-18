package dataAccessObject;

import db.DBConnection;
import model.ToDoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoDAO {
    //--- Insert New Task
    public void insertToDo(ToDoItem item) throws SQLException {
        String sql = "INSERT INTO todos (task, due_date, is_done ) VALUES (?, ?, ?)";
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, item.getTask());
        statement.setDate(2, new java.sql.Date(item.getDate().getTime()));
        statement.setBoolean(3, item.isDone());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void updateTask(ToDoItem item) throws SQLException {
        String sql = "UPDATE todos SET is_done = ? WHERE task = ? AND due_date = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, item.isDone());
        statement.setString(2, item.getTask());
        statement.setDate(3, new java.sql.Date(item.getDate().getTime()));
        statement.executeUpdate();
        statement.close();
    }

    public List<ToDoItem> getAllTasks() throws SQLException {
        List<ToDoItem> items = new ArrayList<>();
        String sql = "SELECT task, due_date, is_done FROM todos";
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
           String task = resultSet.getString("task");
           Date date = resultSet.getDate("due_date");
           boolean isDone = resultSet.getBoolean("is_done");
           items.add(new ToDoItem(task, date, isDone));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return items;
    }
}
