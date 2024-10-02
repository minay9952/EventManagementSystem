package Models;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    private ArrayList<Attendant> attendants;
    private String name;
    private EventType type;
    private Date startDate;
    private Date endDate;
    private Organizer organizer;
    private String location;

    public Event(String name, Date startDate, Date endDate, Organizer organizer, String location){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.location = location;
        this.attendants = new ArrayList<>();
    }
}
