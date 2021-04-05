package tracker.exceptions;


/**
 * This exception throws if menu is empty
 */

public class NoSuchElementsMenuException extends RuntimeException{

    public NoSuchElementsMenuException(String message) {
        super(message);
    }
}
