package tracker.db.entities;

import org.junit.Assert;
import org.junit.Test;
import tracker.dao.SubtaskDAO;
import tracker.dao.TaskDAO;
import tracker.dao.UserDAO;
import tracker.entities.Task;
import tracker.utils.DBHelper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class TestTaskTable {

    private TaskDAO taskDAO = new TaskDAO();
    private Connection con = DBHelper.getConnection();

    @Test
    public void testCreateTask() {
        taskDAO.create(new Task("Add method to ConsoleHelper class", "Method to help get input from user's"));
        List<Task> taskList = taskDAO.getAllTasks();
        Assert.assertEquals(5, taskList.size());
        Assert.assertEquals("Add method to ConsoleHelper class", taskList.get(4).getTaskName());
        Assert.assertEquals("Method to help get input from user's", taskList.get(4).getDescription());
    }

    @Test
    public void testCascadeDeleteSubtaskIfDeleteTask() {
        TaskDAO taskDAO = new TaskDAO();
        SubtaskDAO subtaskDAO = new SubtaskDAO();
        taskDAO.delete(taskDAO.getTask(3));
        int afterDeleteSize = subtaskDAO.getAllSubtasks().size();
        Assert.assertEquals(3, afterDeleteSize);
    }

    @Test
    public void testGetValidTask()
    {
        Task testTask = taskDAO.getTask(5);
        Task expectedTask = new Task(5, "Design the ArrayList class", "", 2);
        Assert.assertEquals(expectedTask, testTask);
    }

    @Test
    public void testAssignTaskToUser()
    {
        taskDAO.assignTaskToUser(taskDAO.getTask(5), new UserDAO().getUser(2));
        Task testTask = taskDAO.getTask(5);
        Task expectedTask = new Task(5, "Design the ArrayList class", "", 2);
        Assert.assertEquals(expectedTask, testTask);
    }

    @Test
    public void testValidGetAllTasks()
    {
        List<Task> testList = taskDAO.getAllTasks();
        List<Task> expectedList = new ArrayList<>();
        expectedList.add(new Task(1, "Design the Set intefrace", "Test Description1", 1));
        expectedList.add(new Task(2, "Create component BeanFactoryPostProcessor for Autowired annotation", "", 4));
        expectedList.add(new Task(4, "Design the Map interface", "Test Description1", 1));
        expectedList.add(new Task(5, "Design the ArrayList class", "", 2));
        expectedList.add(new Task(6, "Add method to ConsoleHelper class", "Method to help get input from user's", 0));
        Assert.assertEquals(expectedList, testList);
    }
}
