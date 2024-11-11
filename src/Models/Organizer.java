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

    /*
    * This method searches for an event by the event id
    *
    * @param eventId the ID of the event to search for
    * @return the event corresponding to the provided event ID if found
    * @throws EventNotFoundException in case the event was not found
    * */
    private IEvent getEventById(String eventId) throws EventServiceException {
        for(int i=0; i < organizedEvents.size(); i++){
            if(organizedEvents.get(i).getId().equals(eventId))
                return organizedEvents.get(i);
        }
        throw new EventNotFoundException("No event was found with this id " + eventId);
    }

    /*
    * This method creates an event of type online
    * @param name the name of the event
    * @param startDate the date and time in which the event will start in
    * @param endDate the date and time in which the event will end in
    * @return the event just created
    * */
    public Event createOnlineEvent(String name, LocalDateTime startDate, LocalDateTime endDate){
        Event createdEvent = new Event(name, startDate, endDate, this);
        organizedEvents.add(createdEvent);
        return createdEvent;
    }

    /*
     * This method creates an event of type onsite
     * @param name the name of the event
     * @param startDate the date and time in which the event will start in
     * @param endDate the date and time in which the event will end in
     * @param location the location where the event will be conducted
     * @return the event just created
     * */
    public Event createOnsiteEvent(String name, LocalDateTime startDate, LocalDateTime endDate, String location){
        Event createdEvent = new Event(name, startDate, endDate, this, location);
        organizedEvents.add(createdEvent);
        return createdEvent;
    }

    /*
    * This method cancels and event and remove it from the list of events
    * @param eventId the ID of the event to be canceled
    * @throws EventNotFoundException in case no event found with the provided ID
    * @throws EventStartedException in case the event already started
    * */
    public void cancelEvent(String eventId) throws EventServiceException {
        IEvent event = getEventById(eventId);
        if(LocalDateTime.now().isAfter(event.getStartDate()))
            throw new EventStartedException("Can't cancel the event after it started");
        organizedEvents.remove(event);
    }

    /*
    * This method gives attendants to an attendant
    *
    * @param eventId the ID of the event to give attendance for
    * @param attendantId the ID of the attendant who will be given the attendance
    * @throws EventNotFoundException in case no event found with the provided ID
    * @throws AttendantNotFoundException in case no attendant found with the provided ID
    * @throws EventNotStartedException in case the event didn't start yet
    * @throws EventEndedException in case the event already ended
    * */
    public void giveAttendance(String eventId, String attendantId) throws EventServiceException, AttendantServiceException {
        IEvent event = getEventById(eventId);
        event.giveAttendance(attendantId);
    }

    /*
    * This method removes the attendance for an attendant
    *
    * @param eventId the ID of the event to give attendance for
    * @param attendantId the ID of the attendant who will have his attendance removed
    * @throws EventNotFoundException in case no event found with the provided ID
    * @throws AttendantNotFoundException in case no attendant found with the provided ID
    * @throws EventNotStartedException in case the event didn't start yet
    * @throws EventEndedException in case the event already ended
    * */
    public void removeAttendance(String eventId, String attendantId) throws EventServiceException, AttendantServiceException {
        IEvent event = getEventById(eventId);
        event.removeAttendance(attendantId);
    }

    /*
    * this method adds a new participant to an event
    * @param eventId the ID of the event to add the participant to
    * @param participant the participant to be added
    * @throws EventNotFoundException in case no event was found with the provided ID
    * @throws EventStartedException in case the event already started
    * */
    public void addParticipant(String eventId, Participant participant) throws EventServiceException {
        IEvent event = getEventById(eventId);
        Attendant attendant = new Attendant(participant);
        event.addAttendant(attendant);
    }

    /*
    * This method removes a participant from the list of an event attendants
    *
    * @param eventId the ID of the event to remove the participant from
    * @param attendantId the ID of the participant to be removed
    * @throws EventNotFoundException in case the event was not found
    * @throws EventStartedException in case the event already started
    * */
    public void removeParticipant(String eventId, String attendantId) throws EventServiceException, AttendantServiceException {
        IEvent event = getEventById(eventId);
        event.removeAttendant(attendantId);
    }
}
