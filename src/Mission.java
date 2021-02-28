import java.util.Random;
import java.util.ArrayList;
import java.util.Queue;

public class Mission implements Runnable{
    private Random random = new Random();

    private int missionID;
    private long startTime;
    private ArrayList<String> components;
    private int destination;
    private int fuelLevel;
    private int thrusters;
    private int controlSystems;
    private int powerPlants;
    private String stage;
    private Queue network;

    // fuelLevel, thrusters, instruments, controlSystems, powerPlants
    Component missionComponent = new Component();
    Network comsNetwork = new Network();

    // The mission destination can be approximated as a function of the fuel load for the mission (ie more
    // fuel implies a mission to further locations in the solar system).
    public Mission(int missionID, long startTime, int destination) {
        this.missionID = missionID;
        this.startTime = startTime;
        this.fuelLevel = missionComponent.fuel();
        this.thrusters = missionComponent.thrusters();
        this.controlSystems = missionComponent.controlSystems();
        this.powerPlants = missionComponent.powerPlants();
        this.destination = this.calculateMissionDestination(this);
        this.network = comsNetwork.calculateBandwidth();
    }

    public static void main(String[] args){
        // TODO
    }

    public void start(String threadName) {
        System.out.println("Starting " +  threadName);
         
        Thread t = new Thread(this, threadName);
        t.start ();
    }

    public void run(Mission missionID) {
        switch(stage) {
            case "Boost Stage":
                boostStage(missionID);
                break;

            case "Transit Stage":
                transitStage(missionID);
                break;

            case "Landing Stage":
                landingStage(missionID);
                break;

            case "Exploration Stage":
                explorationStage(missionID);
                break;            
        }
    }

    public int getMissionId(){
        return this.missionID;
    }

    public double getStartTime(){
        return this.startTime;
    }

    // instant event.
    // 10% chance of failing.
    private void boostStage(Mission missionID){
        System.out.println(this.missionID + ": Starting Boost Stage");
        checkComponentFailure(missionID);

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo pass argument int months
    private void transitStage(Mission missionID){
        System.out.println(this.missionID + ": Starting Transit Stage");
        checkComponentFailure(missionID);

    }

    // instant event.
    // 10% chance of failing.
    private void landingStage(Mission missionID){
        System.out.println(this.missionID + ": Starting Landing Stage");
        checkComponentFailure(missionID);

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo passing parameter int months
    private void explorationStage(Mission missionID){
        System.out.println(this.missionID + ": Starting Exploration Stage");
        checkComponentFailure(missionID);
    }

    private int calculateMissionDestination(Mission spaceMission){
        // The mission destination can be approximated as a function of the fuel load for the mission 
        // (ie more fuel implies a mission to further locations in the solar system).
        int missionFuelLevel = spaceMission.fuelLevel;

        // always keep a fuel reserve of 25% of fuelLevel
        int reserveFuel = (int)(missionFuelLevel*(75.0f/100.0f));
        int usableFuel = missionFuelLevel - reserveFuel;

        return usableFuel / 2;
    }

    private boolean checkComponentFailure(Mission missionID){                               //TODO GroundControl might take this function
        boolean check = false;                                                                //TODO GC checks failure? Mission returns variable day and sofware size
        System.out.println(missionID + ": Performing Component Check");

        // no error occured
        if (random.nextInt(10) == 0) {
            System.out.println("Component check complete, no errors"); 
        }
        else {
            System.out.println("Warning, component has Failed! Checking for resolution."); 
            // try to recover with 25% chance
            check = true;
        }

        return check;
    }

    private boolean checkforResolution(Mission missionID){
        boolean resolved = false;

        // no error occured
        if (random.nextInt(100) < 25) {                                                      //TODO 25% chance of success.

            //
            System.out.println("Component check complete, no errors");
            // restart mission
        }
        else {
            System.out.println("Warning, component has Failed! Checking for resolution."); 
        }

        return resolved;
    }

    // A variable burst of reports and commands are sent at the transition between mission stages
    public void changeMissionStage(Mission missionID){
        // TODO
    }
}