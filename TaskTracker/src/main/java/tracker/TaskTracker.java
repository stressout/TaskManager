package tracker;


import tracker.menu.StartMenu;
import tracker.utils.DBHelper;

public class TaskTracker {

    public static void main(String[] args) {
        StartMenu.startMenu();
        DBHelper.closeConnection();
    }
}
