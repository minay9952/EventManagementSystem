package Exceptions;

public class EventNotStartedException extends EventServiceException{
    public EventNotStartedException(String message) {
        super(message);
    }
}
