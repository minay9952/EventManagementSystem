package Interfaces;

public interface IParticipant {

    public void register(IEvent event) throws Exception;

    public void unregister(IEvent event) throws Exception;
}
