package Models;

import Interfaces.IEvent;
import Interfaces.IParticipant;

import java.util.ArrayList;

public class Participant extends Person implements IParticipant {

    private ArrayList<IEvent> registeredEvents;

    public Participant(String name, Gender gender, int age) {
        super(name, gender, age);
        registeredEvents = new ArrayList();
    }

    public void register(IEvent event){
        registeredEvents.add(event);
        Attendant attendant = new Attendant(this);
        event.addAttendant(attendant);
    }

    public void unregister(IEvent event) throws Exception {
        event.removeAttendance(getId());
    }
}
