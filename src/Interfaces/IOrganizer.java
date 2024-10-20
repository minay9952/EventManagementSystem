package Interfaces;

import Models.Event;
import Models.Participant;

import java.util.Date;

public interface IOrganizer {

    public void createOnlineEvent(String name, Date startDate, Date endDate);

    public void createOnsiteEvent(String name, Date startDate, Date endDate, String location);

    public void cancelEvent(String eventId) throws Exception;

    public void giveAttendance(String eventId, String attendantId) throws Exception;

    public void removeAttendance(String eventId, String attendantId) throws Exception;

    public void addParticipant(String eventId, String attendantId) throws Exception;

    public void removeParticipant(String eventId, String attendantId) throws Exception;
}
