package tracker.db.entities;

import org.junit.Assert;
import org.junit.Test;
import tracker.dao.ProjectDAO;
import tracker.dao.UserDAO;
import tracker.entities.User;
import tracker.utils.DBHelper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TestUserTable {

    private UserDAO userDAO = new UserDAO();
    private Connection con = DBHelper.getConnection();

    @Test
    public void testCreateUser() {
        userDAO.create(new User("LEONARDO", "KAPAREZZI"));
        List<User> userList = userDAO.getAllUsers();
        Assert.assertEquals(6, userList.get(userList.size() - 1).getUserId());
        Assert.assertEquals("LEONARDO", userList.get(5).getFirstName());
        Assert.assertEquals("KAPAREZZI", userList.get(5).getLastName());
    }

    @Test
    public void testGetValidUser()
    {
        User testUser = userDAO.getUser(2);
        User expectUser = new User(2, "Neal", "Gafter", 1);
        Assert.assertEquals(expectUser, testUser);
    }

    @Test
    public void testDeleteUser()
    {
        userDAO.delete(userDAO.getUser(6));
        int testSize = userDAO.getAllUsers().size();
        int expectedSize = 5;
        Assert.assertEquals(expectedSize, testSize);
    }

    @Test
    public void testAssignUserToProject()
    {
        userDAO.assignUserToProject(userDAO.getUser(2), new ProjectDAO().getProject(1));
        User testUser = userDAO.getUser(2);
        User expectedUser = new User(2, "Neal", "Gafter", 1);
        Assert.assertEquals(expectedUser, testUser);
    }

    @Test
    public void testValidGetAllUsers()
    {
        List<User> testList = userDAO.getAllUsers();
        List<User> expectedList = new ArrayList<>();
        expectedList.add(new User(1, "Joshua", "Bloch", 1));
        expectedList.add(new User(2, "Neal", "Gafter", 1));
        expectedList.add(new User(3, "Frank", "Yellin", 2));
        expectedList.add(new User(4, "Juergen", "Hoeller", 3));
        expectedList.add(new User(5, "Michael", "Widenius", 4));
        expectedList.add(new User(6, "LEONARDO", "KAPAREZZI", 0));
        Assert.assertEquals(expectedList, testList);
    }
}
