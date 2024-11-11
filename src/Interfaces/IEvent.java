package Interfaces;

import Exceptions.AttendantServiceException;
import Exceptions.EventServiceException;
import Exceptions.EventStartedException;
import Models.Attendant;
import Models.Event;
import Models.Participant;

import java.time.LocalDateTime;

public interface IEvent {

    String getId();

    LocalDateTime getStartDate();
    void giveAttendance(String attendantId) throws AttendantServiceException, EventServiceException;

    void removeAttendance(String attendantId) throws AttendantServiceException, EventServiceException;

    void addAttendant(IAttendant attendant) throws EventServiceException;

    void removeAttendant(String attendantId) throws AttendantServiceException, EventServiceException;

}
