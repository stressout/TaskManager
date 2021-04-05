package tracker.exceptions;


/**
 * This exception throws if user try create entity that already exists
 */

public class EntityAlreadyExistsException extends RuntimeException{

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
