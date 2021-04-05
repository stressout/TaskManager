package tracker.dao;

import tracker.entities.Subtask;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubtaskDAO extends DAO<Subtask> {

    @Override
    public void create(Subtask subtask) {
        String preparedSqlStatement = "INSERT INTO Subtasks (taskId, subtaskName) VALUES (?, ?);";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, subtask.getTaskId());
            createSql.setString(2, subtask.getSubtaskName());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Subtask subtask) {
        String preparedSqlStatement = "DELETE FROM Subtasks WHERE Subtasks.subtaskId = ?;";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, subtask.getSubtaskId());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void showInfo(Subtask entity) {
        System.out.println("Subtask id= " + entity.getSubtaskId() + ", Subtask name= " + entity.getSubtaskName() + ", task id= " + entity.getTaskId());
    }

    public Subtask getSubtask(int taskIdArg) {
        String preparedSqlStatement = "SELECT * FROM Subtasks WHERE Subtasks.subtaskId = ?;";
        Subtask subtask = null;
        try {
            PreparedStatement getProjectQuery = connection.prepareStatement(preparedSqlStatement);
            getProjectQuery.setInt(1, taskIdArg);
            ResultSet resultSet = getProjectQuery.executeQuery();
            if (resultSet.next()) {
                int subtaskId = resultSet.getInt(1);
                int taskId = resultSet.getInt(2);
                String subtaskName = resultSet.getString(3);
                subtask = new Subtask(subtaskId, taskId, subtaskName);
            } else
                throw new SQLException("Task with tasktId " + taskIdArg + " does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subtask;
    }

    public List<Subtask> getAllSubtasksForTask(int taskIdArg) {
        List<Subtask> subtaskList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Subtasks WHERE Subtasks.taskId = " + taskIdArg);
            while (resultSet.next()) {
                int subtaskId = resultSet.getInt(1);
                int taskId = resultSet.getInt(2);
                String subtaskName = resultSet.getString(3);
                Subtask subtask = new Subtask(subtaskId, taskId, subtaskName);
                subtaskList.add(subtask);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subtaskList;
    }

    public List<Subtask> getAllSubtasks() {
        List<Subtask> subtaskList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Subtasks");
            while (resultSet.next()) {
                int subtaskId = resultSet.getInt(1);
                int taskId = resultSet.getInt(2);
                String subtaskName = resultSet.getString(3);
                Subtask subtask = new Subtask(subtaskId, taskId, subtaskName);
                subtaskList.add(subtask);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subtaskList;
    }
}
