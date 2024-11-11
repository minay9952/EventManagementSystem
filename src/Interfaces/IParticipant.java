package Interfaces;

import Exceptions.AttendantServiceException;
import Exceptions.EventServiceException;
import Exceptions.ParticipantServiceException;

public interface IParticipant {

    void register(IEvent event) throws ParticipantServiceException, EventServiceException;

    void unregister(IEvent event) throws ParticipantServiceException, EventServiceException, AttendantServiceException;
}
