import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable; 
import java.util.concurrent.Future; 
import java.util.concurrent.ExecutionException;


import utils.SoftwareUpdater;
import network.Network;
import utils.SimulateTimeAmount;

public class Mission implements Runnable {

    private String id;
    private long startTime;
    private String stage = "launch";
    private boolean missionInProgress = true;
    private Component[] components = new Component[5];

    private int destination;
    Network network;
    ExecutorService componentPool = Executors.newFixedThreadPool(5);


    public Mission(String id, long startTime){
        this.id = id;
        this.startTime = startTime;

        this.network = new Network();
        components[0] = new Component("fuel", this.network, 100 + 1);
        components[1] = new Component("thrusters", this.network, 4 + 1);
        components[2] = new Component("powerplants", this.network, 100 + 1);
        components[3] = new Component("controlSystems", this.network, 10 + 1);
        components[4] = new Component("instruments", this.network, 25 + 1);

        this.destination = components[0].getSize(); //fuel amount.
    }

    @Override
    public void run() {
        System.out.println(id + " created.");
        System.out.printf("%s is booting up in %s day(s).%n", id, startTime / 30);

        System.out.println(id + " destination = " + destination);

        for(int i = 0; i < 5; i++) {
			componentPool.execute(components[i]);
		}

        // System.out.println(components[0].getID());

        try{ 
            Thread.sleep(startTime); 
        } catch (InterruptedException e) {Thread.currentThread().interrupt();}

        while(missionInProgress){
            changeStage();
        }
        componentPool.shutdown();
        
        if(stage.isEmpty()){
            System.out.printf("%s has been successful!%n", id);
        }
    }

    public void changeStage(){
        int journeyTime = SimulateTimeAmount.compute(1001, 10000 + 1);
        switch(stage) {
            case "launch":
                //if no failures
                if(checkRunning()){
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "transit";
                } else {
                    missionInProgress = false;
                }
                break;

            case "transit":
                if(checkRunning()){
                    simulateJourneyTime(journeyTime);
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "landing";
                } else {
                    missionInProgress = false;
                }
                break;

            case "landing":
                if(checkRunning()){
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "explore";
                } else {
                    missionInProgress = false;
                }
                break;

            case "explore":
                if(checkRunning()){
                    simulateJourneyTime(journeyTime);
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "";
                }
                missionInProgress = false;
                break;

            default:
                System.out.println("Invalid Argument: " + stage);
                break;    
        }
    }
    private void simulateJourneyTime(int journeyTime){
        System.out.printf("%s in %s stage for %s month(s)!%n", id, stage, journeyTime / 1000);
        try{ 
            Thread.sleep(journeyTime); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private boolean checkRunning(){
        boolean success = true;

        // 10% chance of failurebound
        int failTen = ThreadLocalRandom.current().nextInt(1, 10+1);   // TODO add to utils
        if(failTen == 1){
            System.out.printf("!! %s system failure during %s! Request fix from GroundControl.%n", id, stage);
            success = fixSoftwareFailure();
        }
        return success;
    }

    private boolean fixSoftwareFailure(){

        //Update takes a few days
        Callable<Boolean> updater = new SoftwareUpdater(network);
        Future<Boolean> fixed = componentPool.submit(updater);
        boolean success;

        try {
            success = fixed.get().booleanValue();
            if(!success){
                System.out.printf("XX %s upgrade has failed during %s. %1$s aborted.%n", id, stage);
            }
            else{
                SoftwareUpdater.showProgress();
                System.out.printf("++ %s software upgrade successfully applied.%n", id);
            }
            return success;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();	
        }
    return false;
    } 

    public String getStage(){
        return this.stage; 
    }
}
