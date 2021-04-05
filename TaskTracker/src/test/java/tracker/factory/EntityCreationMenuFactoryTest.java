package tracker.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tracker.menu.Options;
import tracker.menu.commander.ProjectMenuCommander;
import tracker.menu.commander.TaskMenuCommander;
import tracker.menu.commander.UserMenuCommander;
import tracker.menu.factory.EntityCreationMenuFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EntityCreationMenuFactoryTest {

    private Map<Options, String> map = new HashMap<>();

    @Before
    public void setMap() {
        map.put(Options.USER, UserMenuCommander.class.getSimpleName());
        map.put(Options.USER_CREATE, UserMenuCommander.class.getSimpleName());
        map.put(Options.PROJECT, ProjectMenuCommander.class.getSimpleName());
        map.put(Options.PROJECT_CREATE, ProjectMenuCommander.class.getSimpleName());
        map.put(Options.TASK, TaskMenuCommander.class.getSimpleName());
        map.put(Options.TASK_CREATE, TaskMenuCommander.class.getSimpleName());
        map.put(Options.EXIT, null);
    }

    @Test
    public void testForCorrectlyWorksFactoryMethod() {
        for (Options entity : Arrays.copyOf(Options.values(), Options.values().length - 1)) {
            String className = EntityCreationMenuFactory.createEntityMenu(entity).getClass().getSimpleName();
            Assert.assertEquals(map.get(entity), className);
        }
    }
}
