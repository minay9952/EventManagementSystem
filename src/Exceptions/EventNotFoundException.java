package Exceptions;

public class EventNotFoundException extends EventServiceException{
    public EventNotFoundException(String message) {
        super(message);
    }
}
