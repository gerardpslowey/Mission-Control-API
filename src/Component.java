import java.util.concurrent.ThreadLocalRandom;
    
public class Component {
    
    // All mission components transmit reports (telemetry) on progress and instruments send data on a regular basis
    // this is limited by bandwidth and subject to increasing delays as the mission travels further away from Earth.  
    
    //====================================================================================================
    // 30% of reports require a command response and the mission is paused until that command is received.
    // Reports can be telemetry (100-10k bytes, frequent) or data (100k-100MB, periodic)

    // Each component should have a random report rate and size for the mission.

    // Each mission is allocated variable supplies of 
    // fuel, thrusters, instruments, control systems and powerplants.

    private Integer reportRate;
    private Integer size;

    public Component(){
        this.reportRate = setReportRate();            //TODO: Randomly allocated
        this.size = 5;                                  //TODO: Randomly allocated
    }
    
    public static int setReportRate() {
        final int MIN_LIMIT = 20;
        final int MAX_LIMIT = 200;
        return ThreadLocalRandom.current().nextInt(MIN_LIMIT, MAX_LIMIT);
    }

    public int getReportRate(){
        return reportRate;
    }

    public static int setSize() {
        final int MIN_LIMIT = 20;
        final int MAX_LIMIT = 200;
        return ThreadLocalRandom.current().nextInt(MIN_LIMIT, MAX_LIMIT);
    }

    public int getSize(){
        return size;
    }

    // When on a mission it is necessary for all mission components to transmit reports (telemetry ) on progress  
    // instruments send data on a regular basis, but this is limited by bandwidth 
    // subject to increasing delays as the mission travels further away from Earth.

    //  There are a variable number of types of commands and reports for each mission. 
    // Reports can be telemetry (100-10k bytes, frequent) or data (100k-100MB, periodic)
    public void sendMessage(String data, String bandwidth, Integer time){
        // TODO
    }
}
