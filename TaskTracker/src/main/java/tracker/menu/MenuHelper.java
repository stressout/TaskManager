package tracker.menu;

import javafx.util.Pair;
import tracker.exceptions.NoSuchElementsMenuException;
import tracker.exceptions.NoSuchEntityException;
import tracker.utils.ConsoleHelper;

import java.util.List;

/**
 * This class contains one main method a "selectAction". It's checking availability list of entity (user, projects, tasks, subtasks) in database,
 * then getting input from user, checking correctly input and generating required menu, return selected action and selected id of entity for further processing
 */

public class MenuHelper {

    public static Pair<Integer, Integer> selectAction(List entityList, Options options) {
        String entityName = getNameOfEntity(options);
        if (entityList.size() == 0)
            throw new NoSuchElementsMenuException(entityName + "'s not found"); // If database hasn't any entity
        System.out.println("Select " + entityName + "'s id: ");
        entityList.forEach(System.out::println); // Displaying the list entities
        int selectedId = ConsoleHelper.readInt(); // Choosing required id from list
        if (!entityList.stream().anyMatch(ent -> Integer.parseInt(ent.toString().split(" ")[1].substring(0, 1)) == selectedId))
            throw new NoSuchEntityException(entityName + " with id " + selectedId + " is not exist"); // If was invalid input id's from user
        int selectAction = -1;
        printMenu(options);
        selectAction = ConsoleHelper.readInt(); // Choosing required action with selected entity
        Pair<Integer, Integer> returnPair = new Pair<>(selectAction, selectedId);
        return returnPair;
    }


    private static void printMenu(Options entity) {
        if (entity == null) { // This case for Subtasks, because in Enum Options i decided, that i won't to add this entity
            System.out.println("Select an action: ");
            System.out.printf("%d - Delete subtask%n", 1);
            System.out.printf("%d - Get info about subtask%n", 2);
            System.out.printf("%d - Return on back menu%n", 3);
        } else {
            switch (entity) { // This Switch for choice to required entity
                case USER: {
                    System.out.println("Select an action: ");
                    System.out.printf("%d - Assign user to the project%n", 1);
                    System.out.printf("%d - Show user's tasks%n", 2);
                    System.out.printf("%d - Delete user%n", 3);
                    System.out.printf("%d - Get info about user%n", 4);
                    System.out.printf("%d - Return on back menu%n", 5);
                    break;
                }
                case PROJECT: {
                    System.out.println("Select an action: ");
                    System.out.printf("%d - Show all tasks of user's on this project%n", 1);
                    System.out.printf("%d - Delete project%n", 2);
                    System.out.printf("%d - Get info about project%n", 3);
                    System.out.printf("%d - Return on back menu%n", 4);
                    break;
                }
                case TASK: {
                    System.out.println("Select an action: ");
                    System.out.printf("%d - Assign task to user%n", 1);
                    System.out.printf("%d - Create subtask%n", 2);
                    System.out.printf("%d - Select subtask%n", 3);
                    System.out.printf("%d - Delete task%n", 4);
                    System.out.printf("%d - Get info about task%n", 5);
                    System.out.printf("%d - Return on back menu%n", 6);
                    break;
                }
            }
        }
    }

    // Auxiliary method for the "selectAction" method
    private static String getNameOfEntity(Options options) {
        String result;
        if (options == null) // This case for Subtask
            return "Subtasks";
        StringBuilder nameBuilder = new StringBuilder(options.name().toLowerCase());
        nameBuilder.deleteCharAt(0).insert(0, options.name().charAt(0));
        return nameBuilder.toString();
    }
}
