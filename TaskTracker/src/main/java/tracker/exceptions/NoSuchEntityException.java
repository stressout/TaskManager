package tracker.exceptions;


/**
 * This exception throws if entity wasn't found
 */

public class NoSuchEntityException extends RuntimeException{

    public NoSuchEntityException(String message) {
        super(message);
    }
}
