package Models;

import Exceptions.*;
import Interfaces.IAttendant;
import Interfaces.IEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Event implements IEvent {

    private ArrayList<IAttendant> attendants;
    private String name;
    private EventType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Organizer organizer;
    private String location;
    private String id;
    private static int idCounter = 0;

    public Event(String name, LocalDateTime startDate, LocalDateTime endDate, Organizer organizer){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.location = null;
        this.type = EventType.ONLINE;
        this.attendants = new ArrayList<>();
        this.id = "E" + idCounter++;
    }

    public Event(String name, LocalDateTime startDate, LocalDateTime endDate, Organizer organizer, String location){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.location = location;
        this.type = EventType.ONSITE;
        this.attendants = new ArrayList<>();
        this.id = "E" + idCounter++;
    }

    public String getId(){
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    /*
    * This method search for any attendant by the ID
    *
    * @param attendantId the id of the attendant
    * @return the attendant if found
    * @throws AttendantNotFoundException in case the attendant wasn't found
    * */
    private IAttendant getAttendantById(String attendantId) throws AttendantServiceException {
        for(int i=0; i < attendants.size(); i++){
            if(attendants.get(i).getId().equals(attendantId))
                return attendants.get(i);
        }
        throw new AttendantNotFoundException("No attendant was found with this id " + attendantId);
    }

    /*
    * This method gives attendance for an attendant
    *
    * @param attendantId the attendant ID
    * @throws AttendantNotFoundException in case no attendant found with the provided ID
    * @throws EventNotStartedException in case the event to give attendance for has not yet started
    * @throws EventEndedException in case the event to give attendance for has ended
    * */
    public void giveAttendance(String attendantId) throws AttendantServiceException, EventServiceException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventNotStartedException("The event didn't start yet. It starts at " + startDate.toString());
        if(LocalDateTime.now().isAfter(endDate))
            throw new EventEndedException("The event already ended on " + endDate.toString());
        IAttendant attendant = getAttendantById(attendantId);
        attendant.setHasAttended(true);
    }

    /*
    * This method removes the attendance that was given before for an attendant
    *
    * @param attendantId the attendant ID
    * @throws AttendantNotFoundException in case no attendant found with the provided ID
    * @throws EventNotStartedException in case the event to give attendance for has not yet started
    * @throws EventEndedException in case the event to give attendance for has ended
    * */
    public void removeAttendance(String attendantId) throws EventServiceException, AttendantServiceException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventNotStartedException("The event didn't start yet. It starts at " + startDate.toString());
        if(LocalDateTime.now().isAfter(endDate))
            throw new EventEndedException("The event already ended on " + endDate.toString());
        IAttendant attendant = getAttendantById(attendantId);
        attendant.setHasAttended(false);
    }

    /*
    * This method adds an attendant to the event's list of attendants
    *
    * @param attendant the attendant to be added to the event
    * @throws EventStartedException in case the event already started
    * */
    public void addAttendant(IAttendant attendant) throws EventServiceException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventStartedException("Can't add attendants after the event started");
        attendants.add(attendant);
    }

    /*
    *This method removes an attendant from the event
    *
    * @param attendantId the attendant id of the attendant to be removed
    * @throws EventStartedException in case the event already started
    * @throws AttendantNotFoundException in case the attendant to be removed is not found
    * */
    public void removeAttendant(String attendantId) throws EventServiceException, AttendantServiceException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventStartedException("Can't remove attendants after the event started");
        IAttendant attendant = getAttendantById(attendantId);
        attendants.remove(attendant);
    }
}
