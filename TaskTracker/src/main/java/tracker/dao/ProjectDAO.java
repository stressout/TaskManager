package tracker.dao;

import tracker.entities.Project;
import tracker.entities.Task;
import tracker.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends DAO<Project> {

    @Override
    public void create(Project entity) {
        String preparedSqlStatement = "INSERT INTO Projects (projectName) VALUES (?);";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setString(1, entity.getProjectName());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Project entity) {
        String preparedSqlStatement = "DELETE FROM Projects WHERE Projects.projectId = ?;";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, entity.getProjectId());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void showInfo(Project entity) {
        System.out.println("Project id= " + entity.getProjectId() + ", Project name= " + entity.getProjectName());
    }

    public Project getProject(int projectIdArg) {
        String preparedSqlStatement = "SELECT * FROM Projects WHERE Projects.projectId = ?;";
        Project project = null;
        try {
            PreparedStatement getProjectQuery = connection.prepareStatement(preparedSqlStatement);
            getProjectQuery.setInt(1, projectIdArg);
            ResultSet resultSet = getProjectQuery.executeQuery();
            if (resultSet.next()) {
                int projectId = resultSet.getInt(1);
                String projectName = resultSet.getString(2);
                project = new Project(projectId, projectName);
            } else
                throw new SQLException("Project with projectId " + projectIdArg + " does not exist");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return project;
    }

    public List<Project> getAllProjects() {
        List<Project> projectList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Projects");
            while (resultSet.next()) {
                int projectId = resultSet.getInt(1);
                String projectName = resultSet.getString(2);
                Project project = new Project(projectId, projectName);
                projectList.add(project);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return projectList;
    }

    public List<Task> getAllTasksOfUserOnThisProject(User user) {
        String query = "SELECT * FROM Tasks WHERE Tasks.userId = ?";
        List<Task> taskList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
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
