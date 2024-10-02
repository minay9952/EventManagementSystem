package Models;

public class Attendant {

        private Participant attendant;
        private Boolean hasAttended;

        public Attendant(Participant participant){
                attendant = participant;
                hasAttended = false;
        }
}
