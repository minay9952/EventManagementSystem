package Interfaces;

import Models.Attendant;
import Models.Event;
import Models.Participant;

public interface IEvent {

    public String getId();
    public void giveAttendance(String attendantId) throws Exception;

    public void removeAttendance(String attendantId) throws Exception;

    public void addAttendant(Attendant attendant);

    public void removeAttendant(String attendantId) throws Exception;
}
