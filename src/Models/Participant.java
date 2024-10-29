package Models;

import Exceptions.ParticipantAlreadyRegisteredException;
import Exceptions.ParticipantNotRegisteredException;
import Interfaces.IAttendant;
import Interfaces.IEvent;
import Interfaces.IParticipant;

import java.util.ArrayList;

public class Participant extends Person implements IParticipant {

    private ArrayList<IEvent> registeredEvents;

    public Participant(String name, Gender gender, int age) {
        super(name, gender, age);
        registeredEvents = new ArrayList();
    }

    private Boolean isRegistered(String eventId){
        for(int i=0; i < registeredEvents.size(); i++){
            if(registeredEvents.get(i).getId().equals(eventId))
                return true;
        }
        return false;
    }

    public void register(IEvent event) throws Exception {
        if(isRegistered(event.getId()))
            throw new ParticipantAlreadyRegisteredException("This participant is already registered");
        registeredEvents.add(event);
        IAttendant attendant = new Attendant(this);
        event.addAttendant(attendant);
    }

    public void unregister(IEvent event) throws Exception {
        if(!isRegistered(event.getId()))
            throw new ParticipantNotRegisteredException("This participant is already bot registered");
        event.removeAttendant(getId());
    }
}
