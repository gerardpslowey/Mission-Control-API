import java.util.Random;
import java.util.Queue;

public class Mission implements Runnable {
    private Random random = new Random();

    private String id;
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

    public Mission(String id, long startTime) {
        this.id = id;
        this.startTime = startTime;
        
        this.fuelLevel = new Component("fuel");
        this.thrusters = new Component("thrusters");
        this.controlSystems = new Component("controlSys");
        this.powerPlants = new Component("powerPlant");

        this.destination = fuelLevel.getSize(); //this.fuelLevel;              //TODO: The mission destination can be approximated as a function of the fuel load          
        this.network = comsNetwork.calculateBandwidth();
        this.stage = "boost";
    }

    @Override
    public void run(){
        System.out.println("Starting " +  id);
        //changeStage
        //checkFailure
        //requestSoftwarePatch
        //sendCommandResponse
        
    }


    public void changeStage() {
        switch(stage) {
            case "boost": case "b":
                boostStage();
                break;

            case "transit": case "t":
                transitStage(5);        //TODO: variable amount of months ===> 1000 < x
                break;

            case "landing": case "l":
                landingStage();
                break;

            case "explore": case "e":
                explorationStage(5);
                break;    
            default:
            System.out.println("Invalid Argument: " + stage);       
        }
    }

    public String getid(){
        return id;
    }

    public long getStartTime(){
        return startTime;
    }

    // instant event.
    private void boostStage(){
        System.out.println(this.id + ": Starting Boost Stage");

        // 10% chance of failing.
        checkComponentFailure();
        changeMissionStage();
    }

    // variable amounts of time to execute (in months)
    private void transitStage(int months){
        System.out.println(this.id + ": Starting Transit Stage");

        // 10% chance of failing.
        checkComponentFailure();
        changeMissionStage();
    }

    // instant event.
    private void landingStage(){
        System.out.println(this.id + ": Starting Landing Stage");

        // 10% chance of failing.
        checkComponentFailure();
        changeMissionStage();

    }

    // variable amounts of time to execute (in months)
    private void explorationStage(Integer months){
        System.out.println(this.id + ": Starting Exploration Stage");
        
        // 10% chance of failing.
        checkComponentFailure();
        changeMissionStage();
    }

    private boolean checkComponentFailure(){                                        //TODO GroundControl might take this function
        boolean check = false;                                                      //TODO GC checks failure? Mission returns variable day and sofware size
        System.out.println(id + ": Performing Component Check");

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
    public void changeMissionStage(){
        // TODO
    }
}