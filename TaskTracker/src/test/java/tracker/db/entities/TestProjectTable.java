package tracker.db.entities;


import org.junit.Assert;
import org.junit.Test;
import tracker.dao.ProjectDAO;
import tracker.dao.UserDAO;
import tracker.entities.Project;
import tracker.entities.Task;
import tracker.menu.commander.ProjectMenuCommander;
import tracker.utils.DBHelper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class TestProjectTable {

    private ProjectDAO projectDAO = new ProjectDAO();
    private Connection connection = DBHelper.getConnection();
    private ProjectMenuCommander pmc = new ProjectMenuCommander();


    @Test
    public void testCreateProject() {
        projectDAO.create(new Project("TEST PROJECT"));
        Project testProject = projectDAO.getProject(5);
        Project expectedProject = new Project(5, "TEST PROJECT");
        Assert.assertEquals(expectedProject, testProject);
    }

    @Test
    public void testDeleteProject() {
        projectDAO.delete(projectDAO.getProject(5));
        int testSize = projectDAO.getAllProjects().size();
        int expectedSize = 4;
        Assert.assertEquals(expectedSize, testSize);
    }

    @Test
    public void testShowProject() {
        Project testProject = projectDAO.getProject(1);
        Project expectedProject = new Project(1, "Java Collections");
        Assert.assertEquals(expectedProject, testProject);
    }

    @Test
    public void testGetAllTasksOfUsersOnProject() {
        List<Task> testList = projectDAO.getAllTasksOfUserOnThisProject(new UserDAO().getUser(1));
        List<Task> expectedList = new ArrayList<>();
        expectedList.add(new Task(1, "Design the Set intefrace", "Test Description1", 1));
        expectedList.add(new Task(4, "Design the Map interface", "Test Description1", 1));
        expectedList.add(new Task(5, "Design the ArrayList class", "", 1));
        Assert.assertEquals(expectedList, testList);
    }

}
