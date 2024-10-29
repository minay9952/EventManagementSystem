package Interfaces;

import Exceptions.EventStartedException;
import Models.Attendant;
import Models.Event;
import Models.Participant;

import java.time.LocalDateTime;

public interface IEvent {

    public String getId();

    public LocalDateTime getStartDate();
    public void giveAttendance(String attendantId) throws Exception;

    public void removeAttendance(String attendantId) throws Exception;

    public void addAttendant(IAttendant attendant) throws Exception;

    public void removeAttendant(String attendantId) throws Exception;

}
