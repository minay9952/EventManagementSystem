package Exceptions;

public class ParticipantNotRegisteredException extends ParticipantServiceException{
    public ParticipantNotRegisteredException(String message) {
        super(message);
    }
}
