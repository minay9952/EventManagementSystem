package Interfaces;

import Exceptions.*;
import Models.Gender;

import java.time.LocalDateTime;

public interface IEventManager {

    String addOrganizer (String name, Gender gender, int age) throws OrganizerServiceException;

    void removeOrganizer(String organizerId) throws OrganizerServiceException;

    String addParticipant(String name, Gender gender, int age) throws ParticipantServiceException;

    void removeParticipant(String participantId) throws ParticipantServiceException;

    String createOnlineEvent(String organizerId, String name, LocalDateTime startDate, LocalDateTime endDate) throws OrganizerServiceException;

    String createOnsiteEvent(String organizerId, String name, LocalDateTime startDate, LocalDateTime endDate, String location) throws OrganizerServiceException;

    void cancelEvent(String organizerId, String eventId) throws EventServiceException, OrganizerServiceException;

    void giveAttendance(String organizerId, String eventId, String attendantId) throws EventServiceException, OrganizerServiceException, AttendantServiceException;

    void removeAttendance(String organizerId, String eventId, String attendantId) throws EventServiceException, OrganizerServiceException, AttendantServiceException;

    void addParticipant(String organizerId, String eventId, String participantId) throws OrganizerServiceException, ParticipantServiceException, EventServiceException;

    void removeParticipant(String organizerId, String eventId, String participantId) throws OrganizerServiceException, ParticipantServiceException, EventServiceException, AttendantServiceException;

    void register(String eventId, String participantId) throws EventServiceException, ParticipantServiceException;

    void unregister(String eventId, String participantId) throws EventServiceException, ParticipantServiceException, AttendantServiceException;
}
