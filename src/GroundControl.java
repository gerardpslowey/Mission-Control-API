import java.time.LocalTime;

public class GroundControl {

    // Each "mission" should be generated with a random start time, components list, destination, network and
    // have a unique ID for identification purposes.

    private static final int MIN_MISSION_COUNT = 10;

    public GroundControl(){
        // need these variables
        // this.startTime = startTime;
        // this.components = components;
        // this.destination = destination;
        // this.network = network;
        // this.id = id;
    }

    // execution order
    public static void main(String[] args){
        // Each mission can be represented using threads
        // Thread t = new Thread (spaceMission, spaceMission.missionName);
        // t.start();

        LocalTime currentTime = java.time.LocalTime.now();

        int i = 1;
        while (i < 4) {
            
            //System.out.println("Fuel " +  component.fuel() );
            Mission spaceMission = new Mission("Space Mission");
            int destination = this.calculateMissionDestination(spaceMission);
            spaceMission.start();
            i++;

    }

    private int calculateMissionDestination(Mission spaceMission){
        // todo
        // The mission destination can be approximated as a function of the fuel load for the mission 
        // (ie more fuel implies a mission to further locations in the solar system).
    }

    // simulate 10% chance of failure
    boolean failure = Mission().checkComponentFailure(Mission missionName);
    if(failure){
        int numDays = random;
        int softwareSize = random;
        transmitSoftwareUpdate(numDays, softwareSize);
    }

    // Software upgrades must be transmitted from the mission controller.
    public static void transmitSoftwareUpdate(){
        //todo
        // takes a variable number of days to develop
        // variable size in mb
        // related to the bandwidth queue
    }
}
