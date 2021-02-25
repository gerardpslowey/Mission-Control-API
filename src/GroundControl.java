public class GroundControl {

    // Each "mission" should be generated with a random start time, components list, destination, network and
    // have a unique ID for identification purposes.

    private static final int MIN_MISSION_COUNT = 10;

    public GroundControl(){

    }

    // execution order
    public static void main(String[] args){
        // todo
    }

    // simulate 10% chance of failure
    boolean failure = Mission().checkComponentFailure(Mission missionName);
    if(failure){
        int numDays = random;
        int softwareSize = random;
        transmitSoftwareUpdate(numDays, softwareSize);
    }


    public static void transmitSoftwareUpdate(){
        //todo
        // takes a variable number of days to develop
        // variable size in mb
        // related to the bandwidth queue
    }
}
