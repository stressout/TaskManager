package tracker.dao;

import tracker.entities.Project;
import tracker.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<User> {

    @Override
    public void create(User entity) {
        String preparedSqlStatement = "INSERT INTO Users (firstName, lastName) VALUES (?, ?);";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setString(1, entity.getFirstName());
            createSql.setString(2, entity.getLastName());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(User entity) {
        String preparedSqlStatement = "DELETE FROM Users WHERE Users.userId = ?;";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, entity.getUserId());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void showInfo(User entity) {
        System.out.println("User id= " + entity.getProjectId() + ", First name= " + entity.getFirstName() + ", Last name= " + entity.getLastName()
                + ", Project id= " + entity.getProjectId());
    }

    public User getUser(int userIdArg) {
        String preparedSqlStatement = "SELECT * FROM Users WHERE Users.userId = ?;";
        User user = null;
        try {
            PreparedStatement getProjectQuery = connection.prepareStatement(preparedSqlStatement);
            getProjectQuery.setInt(1, userIdArg);
            ResultSet resultSet = getProjectQuery.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int projectId = resultSet.getInt(4);
                user = new User(userId, firstName, lastName, projectId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public void assignUserToProject(User user, Project project) {
        String preparedSqlStatement = "UPDATE Users SET Users.projectId = ? WHERE Users.userId = ?;";
        try {
            PreparedStatement createSql = connection.prepareStatement(preparedSqlStatement);
            createSql.setInt(1, project.getProjectId());
            createSql.setInt(2, user.getUserId());
            createSql.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int projectId = resultSet.getInt(4);
                User user = new User(userId, firstName, lastName, projectId);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
