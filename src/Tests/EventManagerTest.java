package Tests;

import Controller.EventManager;
import Exceptions.OrganizerAlreadyExistException;
import Exceptions.OrganizerServiceException;
import Exceptions.ParticipantAlreadyExistException;
import Exceptions.ParticipantServiceException;
import Models.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Event Manager Test Class")
public class EventManagerTest {

    private EventManager eventManager;

    @BeforeEach
    public void initializeTests(){
        eventManager = new EventManager();
    }

    @Test
    @DisplayName("Add an organizer")
    public void addOrganizer(){
        try {
            String organizerId = eventManager.addOrganizer("Ahmed", Gender.MALE, 25);
            for(int i=0; i < eventManager.getOrganizers().size(); i++){
                if(eventManager.getOrganizers().get(i).getId().equals(organizerId)) {
                    assertTrue(true, "Organizer added successfully");
                    return;
                }
            }
            fail("Organizer wasn't found after adding him");
        }
        catch(OrganizerServiceException e){
            fail("an exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add an existing organizer")
    public void addExistingOrganizer(){
        try{
            eventManager.addOrganizer("Ahmed", Gender.MALE, 25);
            assertThrows(OrganizerAlreadyExistException.class, () -> {
                eventManager.addOrganizer("Ahmed", Gender.MALE, 25);
            }, "An existing organizer was successfully added");
        }
        catch(OrganizerServiceException e){
            fail("an exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add a Participant")
    public void addParticipant(){
        try{
            String participantId = eventManager.addParticipant("Ron", Gender.MALE, 23);
            for(int i=0; i < eventManager.getParticipants().size(); i++){
                if(eventManager.getParticipants().get(i).getId().equals(participantId)){
                    assertTrue(true, "participant added successfully");
                    return;
                }
            }
            fail("the participant wasn't found after adding him");
        }
        catch (ParticipantServiceException e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add an existing participant")
    public void addExistingParticipant(){
        try{
            eventManager.addParticipant("Ron", Gender.MALE, 23);
            assertThrows(ParticipantAlreadyExistException.class,
                    () -> eventManager.addParticipant("Ron", Gender.MALE, 23),
                    "An existing participant was added successfully");
        }
        catch(ParticipantServiceException e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }
}
