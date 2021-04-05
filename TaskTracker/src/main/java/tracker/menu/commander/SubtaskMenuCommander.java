package tracker.menu.commander;

import javafx.util.Pair;
import tracker.dao.SubtaskDAO;
import tracker.entities.Subtask;
import tracker.entities.Task;
import tracker.menu.MenuHelper;
import tracker.menu.Options;

import java.util.InputMismatchException;
import java.util.List;

public class SubtaskMenuCommander implements MenuCommander {

    private SubtaskDAO subtaskDAO = new SubtaskDAO();
    private List<Subtask> subtaskList;
    private Task task;

    public SubtaskMenuCommander() {
    }

    public SubtaskMenuCommander(Task task) {
        this.task = task;
    }

    @Override
    public void execution(Options selectedEntity) {
        subtaskList = subtaskDAO.getAllSubtasksForTask(task.getTaskId());
        try {
            Pair<Integer, Integer> actionAndId = MenuHelper.selectAction(subtaskList, selectedEntity); // From this method we get required action and selected id of entity for further processing
            if (actionAndId.getKey() == 3)
                return;
            executeSelectedAction(actionAndId.getKey(), actionAndId.getValue());
        } catch (InputMismatchException | IllegalArgumentException invalidInput) {
            System.out.println("You selected invalid option, try again or select option \"Return\"\n\n");
            execution(selectedEntity);
        }
    }

    // This method handles selected action from user input
    private void executeSelectedAction(int selectedAction, int selectedSubtask) {
        if (selectedAction < 1 || selectedAction > 3)
            throw new IllegalArgumentException("Invalid input");
        switch (selectedAction) {
            case 1: { // Delete subtask
//                Subtask expectedSubtask = task.getSubtaskList().stream().filter(subtask -> subtask.getSubtaskId() == selectedSubtask).findFirst().get();
                Subtask expectedSubtask = subtaskDAO.getSubtask(selectedSubtask);
                subtaskDAO.delete(expectedSubtask);
                System.out.println("Subtask " + expectedSubtask.getSubtaskName() + " of task with id " + expectedSubtask.getTaskId() + " was successfully deleted");
                break;
            }
            case 2: { // Get info about subtask
                subtaskDAO.showInfo(subtaskDAO.getSubtask(selectedSubtask));
                break;
            }
        }
    }
}
