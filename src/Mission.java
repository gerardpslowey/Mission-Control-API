import java.util.Random;
import java.util.ArrayList;

// Each mission is allocated variable supplies of 
// fuel, thrusters, instruments, control systems and powerplants.
public class Mission implements Runnable{
    private String missionName;
    private long startTime;
    private ArrayList<String> components;
    private String destination;
    private String network;
    private final int id;

    // private int fuelLevel;
    // private int thrusters;
    // private final int instruments;
    // private int controlSystems;
    // private int powerPlants;
    // private String stage;

    Random random = new Random();
    Component missionComponent = new Component();

    // The mission destination can be approximated as a function of the fuel load for the mission (ie more
    // fuel implies a mission to further locations in the solar system).
    public Mission(String name, long startTime, ArrayList<String> components, String destination, String network, int id){
        this.missionName = name;
        // this.fuelLevel = missionComponent.fuel();
        // this.thrusters = missionComponent.thrusters();
        // this.controlSystems = missionComponent.controlSystems();
        // this.powerPlants = missionComponent.powerPlants();
        // this.destination = this.calculateMissionDestination();
        this.startTime = startTime;
        this.components = components;
        this.destination = destination;
        this.network = network;
        this.id = id;
    }

    public static void main(String[] args){
        // Each mission can be represented using threads
        // Mission spaceMission = new Mission("Space Mission");
        // Thread t = new Thread (spaceMission, spaceMission.missionName);
        // t.start();
    }

    public void run(){
        switch(stage) {
            case "Boost Stage":
                boostStage();
                break;

            case "Transit Stage":
                transitStage();
                break;

            case "Landing Stage":
                landingStage();
                break;

            case "Exploration Stage":
                explorationStage();
                break;            
        }
    }

    public int getMissionId(){
        return this.id;
    }

    public double getStartTime(){
        return this.startTime;
    }

    // private String calculateMissionDestination(){
        // todo
        // The mission destination can be approximated as a function of the fuel load for the mission 
        // (ie more fuel implies a mission to further locations in the solar system).
    // }

    // instant event.
    // 10% chance of failing.
    private void boostStage(){
        System.out.println(missionName + ": Starting Boost Stage");
        checkComponentFailure();

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo pass argument int months
    private void transitStage(){
        System.out.println(missionName + ": Starting Transit Stage");
        checkComponentFailure();

    }

    // instant event.
    // 10% chance of failing.
    private void landingStage(){
        System.out.println(missionName + ": Starting Landing Stage");
        checkComponentFailure();

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo passing parameter int months
    private void explorationStage(){
        System.out.println(missionName + ": Starting Exploration Stage");
        checkComponentFailure();

    }

    // simulate 10% chance of failure
    private boolean checkComponentFailure(){
        boolean check = false;
        System.out.println(missionName + ": Performing Component Check");

        // no error occured
        if (random.nextInt(10) == 0) {
            System.out.println("Component check complete, no errors");    
        }
        else {
            System.out.println("Warning, component has Failed! Checking for resolution."); 
            checkforResolution();   
        }

        return check;
    }

    private boolean checkforResolution(){
        boolean resolved = false;

        // no error occured
        if (random.nextInt(25) == 0) {
            System.out.println("Component check complete, no errors");    
        }
        else {
            System.out.println("Warning, component has Failed! Checking for resolution."); 
            checkforResolution();   
        }

        return resolved;
    }
}