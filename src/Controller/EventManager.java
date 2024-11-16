package Controller;

import Exceptions.*;
import Interfaces.IEventManager;
import Models.Event;
import Models.Gender;
import Models.Organizer;
import Models.Participant;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventManager implements IEventManager {

    private ArrayList<Event> events;
    private ArrayList<Organizer> organizers;
    private ArrayList<Participant> participants;

    public EventManager(){
        events = new ArrayList<>();
        organizers = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Organizer> getOrganizers() {
        return organizers;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    /*
    * This method searches for an event by its ID
    *
    * @param eventId the ID of the event to be search for
    * @return the event in case found
    * @throws EventNotFoundException in case no event found with the provided ID
    * */
    private Event findEventById(String eventId) throws EventServiceException{
        for(int i=0; i < events.size(); i++){
            if(events.get(i).getId().equals(eventId))
                return events.get(i);
        }
        throw new EventNotFoundException("No event found with the following ID " + eventId);
    }

    /*
    * This method searches for an organizer by his name
    *
    * @param name the name of the organizer
    * @return the organizer in case found
    * @throws OrganizerNotFoundException in case no organizer found with the provided ID
    * */
    private Organizer findOrganizerByName(String name) throws OrganizerServiceException {
        for(int i=0; i < organizers.size(); i++){
            if(organizers.get(i).getName().equals(name))
                return organizers.get(i);
        }
        throw new OrganizerNotFoundException("There is no organizer with the name " + name);
    }

    /*
    * This method searches for an organizer by his ID
    *
    * @param organizerId the ID of the organizer to be searched for
    * @return the organizer in case found
    * @throws OrganizerNotFoundException in case no organizer was found with the provided ID
    * */
    private Organizer findOrganizerById(String organizerId) throws OrganizerServiceException{
        for(int i=0; i < organizers.size(); i++){
            if(organizers.get(i).getId().equals(organizerId))
                return organizers.get(i);
        }
        throw new OrganizerNotFoundException("There is no organizer with the id " + organizerId);
    }

    /*
     * This method searches for a participant by his name
     *
     * @param name the name of the participant to be searched for
     * @return the participant in case found
     * @throws ParticipantNotFoundException in case no participant was found with the provided name
     * */
    private Participant findParticipantByName(String name) throws ParticipantServiceException {
        for(int i=0; i < participants.size(); i++){
            if(participants.get(i).getName().equals(name)){
                return participants.get(i);
            }
        }
        throw new ParticipantNotFoundException("There is no participant with the name " + name);
    }

    /*
     * This method searches for a participant by his ID
     *
     * @param participantId the ID of the participant to be searched for
     * @return the participant in case found
     * @throws ParticipantNotFoundException in case no participant was found with the provided ID
     * */
    private Participant findParticipantById(String participantId) throws ParticipantServiceException{
        for(int i=0; i < participants.size(); i++){
            if(participants.get(i).getId().equals(participantId)){
                return participants.get(i);
            }
        }
        throw new ParticipantNotFoundException("There is no participant with the id " + participantId);
    }

    /*
    * This method creates a new organizer and adds him to the list of organizers
    *
    * @param name the name of the new organizer
    * @param gender the gender of the new organizer
    * @param age the age of the new organizer
    * @return the ID of the newly created organizer
    * @throws OrganizerAlreadyExistException in case an organizer already exists with the same name
    * */
    public String addOrganizer(String name, Gender gender, int age) throws OrganizerServiceException {
        try{
            findOrganizerByName(name);
            throw new OrganizerAlreadyExistException("There is an exiting organizer with the name " + name);
        }
        catch(OrganizerNotFoundException e){
            Organizer newOrganizer = new Organizer(name, gender, age);
            organizers.add(newOrganizer);
            return newOrganizer.getId();
        }
    }

    /*
     * This method removes an organizer from the list of organizers
     *
     * @param organizerID the ID of the organizer to be removed
     * @throws OrganizerNotFoundException in case no organizer is found with the provided ID
     * */
    public void removeOrganizer(String organizerId) throws OrganizerServiceException {
        Organizer organizer = findOrganizerById(organizerId);
        organizers.remove(organizer);
    }

    /*
     * This method creates a new participant and adds him to the list of participants
     *
     * @param name the name of the new participant
     * @param gender the gender of the new participant
     * @param age the age of the new participant
     * @return the ID of the newly created participant
     * @throws ParticipantAlreadyExistException in case a participant already exists with the same name
     * */
    public String addParticipant(String name, Gender gender, int age) throws ParticipantServiceException {
        try{
            findParticipantByName(name);
            throw new ParticipantAlreadyExistException("There is an existing participant with the name " + name);
        }
        catch(ParticipantNotFoundException e){
            Participant newParticipant = new Participant(name, gender, age);
            participants.add(newParticipant);
            return newParticipant.getId();
        }
    }

    /*
     * This method removes a participant from the list of participants
     *
     * @param participantId the ID of the participant to be removed
     * @throws ParticipantNotFoundException in case no participant is found with the provided ID
     * */
    public void removeParticipant(String participantId) throws ParticipantServiceException {
        Participant participant = findParticipantById(participantId);
        participants.remove(participant);
    }

    /*
    * This method creates a new event of type online and adds it to the list of events
    *
    * @param organizerId the ID of the organizer who will create the event
    * @param name the name of the event to be created
    * @param startDate the start date and time of the event to be created
    * @param endDate the end date and time of the event of be created
    * @return the ID of the newly created event
    * @throw OrganizerNotFoundException in case no organizer was found with the provided ID
    * */
    public String createOnlineEvent(String organizerId, String name, LocalDateTime startDate, LocalDateTime endDate) throws OrganizerServiceException{
        Organizer organizer = findOrganizerById(organizerId);
        Event event = organizer.createOnlineEvent(name, startDate, endDate);
        events.add(event);
        return event.getId();
    }

    /*
     * This method creates a new event of type onsite and adds it to the list of events
     *
     * @param organizerId the ID of the organizer who will create the event
     * @param name the name of the event to be created
     * @param startDate the start date and time of the event to be created
     * @param endDate the end date and time of the event of be created
     * @param location the location where the event will be hosted
     * @return the ID of the newly created event
     * @throw OrganizerNotFoundException in case no organizer was found with the provided ID
     * */
    public String createOnsiteEvent(String organizerId, String name, LocalDateTime startDate, LocalDateTime endDate, String location) throws OrganizerServiceException{
        Organizer organizer = findOrganizerById(organizerId);
        Event event = organizer.createOnsiteEvent(name, startDate, endDate, location);
        events.add(event);
        return event.getId();
    }

    /*
    * This method cancels an event
    *
    * @param organizerId the ID of the event organizer
    * @param eventId the ID of the event
    * @throws OrganizerNotFoundException in case no organizer was found with the provided ID
    * @throws Event NotFoundException in case no event was found with the provided ID
    * @throes EventStartedException in case the event already started
    * */
    public void cancelEvent(String organizerId, String eventId) throws EventServiceException, OrganizerServiceException{
        Event event = findEventById(eventId);
        Organizer organizer = findOrganizerById(organizerId);
        organizer.cancelEvent(eventId);
        events.remove(event);
    }

    /*
    * This method marks an attendant as attended in an event
    *
    * @param organizerId the ID of the organizer who will give the attendance
    * @param eventId the ID of the event to give the attendance for
    * @param attendantID the ID of the attendant who will be given the attendance
    * @throws OrganizerNoFoundException in case no organizer is found with the provided ID
    * @throws EventNotFoundException in case no event found with the provided ID
    * @throws AttendantNotFoundException in case no attendant found with the provided ID
    * @throws EventNotStartedException in case the event didn't start yet
    * @throws EventEndedException in case the event already ended
    * */
    public void giveAttendance(String organizerId, String eventId, String attendantId) throws EventServiceException, OrganizerServiceException, AttendantServiceException{
        findEventById(eventId);
        Organizer organizer = findOrganizerById(organizerId);
        organizer.giveAttendance(eventId, attendantId);
    }

    /*
     * This method removes the attendance given to an attendant before
     *
     * @param organizerId the ID of the organizer who will have his attendance removed
     * @param eventId the ID of the event to remove the attendance from
     * @param attendantID the ID of the attendant who will have his attendance removed
     * @throws OrganizerNoFoundException in case no organizer is found with the provided ID
     * @throws EventNotFoundException in case no event found with the provided ID
     * @throws AttendantNotFoundException in case no attendant found with the provided ID
     * @throws EventNotStartedException in case the event didn't start yet
     * @throws EventEndedException in case the event already ended
     * */
    public void removeAttendance(String organizerId, String eventId, String attendantId) throws EventServiceException, OrganizerServiceException, AttendantServiceException{
        findEventById(eventId);
        Organizer organizer = findOrganizerById(organizerId);
        organizer.removeAttendance(eventId, attendantId);
    }

    /*
    * This method adds a participant as an attendant for an event
    *
    * @param OrganizerId the ID of the organizer who will add the attendant
    * @param eventId the ID of the event for which the attendant will be added
    * @param participantID the ID of the participant who will be added in the event attendants
    * @throws OrganizerNotFoundException in case no organizer is found with the provided ID
    * @throws ParticipantNotFoundException in case no participant found with the provided ID
    * @throws EventNotFoundException in case no event found with the provided ID
    * @throws EventStartedException in case the event already started
    * */
    public void addParticipant(String organizerId, String eventId, String participantId) throws OrganizerServiceException, ParticipantServiceException, EventServiceException{
        findEventById(eventId);
        Organizer organizer = findOrganizerById(organizerId);
        Participant participant = findParticipantById(participantId);
        organizer.addParticipant(eventId, participant);
    }

    /*
     * This method removes a participant from the attendants of an event
     *
     * @param OrganizerId the ID of the organizer who will remove the attendant
     * @param eventId the ID of the event for which the attendant will be removed
     * @param participantID the ID of the participant who will be removed from the event attendants
     * @throws OrganizerNotFoundException in case no organizer is found with the provided ID
     * @throws ParticipantNotFoundException in case no participant found with the provided ID
     * @throws EventNotFoundException in case no event found with the provided ID
     * @throws EventStartedException in case the event already started
     * */
    public void removeParticipant(String organizerId, String eventId, String participantId) throws OrganizerServiceException, ParticipantServiceException, EventServiceException, AttendantServiceException{
        findEventById(eventId);
        findParticipantById(participantId);
        Organizer organizer = findOrganizerById(organizerId);
        organizer.removeParticipant(eventId, participantId);
    }

    /*
    * This method allows a participant to register for an event and get added to the attendants of the event
    *
    * @param eventId the ID of the event that the participant will register for
    * @param participantId the ID of the participant who will register for an event
    * @throws EventNotFoundException in case no event found with te provided ID
    * @throws ParticipantNotFoundException in case no participant found with the provided ID
    * @throws EventAlreadyStartedException in case the event was already started
    * @throws ParticipantAlreadyRegisteredException in case the participant was already registered for the event
    * */
    public void register(String eventId, String participantId) throws EventServiceException, ParticipantServiceException{
        Event event  = findEventById(eventId);
        Participant participant = findParticipantById(participantId);
        participant.register(event);
    }

    /*
     * This method allows a participant to unregister from an event and remove him from the event attendants
     *
     * @param eventId the ID of the event that the participant will unregister from
     * @param participantId the ID of the participant who will unregister from the event
     * @throws EventNotFoundException in case no event found with te provided ID
     * @throws ParticipantNotFoundException in case no participant found with the provided ID
     * @throws EventAlreadyStartedException in case the event was already started
     * @throws ParticipantNotRegisteredException in case the participant was already not registered in the event
     * */
    public void unregister(String eventId, String participantId) throws EventServiceException, ParticipantServiceException, AttendantServiceException{
        Event event = findEventById(eventId);
        Participant participant = findParticipantById(participantId);
        participant.unregister(event);
    }
}