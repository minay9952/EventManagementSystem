package Models;

import Exceptions.AttendantServiceException;
import Exceptions.EventNotFoundException;
import Exceptions.EventServiceException;
import Exceptions.EventStartedException;
import Interfaces.IEvent;
import Interfaces.IOrganizer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Organizer extends Person implements IOrganizer {

    private ArrayList<IEvent> organizedEvents;

    public Organizer(String name, Gender gender, int age) {
        super(name, gender, age);
        organizedEvents = new ArrayList();
    }

    private IEvent getEventById(String eventId) throws EventServiceException {
        for(int i=0; i < organizedEvents.size(); i++){
            if(organizedEvents.get(i).getId().equals(eventId))
                return organizedEvents.get(i);
        }
        throw new EventNotFoundException("No event was found with this id " + eventId);
    }

    public Event createOnlineEvent(String name, LocalDateTime startDate, LocalDateTime endDate){
        Event createdEvent = new Event(name, startDate, endDate, this);
        organizedEvents.add(createdEvent);
        return createdEvent;
    }

    public Event createOnsiteEvent(String name, LocalDateTime startDate, LocalDateTime endDate, String location){
        Event createdEvent = new Event(name, startDate, endDate, this, location);
        organizedEvents.add(createdEvent);
        return createdEvent;
    }

    public void cancelEvent(String eventId) throws EventServiceException {
        IEvent event = getEventById(eventId);
        if(LocalDateTime.now().isAfter(event.getStartDate()))
            throw new EventStartedException("Can't cancel the event after it started");
        organizedEvents.remove(event);
    }

    public void giveAttendance(String eventId, String attendantId) throws EventServiceException, AttendantServiceException {
        IEvent event = getEventById(eventId);
        event.giveAttendance(attendantId);
    }

    public void removeAttendance(String eventId, String attendantId) throws EventServiceException, AttendantServiceException {
        IEvent event = getEventById(eventId);
        event.removeAttendance(attendantId);
    }

    public void addParticipant(String eventId, Participant participant) throws EventServiceException {
        IEvent event = getEventById(eventId);
        Attendant attendant = new Attendant(participant);
        event.addAttendant(attendant);
    }

    public void removeParticipant(String eventId, String attendantId) throws EventServiceException, AttendantServiceException {
        IEvent event = getEventById(eventId);
        event.removeAttendant(attendantId);
    }
}
