package tracker.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class contains useful methods to work with input from user and working with console
 */

public final class ConsoleHelper {

    private static BufferedReader readInput = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleHelper() {
    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static int readInt() {
        int read = 0;
        try {
            read = Integer.parseInt(readInput.readLine());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return read;
    }

    public static String readString() {
        String readString = null;
        try {
            readString = readInput.readLine();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return readString;
    }
}
