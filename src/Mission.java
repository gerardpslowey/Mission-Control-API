import java.util.Random;
import java.util.ArrayList;
import java.util.Queue;

public class Mission implements Runnable{
    private Random random = new Random();

    private String missionName;
    private long startTime;
    private ArrayList<String> components;
    private int destination;

    private static volatile int fuelLevel;               //TODO: This should be static as it needs to be topped up and changed often.
    private static int thrusters;
    private static int controlSystems;
    private static int powerPlants;
    
    private String stage;
    private Queue network;
    private final int id;

    // fuelLevel, thrusters, instruments, controlSystems, powerPlants
    Component missionComponent = new Component();
    Network comsNetwork = new Network();

    // The mission destination can be approximated as a function of the fuel load for the mission (ie more
    // fuel implies a mission to further locations in the solar system).
    public Mission(long startTime, int destination, int id){
        this.id = id;
        this.startTime = startTime;
        this.destination = destination;

        fuelLevel = missionComponent.fuel();
        thrusters = missionComponent.thrusters();
        controlSystems = missionComponent.controlSystems();
        powerPlants = missionComponent.powerPlants();

        network = comsNetwork.calculateBandwidth();
    }

    public static void main(String[] args){
        // TODO
    }

    public void start(String threadName) {
        System.out.println("Starting " +  threadName );
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
        return this.id;
    }

    public double getStartTime(){
        return this.startTime;
    }



    // instant event.
    // 10% chance of failing.
    private void boostStage(Mission missionID){
        System.out.println(this.id + ": Starting Boost Stage");
        checkComponentFailure(missionID);

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo pass argument int months
    private void transitStage(Mission missionID){
        System.out.println(this.id + ": Starting Transit Stage");
        checkComponentFailure(missionID);

    }

    // instant event.
    // 10% chance of failing.
    private void landingStage(Mission missionID){
        System.out.println(this.id + ": Starting Landing Stage");
        checkComponentFailure(missionID);

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo passing parameter int months
    private void explorationStage(Mission missionID){
        System.out.println(this.id + ": Starting Exploration Stage");
        checkComponentFailure(missionID);

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
}