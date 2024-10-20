package Models;

import Interfaces.IEvent;

import java.util.ArrayList;
import java.util.Date;

public class Event implements IEvent {

    private ArrayList<Attendant> attendants;
    private String name;
    private EventType type;
    private Date startDate;
    private Date endDate;
    private Organizer organizer;
    private String location;
    private String id;
    private static int idCounter = 0;

    public Event(String name, Date startDate, Date endDate, Organizer organizer){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.location = null;
        this.type = EventType.ONLINE;
        this.attendants = new ArrayList<>();
        this.id = "E" + idCounter++;
    }

    public Event(String name, Date startDate, Date endDate, Organizer organizer, String location){
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

    private Attendant getAttendantById(String attendantId) throws Exception {
        for(int i=0; i < attendants.size(); i++){
            if(attendants.get(i).getId().equals(attendantId))
                return attendants.get(i);
        }
        throw new Exception("Attendant not found.");
    }

    public void giveAttendance(String attendantId) throws Exception {
        Attendant attendant = getAttendantById(attendantId);
        attendant.setHasAttended(true);
    }

    public void removeAttendance(String attendantId) throws Exception {
        Attendant attendant = getAttendantById(attendantId);
        attendant.setHasAttended(false);
    }

    public void addAttendant(Attendant attendant){
        attendants.add(attendant);
    }

    public void removeAttendant(String attendantId) throws Exception {
        Attendant attendant = getAttendantById(attendantId);
        attendants.remove(attendant);
    }
}
