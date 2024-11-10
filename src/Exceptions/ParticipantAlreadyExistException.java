package Exceptions;

public class ParticipantAlreadyExistException extends ParticipantServiceException{

    public ParticipantAlreadyExistException(String message) {
        super(message);
    }

}
