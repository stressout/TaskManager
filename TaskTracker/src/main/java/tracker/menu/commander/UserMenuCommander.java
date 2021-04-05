package tracker.menu.commander;

import javafx.util.Pair;
import tracker.dao.ProjectDAO;
import tracker.dao.TaskDAO;
import tracker.dao.UserDAO;
import tracker.entities.Project;
import tracker.entities.Task;
import tracker.entities.User;
import tracker.exceptions.NoSuchElementsMenuException;
import tracker.exceptions.NoSuchEntityException;
import tracker.menu.Options;
import tracker.utils.ConsoleHelper;
import tracker.menu.MenuHelper;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class UserMenuCommander implements MenuCommander {

    private UserDAO userDAO = new UserDAO();
    private List<User> userList;

    @Override
    public void execution(Options selectedEntity) {
        userList = userDAO.getAllUsers();
        if (selectedEntity == Options.USER) {
            try {
                Pair<Integer, Integer> actionAndId = MenuHelper.selectAction(userList, selectedEntity); // From this method we get required action and selected id of entity for further processing
                if (actionAndId.getKey() == 4)
                    return;
                executeSelectedAction(actionAndId.getKey(), actionAndId.getValue()); //
            } catch (InputMismatchException | IllegalArgumentException invalidInput) {
                System.out.println("You selected invalid option, try again or select option \"Return\"\n\n");
                execution(selectedEntity);
            } catch (NoSuchEntityException nsee) {
                System.out.println(nsee.getMessage() + "\n");
                execution(selectedEntity);
            } catch (NoSuchElementsMenuException nseme) {
                System.out.println(nseme.getMessage() + "\n");
                return;
            }
        } else
            executionCreateUser();
    }

    public List<User> getUsersList() {
        return new ArrayList<>(userList);
    }

    // This method is invoked if user's choice was 'Create'
    private void executionCreateUser() {
        int usersId = userList.get(userList.size() - 1).getUserId() + 1;
        System.out.println("Enter first name");
        String firstName = ConsoleHelper.readString();
        System.out.println("Enter last name");
        String lastName = ConsoleHelper.readString();
        User user = new User(usersId, firstName, lastName);
        userDAO.create(user);
        System.out.println("The user was successfully created");
    }

    // This method handles selected action from user input
    private void executeSelectedAction(int selectedAction, int selectedUser) {
        if (selectedAction < 1 || selectedAction > 5)
            throw new IllegalArgumentException("Invalid input");
        switch (selectedAction) {
            case 1: { // Assign user to the project
                ProjectDAO projectDAO = new ProjectDAO();
                System.out.println("Select the project to which you want to assign a User with userId " + selectedUser);
                for (Project project : projectDAO.getAllProjects())
                    System.out.println(project);
                int selectedProjectId = ConsoleHelper.readInt();
                if (!projectDAO.getAllProjects().stream().anyMatch(project -> project.getProjectId() == selectedProjectId))
                    throw new NoSuchEntityException("Project with id " + selectedProjectId + " is not exist"); // If was invalid input id's from user
                Project project = projectDAO.getProject(selectedProjectId);
                userDAO.assignUserToProject(userDAO.getUser(selectedUser), project);
                System.out.println("User with id " + selectedUser + " was successfully assigned to the project " + project.getProjectName());
                break;
            }
            case 2: {
                List<Task> taskList = new TaskDAO().getAllTasks().stream().filter(task -> task.getUserId() == selectedUser).collect(Collectors.toList());
                if (taskList.size() == 0)
                    throw new NoSuchElementsMenuException("Task's not found"); // If database hasn't any entity
                taskList.forEach(System.out::println);
                break;
            }
            case 3: { // Delete user
                User expectedUser = userDAO.getUser(selectedUser);
                userDAO.delete(expectedUser);
                System.out.println("User " + expectedUser.getFirstName() + " " + expectedUser.getLastName() + " was successfully deleted");
                break;
            }
            case 4: { // Get info about user
                userDAO.showInfo(userDAO.getUser(selectedUser));
                break;
            }
        }
    }
}
