package Models;

import Interfaces.IAttendant;

public class Attendant implements IAttendant {

        private Participant attendant;
        private Boolean hasAttended;

        public Attendant(Participant participant){
                attendant = participant;
                hasAttended = false;
        }

        public String getId(){
                return attendant.getId();
        }

        public void setHasAttended(Boolean hasAttended) {
                this.hasAttended = hasAttended;
        }
}
