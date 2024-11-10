package Exceptions;

public class AttendantNotFoundException extends AttendantServiceException{

    public AttendantNotFoundException(String message) {
        super(message);
    }
}
