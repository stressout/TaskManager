package tracker.menu.commander;

import javafx.util.Pair;
import tracker.dao.SubtaskDAO;
import tracker.dao.TaskDAO;
import tracker.dao.UserDAO;
import tracker.entities.Subtask;
import tracker.entities.Task;
import tracker.entities.User;
import tracker.exceptions.NoSuchElementsMenuException;
import tracker.exceptions.NoSuchEntityException;
import tracker.menu.MenuHelper;
import tracker.menu.Options;
import tracker.utils.ConsoleHelper;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class TaskMenuCommander implements MenuCommander {

    private TaskDAO taskDAO = new TaskDAO();
    private List<Task> taskList;

    @Override
    public void execution(Options selectedEntity) {
        taskList = taskDAO.getAllTasks();
        if (selectedEntity == Options.TASK) {
            try {
                Pair<Integer, Integer> actionAndId = MenuHelper.selectAction(taskList, selectedEntity); // From this method we get required action and selected id of entity for further processing
                if (actionAndId.getKey() == 6)
                    return;
                executeSelectedAction(actionAndId.getKey(), actionAndId.getValue());
            } catch (InputMismatchException | IllegalArgumentException invalidInput) {
                System.out.println("You selected invalid option, try again or select option \"Return\"\n\n");
                execution(selectedEntity);
            } catch (NoSuchEntityException nsee) {
                System.out.println(nsee.getMessage() + "\n");
                execution(selectedEntity);
            } catch (NoSuchElementsMenuException nseme) {
                System.out.println(nseme.getMessage() + "\n");
                for (StackTraceElement element : nseme.getStackTrace())
                    if (element.getMethodName().equals("executeSelectedAction"))
                        execution(selectedEntity);
            }
        } else
            executionCreateTask();
    }

    public List<Task> getUsersList() {
        return new ArrayList<>(taskList);
    }

    // This method is invoked if user's choice was 'Create'
    private void executionCreateTask() {
        System.out.println("Enter task name");
        String taskName = ConsoleHelper.readString();
        System.out.println("Enter description for task");
        String description = ConsoleHelper.readString();
        Task task = new Task(taskName, description);
        taskDAO.create(task);
        System.out.println("The task was successfully created");
    }

    // This method handles selected action from user input
    private void executeSelectedAction(int selectedAction, int selectedTask) {
        if (selectedAction < 1 || selectedAction > 6)
            throw new IllegalArgumentException("Invalid input");
        switch (selectedAction) {
            case 1: { // Assign task to user
                UserDAO userDAO = new UserDAO();
                System.out.println("Select the user to which you want to assign a Task with taskId " + selectedTask);
                for (User user : userDAO.getAllUsers())
                    System.out.println(user);
                int selectedUserId = ConsoleHelper.readInt();
                if (!userDAO.getAllUsers().stream().anyMatch(user -> user.getUserId() == selectedUserId))
                    throw new NoSuchEntityException("User with id " + selectedUserId + " is not exist"); // If was invalid input id's from user
                User user = userDAO.getUser(selectedUserId);
                taskDAO.assignTaskToUser(taskDAO.getTask(selectedTask), user);
                System.out.println("Task with id " + selectedTask + " was successfully assigned to the user " + user.getFirstName() + " " + user.getLastName());
                break;
            }
            case 2: { // Create subtask
                System.out.println("Enter subtask name");
                String subtaskName = ConsoleHelper.readString();
                Subtask subtask = new Subtask(selectedTask, subtaskName);
                new SubtaskDAO().create(subtask);
                System.out.println("The subtask was successfully created");
                break;
            }
            case 3: { // Select subtask
                SubtaskMenuCommander subtaskMenuCommander = new SubtaskMenuCommander(taskDAO.getTask(selectedTask));
                try {
                    subtaskMenuCommander.execution(null);
                } catch (NoSuchEntityException nsee) {
                    System.out.println(nsee.getMessage() + "\n");
                    executeSelectedAction(selectedAction, selectedTask);
                }
                break;
            }
            case 4: { // Delete task
                Task expectedTask = taskDAO.getTask(selectedTask);
                taskDAO.delete(expectedTask);
                System.out.println("Task " + expectedTask.getTaskName() + " with id " + expectedTask.getTaskId() + " was successfully deleted");
                break;
            }
            case 5: { // Get info about task
                taskDAO.showInfo(taskDAO.getTask(selectedTask));
                break;
            }
        }
    }
}
