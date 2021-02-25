import java.util.Random;

// Each mission is allocated variable supplies of 
// fuel, thrusters, instruments, control systems and powerplants.
public class Mission implements Runnable{
    // Each mission can be represented using threads
    private String threadName;
    private Integer fuelLevel;
    private Integer thrusters;
    private Integer controlSystems;
    private Integer powerPlants;
    private String missionDestination;
    private String stage;

    Random random = new Random();
    Component missionComponent = new Component();

    public static void main(String[] args){
        Mission spaceMission = new Mission("Space Mission");
        Thread t = new Thread (spaceMission, spaceMission.threadName);
        t.start();
    }

    // The mission destination can be approximated as a function of the fuel load for the mission (ie more
    // fuel implies a mission to further locations in the solar system).
    public Mission(String name){
        this.threadName = name;
        this.fuelLevel = missionComponent.fuel();
        this.thrusters = missionComponent.thrusters();
        this.controlSystems = missionComponent.controlSystems();
        this.powerPlants = missionComponent.powerPlants();
        this.missionDestination = this.calculateMissionDestination();
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

    private String calculateMissionDestination(){
        // todo
        // The mission destination can be approximated as a function of the fuel load for the mission 
        // (ie more fuel implies a mission to further locations in the solar system).
    }

    // instant event.
    // 10% chance of failing.
    private void boostStage(){
        System.out.println(threadName + ": Starting Boost Stage");
        checkComponentFailure();

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo pass argument int months
    private void transitStage(){
        System.out.println(threadName + ": Starting Transit Stage");
        checkComponentFailure();

    }

    // instant event.
    // 10% chance of failing.
    private void landingStage(){
        System.out.println(threadName + ": Starting Landing Stage");
        checkComponentFailure();

    }

    // variable amounts of time to execute (in months)
    // 10% chance of failing.
    // todo passing parameter int months
    private void explorationStage(){
        System.out.println(threadName + ": Starting Exploration Stage");
        checkComponentFailure();

    }

    // simulate 10% chance of failure
    private boolean checkComponentFailure(){
        boolean check = false;
        System.out.println(threadName + ": Performing Component Check");

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
