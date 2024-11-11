package Controller;

import Exceptions.*;
import Interfaces.IEventManager;
import Models.Event;
import Models.Gender;
import Models.Organizer;
import Models.Participant;

import java.util.ArrayList;

public class EventManger implements IEventManager {

    private ArrayList<Event> Events;
    private ArrayList<Organizer> organizers;
    private ArrayList<Participant> participants;

    public EventManger(){
        Events = new ArrayList<>();
        organizers = new ArrayList<>();
        participants = new ArrayList<>();
    }

    private Organizer findOrganizerByName(String name) throws OrganizerServiceException {
        for(int i=0; i < organizers.size(); i++){
            if(organizers.get(i).getName().equals(name))
                return organizers.get(i);
        }
        throw new OrganizerNotFoundException("There is no organizer with the name " + name);
    }

    private Organizer findOrganizerById(String organizerId) throws OrganizerServiceException{
        for(int i=0; i < organizers.size(); i++){
            if(organizers.get(i).getId().equals(organizerId))
                return organizers.get(i);
        }
        throw new OrganizerNotFoundException("There is no organizer with the id " + organizerId);
    }

    private Participant findParticipantByName(String name) throws ParticipantServiceException {
        for(int i=0; i < participants.size(); i++){
            if(participants.get(i).getName().equals(name)){
                return participants.get(i);
            }
        }
        throw new ParticipantNotFoundException("There is no participant with the name " + name);
    }

    private Participant findParticipantById(String participantId) throws ParticipantServiceException{
        for(int i=0; i < participants.size(); i++){
            if(participants.get(i).getId().equals(participantId)){
                return participants.get(i);
            }
        }
        throw new ParticipantNotFoundException("There is no participant with the id " + participantId);
    }

    public void addOrganizer(String name, Gender gender, int age) throws OrganizerServiceException {
        try{
            findOrganizerByName(name);
            throw new OrganizerAlreadyExistException("There is an exiting organizer with the name " + name);
        }
        catch(OrganizerNotFoundException e){
            Organizer newOrganizer = new Organizer(name, gender, age);
            organizers.add(newOrganizer);
        }
    }

    public void removeOrganizer(String organizerId) throws OrganizerServiceException {
        Organizer organizer = findOrganizerById(organizerId);
        organizers.remove(organizer);
    }

    public void addParticipant(String name, Gender gender, int age) throws ParticipantServiceException {
        try{
            findParticipantByName(name);
            throw new ParticipantAlreadyExistException("There is an existing participant with the name " + name);
        }
        catch(ParticipantNotFoundException e){
            Participant newParticipant = new Participant(name, gender, age);
            participants.add(newParticipant);
        }
    }

    public void removeParticipant(String participantId) throws ParticipantServiceException {
        Participant participant = findParticipantById(participantId);
        participants.remove(participant);
    }
}
