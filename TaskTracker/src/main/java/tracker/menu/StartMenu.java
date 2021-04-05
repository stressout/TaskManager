package tracker.menu;

import tracker.menu.factory.EntityCreationMenuFactory;
import tracker.utils.ConsoleHelper;

public class StartMenu {

    public static void startMenu() {
        while (true) {
            Options selectedEntity = null;
            try {
                printMainMenu();
                selectedEntity = Options.values()[ConsoleHelper.readInt()];
                if (selectedEntity == Options.EXIT)
                    return;
                EntityCreationMenuFactory.createEntityMenu(selectedEntity).execution(selectedEntity);
                // getting the required class to work with selected entity: User, Task, Project
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException inputProblem) {
                System.out.println("You selected invalid option, try again or select option \"Exit\"\n\n");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("Select an action: ");
        System.out.printf("%d - Select user%n", Options.USER.ordinal());
        System.out.printf("%d - Create user%n", Options.USER_CREATE.ordinal());
        System.out.printf("%d - Select project%n", Options.PROJECT.ordinal());
        System.out.printf("%d - Create project%n", Options.PROJECT_CREATE.ordinal());
        System.out.printf("%d - Select task%n", Options.TASK.ordinal());
        System.out.printf("%d - Create task%n", Options.TASK_CREATE.ordinal());
        System.out.printf("%d - Exit%n", Options.EXIT.ordinal());
    }
}
