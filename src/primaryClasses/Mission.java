package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable; 
import java.util.concurrent.Future; 
import java.util.concurrent.ExecutionException;

import utils.SoftwareUpdater;
import utils.SimulateRandomAmountOf;

public class Mission implements Runnable {

    private String id;
    private long startTime;
    private String stage = "launch";
    private boolean missionInProgress = true;
    private Component[] components = new Component[5];

    private int destination;
    private Network network;
    private String[] map;
    ExecutorService componentPool = Executors.newFixedThreadPool(5);


    public Mission(String id, long startTime){
        this.id = id;
        this.startTime = startTime;

        this.network = new Network();
        map = new String[] {"fuel", "thrusters", "powerplants", "controlSystems", "instruments"};

        for (int i = 0; i < map.length; i++){
            components[i] = new Component(map[i], this.network);
        }

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
        
        if(stage.isEmpty()){
            System.out.printf("%s has been successful!%n", id);
        }
        componentPool.shutdown();
    }

    public String getStage(){
        return this.stage; 
    }

    public void changeStage(){
        int journeyTime = SimulateRandomAmountOf.months();
        switch(stage) {
            case "launch":
                //if no failures
                if(checkRunning()){
                    printSuccessStatus(id, stage);
                    stage = "transit";
                } else {
                    missionInProgress = false;
                }
                break;

            case "transit":
                if(checkRunning()){
                    simulateJourneyTime(journeyTime);
                    stage = "landing";
                } else {
                    missionInProgress = false;
                }
                break;

            case "landing":
                if(checkRunning()){
                    printSuccessStatus(id, stage);
                    stage = "explore";
                } else {
                    missionInProgress = false;
                }
                break;

            case "explore":
                if(checkRunning()){
                    simulateJourneyTime(journeyTime);
                    printSuccessStatus(id, stage);
                    stage = "";
                }
                missionInProgress = false;
                break;

            default:
                System.out.println("Invalid Argument: " + stage);
                break;    
        }
    }

    private void printSuccessStatus(String id, String stage){
        System.out.printf("%s had no system failures during %s.%n", id, stage);

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
        int failTen = SimulateRandomAmountOf.chance();
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
}
