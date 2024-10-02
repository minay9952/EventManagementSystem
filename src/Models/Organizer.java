package Models;

import java.util.ArrayList;

public class Organizer extends Person{

    private ArrayList<Event> organizedEvents;

    public Organizer(String name, Gender gender, int age) {
        super(name, gender, age);
        organizedEvents = new ArrayList();
    }

    public void createEvent(){
        //ToDO: use the interface for the event dependency injections
    }

    public void giveAttendance(){
        //ToDo: should give attendance to a participant
    }

    public void addParticipant(){
        //ToDo: add a specific participant to an event
    }
}
