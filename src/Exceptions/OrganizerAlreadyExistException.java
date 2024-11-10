package Exceptions;

public class OrganizerAlreadyExistException extends OrganizerServiceException{

    public OrganizerAlreadyExistException(String message) {
        super(message);
    }

}
