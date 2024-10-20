package Interfaces;

public interface IParticipant {

    public void register(IEvent event);

    public void unregister(IEvent event) throws Exception;
}
