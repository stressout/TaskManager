package tracker.menu.commander;

import javafx.util.Pair;
import tracker.dao.ProjectDAO;
import tracker.dao.UserDAO;
import tracker.entities.Project;
import tracker.entities.User;
import tracker.exceptions.EntityAlreadyExistsException;
import tracker.exceptions.NoSuchElementsMenuException;
import tracker.exceptions.NoSuchEntityException;
import tracker.menu.MenuHelper;
import tracker.menu.Options;
import tracker.utils.ConsoleHelper;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMenuCommander implements MenuCommander {

    private ProjectDAO projectDAO = new ProjectDAO();
    private List<Project> projectList;

    @Override
    public void execution(Options selectedEntity) {
        projectList = projectDAO.getAllProjects();
        if (selectedEntity == Options.PROJECT) {
            try {
                Pair<Integer, Integer> actionAndId = MenuHelper.selectAction(projectList, selectedEntity); // From this method we get required action and selected id of entity for further processing
                if (actionAndId.getKey() == 4)
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
                return;
            }
        } else
            try {
                executionCreateProject();
            } catch (EntityAlreadyExistsException eaee) {
                System.out.println(eaee.getMessage() + "\n");
                System.out.printf("%d - Try again %n", 1);
                System.out.printf("%d - Back on previous menu %n", 2);
                if (ConsoleHelper.readInt() == 1)
                    execution(Options.PROJECT_CREATE);
                else
                    return;
            }

    }

    public List<Project> getProjectList() {
        return new ArrayList<>(projectList);
    }

    // This method is invoked if user's choice was 'Create'
    private void executionCreateProject() {
        System.out.println("Enter Project name");
        String projectName = ConsoleHelper.readString();
        if (getProjectList().stream().anyMatch(project -> project.getProjectName().equals(projectName)))
            throw new EntityAlreadyExistsException("Project with name " + projectName + " already exist");
        Project project = new Project(projectName);
        projectDAO.create(project);
        System.out.println("The project was successfully created");
    }

    // This method handles selected action from user input
    private void executeSelectedAction(int selectedAction, int selectedProject) {
        if (selectedAction < 1 || selectedAction > 3)
            throw new IllegalArgumentException("Invalid input");
        switch (selectedAction) {
            case 1: { // Show all tasks of user's on this project
                List<User> userList = new UserDAO().getAllUsers().stream().filter(user -> user.getProjectId() == selectedProject).collect(Collectors.toList());
                if (userList.size() == 0)
                    throw new NoSuchElementsMenuException("Users on this project not found"); // If database hasn't any entity
                for (User user : userList)
                    System.out.println(user);
                int selectedUserId = ConsoleHelper.readInt();
                if (!userList.stream().anyMatch(user -> user.getUserId() == selectedUserId))
                    throw new NoSuchEntityException("User with id " + selectedUserId + " is not exist"); // If was invalid input id's from user
                projectDAO.getAllTasksOfUserOnThisProject(new UserDAO().getUser(selectedUserId)).forEach(System.out::println);
                break;
            }
            case 2: { // Delete project
                Project project = projectList.stream().filter(pr -> pr.getProjectId() == selectedProject).findFirst().get();
                projectDAO.delete(project);
                System.out.println("Project " + project.getProjectName() + " was successfully deleted");
                break;
            }
            case 3: { // Get info about project
                Project project = projectList.stream().filter(pr -> pr.getProjectId() == selectedProject).findFirst().get();
                projectDAO.showInfo(project);
                break;
            }
        }
    }
}
