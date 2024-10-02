package Models;

import java.util.ArrayList;

public class Participant extends Person{

    private ArrayList<Event> registeredEvents;

    public Participant(String name, Gender gender, int age) {
        super(name, gender, age);
        registeredEvents = new ArrayList();
    }

    public void register(){
        //ToDo: resister for an event
    }
}
