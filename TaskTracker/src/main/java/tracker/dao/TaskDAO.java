package tracker.dao;

import tracker.entities.Task;
import tracker.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO extends DAO<Task> {

    @Override
    public void create(Task entity) {
        String preparedSqlStatement = "INSERT INTO Tasks (taskName, description) VALUES (?, ?);";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setString(1, entity.getTaskName());
            createSql.setString(2, entity.getDescription());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Task entity) {
        String preparedSqlStatement = "DELETE FROM Tasks WHERE Tasks.taskId = ?;";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, entity.getTaskId());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void showInfo(Task entity) {
        System.out.println("Task id= " + entity.getTaskId() + ", Task name= " + entity.getTaskName() + ", Decription= " + entity.getDescription()
                + ", User id= " + entity.getUserId());
    }

    public Task getTask(int taskIdArg) {
        String preparedSqlStatement = "SELECT * FROM Tasks WHERE Tasks.taskId = ?;";
        Task task = null;
        try {
            PreparedStatement getProjectQuery = connection.prepareStatement(preparedSqlStatement);
            getProjectQuery.setInt(1, taskIdArg);
            ResultSet resultSet = getProjectQuery.executeQuery();
            if (resultSet.next()) {
                int taskId = resultSet.getInt(1);
                String taskName = resultSet.getString(2);
                String description = resultSet.getString(3);
                int userId = resultSet.getInt(4);
                task = new Task(taskId, taskName, description, userId);
            } else
                throw new SQLException("Task with tasktId " + taskIdArg + " does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    public void assignTaskToUser(Task task, User user) {
        String preparedSqlStatement = "UPDATE Tasks SET Tasks.userId = ? WHERE Tasks.taskId = ?;";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, user.getUserId());
            createSql.setInt(2, task.getTaskId());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Tasks");
            while (resultSet.next()) {
                int taskId = resultSet.getInt(1);
                String taskName = resultSet.getString(2);
                String description = resultSet.getString(3);
                int userId = resultSet.getInt(4);
                Task task = new Task(taskId, taskName, description, userId);
                taskList.add(task);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return taskList;
    }
}
