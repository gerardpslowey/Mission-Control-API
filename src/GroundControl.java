public class GroundControl {

    private static final int MIN_MISSION_COUNT = 10;
    private static long startTime = System.currentTimeMillis();


    
    public static void main(String[] args){
        // Each mission can be represented using threads
        // Thread t = new Thread (spaceMission, spaceMission.missionName);
        // t.start();


        int i = 1;
        while (i < 4) {
            
            //System.out.println("Fuel " +  component.fuel() );
            Mission spaceMission = new Mission("Space Mission");
            int destination = this.calculateMissionDestination(spaceMission);
            spaceMission.start();
            i++;

        // // simulate 10% chance of failure
        // boolean failure = Mission().checkComponentFailure(Mission missionName);
        // if(failure){
        //     produceSoftwarePatch();
    }

    // TODO decide the random ranges here
    public static void produceSoftwarePatch() {
        int numDays = random;
        int softwareSize = random;

        transmitSoftwareUpdate();
    }

    // Software upgrades must be transmitted from the mission controller.
    public static void transmitSoftwareUpdate(int ){
        // takes a variable number of days to develop
        // variable size in mb
        // related to the bandwidth queue
    }

    // 30% of reports require a command response and the mission is paused until that command is received. 
    public static void sendCommandResponse() {

    }

    
}
