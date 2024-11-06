package Interfaces;

import Models.Event;
import Models.Participant;

import java.time.LocalDateTime;

public interface IOrganizer {

    public Event createOnlineEvent(String name, LocalDateTime startDate, LocalDateTime endDate);

    public Event createOnsiteEvent(String name, LocalDateTime startDate, LocalDateTime endDate, String location);

    public void cancelEvent(String eventId) throws Exception;

    public void giveAttendance(String eventId, String attendantId) throws Exception;

    public void removeAttendance(String eventId, String attendantId) throws Exception;

    public void addParticipant(String eventId, Participant participant) throws Exception;

    public void removeParticipant(String eventId, String attendantId) throws Exception;
}
