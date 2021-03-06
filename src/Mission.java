import java.util.Random;
import java.util.Queue;

public class Mission implements Runnable {
    private Random random = new Random();

    private String missionID;
    private long startTime;

    private Integer destination;
    private Component fuelLevel;
    private Component thrusters;
    private Component controlSystems;
    private Component powerPlants;

    private String stage;
    private Queue network;

    // fuelLevel, thrusters, instruments, controlSystems, powerPlants
    Network comsNetwork = new Network();

    public Mission(String missionID, long startTime) {
        this.missionID = missionID;
        this.startTime = startTime;
        
        this.fuelLevel = new Component();
        this.thrusters = new Component();
        this.controlSystems = new Component();
        this.powerPlants = new Component();

        this.destination = fuelLevel.getSize(); //this.fuelLevel;              //TODO: The mission destination can be approximated as a function of the fuel load          
        this.network = comsNetwork.calculateBandwidth();
    }

    public void start(String threadName) {
        System.out.println("Starting " +  threadName);
        Thread t = new Thread(this, threadName);
        t.start ();
    }

    @Override
    public void run() {
        switch(stage) {
            case "Boost Stage":
                boostStage();
                break;

            case "Transit Stage":
                transitStage(5);        //TODO: variable amount of months ===> 1000 < x
                break;

            case "Landing Stage":
                landingStage();
                break;

            default:
                explorationStage(5);
                break;            
        }
    }

    public String getMissionId(){
        return missionID;
    }

    public long getStartTime(){
        return startTime;
    }

    // instant event.
    private void boostStage(){
        System.out.println(this.missionID + ": Starting Boost Stage");

        // 10% chance of failing.
        checkComponentFailure();
    }

    // variable amounts of time to execute (in months)
    private void transitStage(int months){
        System.out.println(this.missionID + ": Starting Transit Stage");

        // 10% chance of failing.
        checkComponentFailure();
    }

    // instant event.
    private void landingStage(){
        System.out.println(this.missionID + ": Starting Landing Stage");

        // 10% chance of failing.
        checkComponentFailure();

    }

    // variable amounts of time to execute (in months)
    private void explorationStage(Integer months){
        System.out.println(this.missionID + ": Starting Exploration Stage");
        
        // 10% chance of failing.
        checkComponentFailure();
    }

    private boolean checkComponentFailure(){                                        //TODO GroundControl might take this function
        boolean check = false;                                                      //TODO GC checks failure? Mission returns variable day and sofware size
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

    private boolean checkforResolution(){
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