package Exceptions;

public class ParticipantAlreadyRegisteredException extends ParticipantServiceException{
    public ParticipantAlreadyRegisteredException(String message) {
        super(message);
    }
}
