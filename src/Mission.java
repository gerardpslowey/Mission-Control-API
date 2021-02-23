import java.util.ArrayList;

public class Mission {

    //Each "mission" should be generated with a random start time, components list, destination, network and
    //have a unique ID for identification purposes.

    private long startTime;
    private ArrayList<String> components;
    private String destination;
    private String network;
    private final int uid;
    //====================================================================================================
    // Each mission is allocated variable supplies of fuel, 
    // thrusters, instruments, control systems and powerplants.

    private final int fuel;
    private final int thrusters;
    private final int instruments;
    private final int controlSystem;
    private final int powerPlant;

    public Mission(long startTime, ArrayList<String> components, String destination, String network, int uid){
        this.startTime = startTime;
        this.components = components;
        this.destination = destination;
        this.network = network;
        this.uid = uid;
    }

    public int getMissionId(){
        return this.uid;
    }

    public double getStartTime(){
        return this.startTime;
    }


    
    //====================================================================================================
    // Each mission has boost stage, an interplanetary transit stage,
    // an entry/landing stage and an exploration (rover) stage.

    public void boostStage(){

        // instant event.
        //10% chance of failing.
    }
    

    public void landingStage(){

        // instant event.
        //10% chance of failing.
    }
    

    public int transitStage(int time){

        // variable amounts of time to execute (in months)
        //10% chance of failing.
    }
    

    public void explorationStage(int time){

        // variable amounts of time to execute (in months)
        //10% chance of failing.
    }
    

    //====================================================================================================
    //The mission destination can be approximated as a function of the fuel load for the mission (ie more
    // fuel implies a mission to further locations in the solar system).
}
