package Models;

import Exceptions.*;
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

    /*
    * This method allows a participant to register for an event
    *
    * @param event the event in which the participant wants to register
    * @throws ParticipantAlreadyRegisteredException in case the participant is already registered in the event
    * @throws EventStartedException in case the event already started
    * */
    public void register(IEvent event) throws ParticipantServiceException, EventServiceException {
        if(isRegistered(event.getId()))
            throw new ParticipantAlreadyRegisteredException("This participant is already registered");
        registeredEvents.add(event);
        IAttendant attendant = new Attendant(this);
        event.addAttendant(attendant);
    }

    public void unregister(IEvent event) throws ParticipantServiceException, EventServiceException, AttendantServiceException {
        if(!isRegistered(event.getId()))
            throw new ParticipantNotRegisteredException("This participant is already bot registered");
        event.removeAttendant(getId());
    }
}
