package Models;

import Exceptions.AttendantNotFoundException;
import Exceptions.EventEndedException;
import Exceptions.EventNotStartedException;
import Exceptions.EventStartedException;
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

    private IAttendant getAttendantById(String attendantId) throws AttendantNotFoundException {
        for(int i=0; i < attendants.size(); i++){
            if(attendants.get(i).getId().equals(attendantId))
                return attendants.get(i);
        }
        throw new AttendantNotFoundException("No attendant was found with this id " + attendantId);
    }

    public void giveAttendance(String attendantId) throws AttendantNotFoundException, EventNotStartedException, EventEndedException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventNotStartedException("The event didn't start yet. It starts at " + startDate.toString());
        if(LocalDateTime.now().isAfter(endDate))
            throw new EventEndedException("The event already ended on " + endDate.toString());
        IAttendant attendant = getAttendantById(attendantId);
        attendant.setHasAttended(true);
    }

    public void removeAttendance(String attendantId) throws EventNotStartedException, EventEndedException, AttendantNotFoundException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventNotStartedException("The event didn't start yet. It starts at " + startDate.toString());
        if(LocalDateTime.now().isAfter(endDate))
            throw new EventEndedException("The event already ended on " + endDate.toString());
        IAttendant attendant = getAttendantById(attendantId);
        attendant.setHasAttended(false);
    }

    public void addAttendant(IAttendant attendant) throws EventStartedException {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventStartedException("Can't add attendants after the event started");
        attendants.add(attendant);
    }

    public void removeAttendant(String attendantId) throws Exception {
        if(LocalDateTime.now().isBefore(startDate))
            throw new EventStartedException("Can't remove attendants after the event started");
        IAttendant attendant = getAttendantById(attendantId);
        attendants.remove(attendant);
    }
}
