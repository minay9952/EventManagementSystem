package Tests;

import Controller.EventManager;
import Exceptions.*;
import Models.Event;
import Models.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("Create new online event")
    public void createNewOnlineEvent(){
        try{
            String organizerId = eventManager.addOrganizer("Ahmed", Gender.MALE, 25);
            String eventId = eventManager.createOnlineEvent(organizerId, "EngineerX", LocalDateTime.of(2024, 12, 5, 12, 0), LocalDateTime.of(2024, 12, 5, 18, 0));
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    assertTrue(true, "Event added successfully");
                    return;
                }
            }
            fail("Event was not found in the list of events after adding it");
        }
        catch(OrganizerServiceException e){
            fail("an exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Create new online event with fake organizer")
    public void createNewOnlineEventWithFakeOrganizer(){
        assertThrows(OrganizerNotFoundException.class,
                () -> eventManager.createOnlineEvent("Fake ID", "EngineerX", LocalDateTime.of(2024, 12, 5, 12, 0), LocalDateTime.of(2024, 12, 5, 18, 0)),
                "An Event was successfully added with a fake organizer");
    }

    @Test
    @DisplayName("Create new onsite event")
    public void createNewOnsiteEvent(){
        try{
            String organizerId = eventManager.addOrganizer("Ahmed", Gender.MALE, 23);
            String eventId = eventManager.createOnsiteEvent(organizerId,"EngineerX", LocalDateTime.of(2024, 12, 5, 12, 0), LocalDateTime.of(2024, 12, 5, 18, 0), "New Cairo");
        }
        catch(OrganizerServiceException e){
            fail("an exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Create new onsite event with fake organizer")
    public void createNewOnsiteEventWithFakeOrganizer(){
        assertThrows(OrganizerNotFoundException.class,
                () -> eventManager.createOnsiteEvent("Fake ID", "EngineerX", LocalDateTime.of(2024, 12, 5, 12, 0), LocalDateTime.of(2024, 12, 5, 18, 0), "New Cairo"),
                "An Event was successfully added with a fake organizer");
    }

    @Test
    @DisplayName("Cancel an event")
    public void cancelEvent(){
        try{
            String organizerId = eventManager.addOrganizer("Ahmed", Gender.MALE, 23);
            String eventId = eventManager.createOnlineEvent(organizerId, "EngineerX", LocalDateTime.of(2024, 12, 5, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            eventManager.cancelEvent(organizerId, eventId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId))
                    fail("Event was found even after canceling it");
            }
            assertTrue(true, "Event canceled successfully");
        }
        catch(Exception e){
            fail("an exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Cancel an event with another organizer")
    public void cancelEventWithDifferentOrganizer(){
        try{
            String organizer1 = eventManager.addOrganizer("Ahmed", Gender.MALE, 23);
            String organizer2 = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer1, "EngineerX", LocalDateTime.of(2024, 12, 5, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            assertThrows(EventNotFoundException.class,
                    () -> eventManager.cancelEvent(organizer2, eventId),
                    "The event was cancel with a different organizer without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Cancel an event that already started")
    public void cancelAlreadyStartedEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 11, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            assertThrows(EventStartedException.class,
                    () -> eventManager.cancelEvent(organizer, eventId),
                    "The event was cancel although it already started without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add a new participant to an event")
    public void addNewParticipantToEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    Event event = eventManager.getEvents().get(i);
                    for(int j=0; j < event.getAttendants().size(); j++){
                        if(event.getAttendants().get(j).getId().equals(participantId)){
                            assertTrue(true, "participant added successfully");
                            return;
                        }
                    }
                    fail("Participant was not found in the attendants list after adding him");
                }
            }
            fail("Event not found after adding it");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add a participant with different event organizer")
    public void addParticipantWithDifferentOrganizer(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 11, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(OrganizerNotFoundException.class,
                    () -> eventManager.addParticipant("different ID", eventId, participantId),
                    "a participant was added to an event by another organizer without thrown an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add a participant to a non existing event")
    public void addParticipantToNonExistingEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventNotFoundException.class,
                    () -> eventManager.addParticipant(organizer, "eventId", participantId),
                    "a participant was added to a non existing event without thrown an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add a non existing participant to an event")
    public void addNonExistingParticipant(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 11, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            assertThrows(ParticipantNotFoundException.class,
                    () -> eventManager.addParticipant(organizer, eventId, "participant Id"),
                    "a non existing participant was added to an event without thrown an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Add a participant after the event already started")
    public void addParticipantToAlreadyStartedEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 11, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventStartedException.class,
                    () -> eventManager.addParticipant(organizer, eventId, participantId),
                    "Participant add after the event already started without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Remove a participant from an event")
    public void removeParticipantFromEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            eventManager.removeParticipant(organizer, eventId, participantId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    Event event = eventManager.getEvents().get(i);
                    for(int j=0; j < event.getAttendants().size(); j++){
                        if(event.getAttendants().get(j).getId().equals(participantId)){
                           fail("Participant was found after removing him without throwing an exception");
                        }
                    }
                    assertTrue(true, "Participant removed successfully");
                    return;
                }
            }
            fail("Event not found after adding it");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Remove a participant from an event with a non existing organizer")
    public void removeParticipantFromEventWithNonExistingOrganizer(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            assertThrows(OrganizerNotFoundException.class,
                    () -> eventManager.removeParticipant("non existing organizer", eventId, participantId),
                     "The participant was removed by a non existing organizer without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Remove a participant from a non existing event")
    public void removeParticipantFromNonExistingEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            assertThrows(EventNotFoundException.class,
                    () -> eventManager.removeParticipant(organizer, "event id", participantId),
                    "The participant was removed from a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Remove a non existing participant from an event")
    public void removeNonExistingParticipantFromEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            assertThrows(ParticipantNotFoundException.class,
                    () -> eventManager.removeParticipant(organizer, eventId, "participant ID"),
                    "A non existing participant was removed from an event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Remove a participant after the event already started")
    public void removeParticipantAfterEventStarted(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            assertThrows(EventStartedException.class,
                    () -> eventManager.removeParticipant(organizer, eventId, participantId),
                    "A non existing participant was removed from an event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Register a participant for an event")
    public void registerParticipantForEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.register(eventId, participantId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    Event event = eventManager.getEvents().get(i);
                    for(int j=0; j < event.getAttendants().size(); j++){
                        if(event.getAttendants().get(j).getId().equals(participantId)){
                            assertTrue(true, "participant added successfully");
                            return;
                        }
                    }
                    fail("Participant was not found in the attendants list after adding him");
                }
            }
            fail("Event not found after adding it");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Register an already registered participant for an event")
    public void registerRegisteredParticipant(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.register(eventId, participantId);
            assertThrows(ParticipantAlreadyRegisteredException.class,
                    () -> eventManager.register(eventId, participantId),
                    "Participant was registered twice for the same event without throwing an exception.");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Register a participant for a non existing event")
    public void registerParticipantForNonExistingEvent(){
        try{
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventNotFoundException.class ,
                    () -> eventManager.register("fake id", participantId),
                    "A participant was registered for a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Register a non existing participant")
    public void registerNonExistingParticipant(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 11, 12, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            assertThrows(ParticipantNotFoundException.class ,
                    () -> eventManager.register(eventId, "fake id"),
                    "A non existing participant was registered without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Register a participant after the event already started")
    public void registerParticipantAfterEventStarted(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2023,12,3, 3,0,0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventStartedException.class,
                    () -> eventManager.register(eventId, participantId),
                    "A participant was register after the event started without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Unregister a participant")
    public void unregisterParticipant(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024,12,3, 3,0,0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.register(eventId, participantId);
            eventManager.unregister(eventId, participantId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    Event event = eventManager.getEvents().get(i);
                    for(int j=0; j < event.getAttendants().size(); j++){
                        if(event.getAttendants().get(j).getId().equals(participantId)){
                            fail("Participant was registered in the event after unregistering him");
                            break;
                        }
                    }
                    assertTrue(true, "Participant was unregistered successfully");
                    return;
                }
            }
            fail("Event not found after adding it");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Unregister a non existing participant")
    public void unregisterNonExistingParticipant(){
        try {
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 3, 3, 0, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            assertThrows(ParticipantNotFoundException.class,
                    ()-> eventManager.unregister(eventId, "fake id"),
                    "A non existing participant was unregistered without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }
    @Test
    @DisplayName("Unregister participant from non existing event")
    public void unregisterParticipantFromNonExistingEvent(){
        try {
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventNotFoundException.class,
                    ()-> eventManager.unregister("fake id", participantId),
                    "A participant was unregistered from a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Unregister an already unregistered participant")
    public void unregisterUnregisteredParticipant(){
        try {
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.of(2024, 12, 3, 3, 0, 0), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(ParticipantNotRegisteredException.class,
                    () -> eventManager.unregister(eventId, participantId),
                    "An already unregistered participant was unregistered without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Unregister a participant after the event starts")
    public void unregisterParticipantAfterEventStarts(){
        try {
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.register(eventId, participantId);
            Thread.sleep(3000);
            assertThrows(EventStartedException.class,
                    () -> eventManager.unregister(eventId, participantId),
                    "A participant was unregistered after the event started without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("remove attendance form a participant")
    public void removeAttendance(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            eventManager.giveAttendance(organizer, eventId, participantId);
            eventManager.removeAttendance(organizer, eventId, participantId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    Event event = eventManager.getEvents().get(i);
                    for(int j=0; j < event.getAttendants().size(); j++){
                        if(event.getAttendants().get(j).getId().equals(participantId) && !event.getAttendants().get(j).getHasAttended()){
                            assertTrue(true, "Attendant attendance was removed successfully");
                            return;
                        }
                    }
                    fail("The participant was still marked as attended");
                }
            }
            fail("Event not found after adding it");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("remove attendance from a participant with a non existing organizer")
    public void removeAttendanceWithNonExistingOrganizer(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            eventManager.giveAttendance(organizer, eventId, participantId);
            assertThrows(OrganizerNotFoundException.class,
                    () -> eventManager.removeAttendance("fake id", eventId, participantId),
                    "The attendance was removed by a non existing organizer without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("remove attendance for a non existing event")
    public void removeAttendanceForNonExistingEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            eventManager.giveAttendance(organizer, eventId, participantId);
            assertThrows(EventNotFoundException.class,
                    () -> eventManager.removeAttendance(organizer, "fake id", participantId),
                    "The attendance was removed for a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("remove attendance for a not registered participant")
    public void removeAttendanceForUnregisteredParticipant(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            Thread.sleep(3000);
            assertThrows(AttendantNotFoundException.class,
                    () -> eventManager.removeAttendance(organizer, eventId, participantId),
                    "The attendance was removed from a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("remove attendance for an event that didn't start yet")
    public void removeAttendanceForNonStartedEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventNotStartedException.class,
                    () -> eventManager.removeAttendance(organizer, eventId, participantId),
                    "The attendance was removed for an event that didn't started yet without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("remove attendance for an event that already ended")
    public void removeAttendanceForEndedEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.now().plusSeconds(3));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3500);
            assertThrows(EventEndedException.class,
                    () -> eventManager.removeAttendance(organizer, eventId, participantId),
                    "The attendance was removed for an event that already ended without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("give attendance to a participant")
    public void giveAttendance(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            eventManager.giveAttendance(organizer, eventId, participantId);
            for(int i=0; i < eventManager.getEvents().size(); i++){
                if(eventManager.getEvents().get(i).getId().equals(eventId)){
                    Event event = eventManager.getEvents().get(i);
                    for(int j=0; j < event.getAttendants().size(); j++){
                        if(event.getAttendants().get(j).getId().equals(participantId) && event.getAttendants().get(j).getHasAttended()){
                            assertTrue(true, "Attendant was given attendance successfully");
                            return;
                        }
                    }
                    fail("The participant wasn't given the attendance");
                }
            }
            fail("Event not found after adding it");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("give attendance to a participant with a non existing organizer")
    public void giveAttendanceWithNonExistingOrganizer(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            assertThrows(OrganizerNotFoundException.class,
                    () -> eventManager.giveAttendance("fake id", eventId, participantId),
                    "The attendance was given by a non existing organizer without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("give attendance for a non existing event")
    public void giveAttendanceForNonExistingEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3000);
            assertThrows(EventNotFoundException.class,
                    () -> eventManager.giveAttendance(organizer, "fake id", participantId),
                    "The attendance was given for a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("give attendance for a not registered participant")
    public void giveAttendanceForUnregisteredParticipant(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            Thread.sleep(3000);
            assertThrows(AttendantNotFoundException.class,
                    () -> eventManager.giveAttendance(organizer, eventId, participantId),
                    "The attendance was given for a non existing event without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("give attendance for an event that didn't start yet")
    public void giveAttendanceForNonStartedEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.of(2024, 12, 5, 23, 0));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            assertThrows(EventNotStartedException.class,
                    () -> eventManager.giveAttendance(organizer, eventId, participantId),
                    "The attendance was given for an event that didn't started yet without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }

    @Test
    @DisplayName("give attendance for an event that already ended")
    public void giveAttendanceForEndedEvent(){
        try{
            String organizer = eventManager.addOrganizer("Mark", Gender.MALE, 24);
            String eventId = eventManager.createOnlineEvent(organizer, "EngineerX", LocalDateTime.now().plusSeconds(3), LocalDateTime.now().plusSeconds(3));
            String participantId = eventManager.addParticipant("Ahmed", Gender.MALE, 23);
            eventManager.addParticipant(organizer, eventId, participantId);
            Thread.sleep(3500);
            assertThrows(EventEndedException.class,
                    () -> eventManager.giveAttendance(organizer, eventId, participantId),
                    "The attendance was given for an event that already ended without throwing an exception");
        }
        catch(Exception e){
            fail("An exception was thrown with the following message " + e.getMessage());
        }
    }
}
