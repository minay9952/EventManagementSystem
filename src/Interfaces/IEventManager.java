package Interfaces;

import Exceptions.*;
import Models.Gender;

public interface IEventManager {

    void addOrganizer (String name, Gender gender, int age) throws OrganizerServiceException;

    void removeOrganizer(String organizerId) throws OrganizerServiceException;

    void addParticipant(String name, Gender gender, int age) throws ParticipantServiceException;

    void removeParticipant(String participantId) throws ParticipantServiceException;
}
