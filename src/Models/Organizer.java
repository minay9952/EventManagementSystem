package Models;

import Interfaces.IEvent;
import Interfaces.IOrganizer;

import java.util.ArrayList;
import java.util.Date;

public class Organizer extends Person implements IOrganizer {

    private ArrayList<IEvent> organizedEvents;

    public Organizer(String name, Gender gender, int age) {
        super(name, gender, age);
        organizedEvents = new ArrayList();
    }

    private IEvent getEventById(String eventId) throws Exception {
        for(int i=0; i < organizedEvents.size(); i++){
            if(organizedEvents.get(i).getId().equals(eventId))
                return organizedEvents.get(i);
        }
        throw new Exception("Event not found.");
    }

    public void createOnlineEvent(String name, Date startDate, Date endDate){
        Event createdEvent = new Event(name, startDate, endDate, this);
        organizedEvents.add(createdEvent);
    }

    public void createOnsiteEvent(String name, Date startDate, Date endDate, String location){
        Event createdEvent = new Event(name, startDate, endDate, this, location);
        organizedEvents.add(createdEvent);
    }

    public void cancelEvent(String eventId) throws Exception {
        IEvent event = getEventById(eventId);
        organizedEvents.remove(event);
    }

    public void giveAttendance(String eventId, String attendantId) throws Exception {
        IEvent event = getEventById(eventId);
        event.giveAttendance(attendantId);
    }

    public void removeAttendance(String eventId, String attendantId) throws Exception {
        IEvent event = getEventById(eventId);
        event.removeAttendance(attendantId);
    }

    public void addParticipant(String eventId, String attendantId) throws Exception {
        IEvent event = getEventById(eventId);
        event.giveAttendance(attendantId);
    }

    public void removeParticipant(String eventId, String attendantId) throws Exception {
        IEvent event = getEventById(eventId);
        event.removeAttendance(attendantId);
    }
}
