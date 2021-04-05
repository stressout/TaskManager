package tracker.menu.factory;

import tracker.menu.Options;
import tracker.menu.commander.MenuCommander;

import java.io.File;
import java.util.Objects;

/**
 * This class generating required class of selected entity regarding the argument entity
 */

public class EntityCreationMenuFactory {


    public static MenuCommander createEntityMenu(Options entity) {
        MenuCommander commander = null;
        StringBuilder buildClassName = new StringBuilder(entity.name().toLowerCase());
        if (buildClassName.indexOf("_") > 0) // If was selected entity with name containing symbol '_'. These entities are designed to create entity
            buildClassName.delete(buildClassName.indexOf("_", 3), buildClassName.length());
        buildClassName.deleteCharAt(0).insert(0, entity.name().charAt(0));
        String firstWord = buildClassName.toString(); // Got required beginning name of required class
        File commanderPackage = new File("src/main/java/tracker/menu/commander");
        for (File fileFromCommanderDirectory : Objects.requireNonNull(commanderPackage.listFiles((File file, String fileName) -> fileName.startsWith(firstWord)))) {
            try {
                commander = (MenuCommander) Class.forName("tracker.menu.commander." + fileFromCommanderDirectory.getName().replace(".java", "")).getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        // This cycle goes through all files in the specified directory and filter these files that begin with required word
        // When required class will be found, program creating that class and returning it
        return commander;
    }
}
