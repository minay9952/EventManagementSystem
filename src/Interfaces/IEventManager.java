package Interfaces;

import Exceptions.OrganizerAlreadyExistException;
import Exceptions.OrganizerNotFoundException;
import Exceptions.ParticipantAlreadyExistException;
import Exceptions.ParticipantNotFoundException;
import Models.Gender;

public interface IEventManager {

    public void addOrganizer (String name, Gender gender, int age) throws OrganizerAlreadyExistException;

    public void removeOrganizer(String organizerId) throws OrganizerNotFoundException;

    public void addParticipant(String name, Gender gender, int age) throws ParticipantAlreadyExistException;

    public void removeParticipant(String participantId) throws ParticipantNotFoundException;
}
