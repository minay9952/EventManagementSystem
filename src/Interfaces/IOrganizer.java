package Interfaces;

import Exceptions.AttendantServiceException;
import Exceptions.EventServiceException;
import Models.Event;
import Models.Participant;

import java.time.LocalDateTime;

public interface IOrganizer {

    Event createOnlineEvent(String name, LocalDateTime startDate, LocalDateTime endDate);

    Event createOnsiteEvent(String name, LocalDateTime startDate, LocalDateTime endDate, String location);

    void cancelEvent(String eventId) throws EventServiceException;

    void giveAttendance(String eventId, String attendantId) throws EventServiceException, AttendantServiceException;

    void removeAttendance(String eventId, String attendantId) throws EventServiceException, AttendantServiceException;

    void addParticipant(String eventId, Participant participant) throws EventServiceException;

    void removeParticipant(String eventId, String attendantId) throws EventServiceException, AttendantServiceException;
}
