package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable; 
import java.util.concurrent.Future; 
import java.util.concurrent.ExecutionException;

import utils.SoftwareUpdater;
import utils.SimulateRandomAmountOf;

public class Mission implements Runnable {

    // Missions given a unique ID for identification pruposes
    private String id;
    private long startTime;
    private String stage = "launch";
    private boolean missionInProgress = true;
    private Component[] components = new Component[5];

    private int destination;

    //  communication networks for a mission are a shared resource used by all mission components
    // each mission has its own network
    private Network network;
    private String[] map;
    ExecutorService componentPool = Executors.newFixedThreadPool(5);


    // Depending on the mission target, each mission must be allocated variable supplies of 
    // fuel, thrusters, instruments, control systems and powerplants
    public Mission(String id, long startTime){
        this.id = id;
        // missions are generated with a random start time
        this.startTime = startTime;
        // each mission has its own network
        this.network = new Network();
        // construct the mission vehicles from components,
        map = new String[] {"fuel", "thrusters", "powerplants", "controlSystems", "instruments"};

        for (int i = 0; i < map.length; i++){
            components[i] = new Component(map[i], this.network, this);
        }

        // mission destination can be approximated as a function of the fuel load for the mission
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

        try{ 
            // When waiting a mission sleeps
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

    public Network getNetwork(){
        return this.network;
    }

    public boolean getMissionProgress(){
        return this.missionInProgress; 
    }

    private static int simulateMissionDistance() {
        // Hard code mission destination distance for the moment
        final int distance = 100;
        // TODO
        return distance;
    } 

    // move missions along their stages
    public void changeStage(){
        int journeyTime = SimulateRandomAmountOf.months();

        // mission consists of boost stage, an interplanetary transit stage, 
        // an entry/landing stage and an exploration (rover) stage
        switch(stage) {
            case "launch":
                //if no failures
                if(checkRunning()){
                    // effectively instant events
                    printSuccessStatus(id, stage);
                    burstOfReports();
                    stage = "transit";
                } else {
                    missionInProgress = false;
                }
                break;

            case "transit":
                if(checkRunning()){
                    // takes variable amounts of time to execute (in months)
                    simulateJourneyTime(journeyTime);
                    printSuccessStatus(id, stage);
                    burstOfReports();
                    stage = "landing";
                } else {
                    missionInProgress = false;
                }
                break;

            case "landing":
                if(checkRunning()){
                    // effectively instant events
                    printSuccessStatus(id, stage);
                    burstOfReports();
                    stage = "explore";
                } else {
                    missionInProgress = false;
                }
                break;

            case "explore":
                if(checkRunning()){
                    // takes variable amounts of time to execute (in months)
                    simulateJourneyTime(journeyTime);
                    printSuccessStatus(id, stage);
                    burstOfReports();
                    stage = "";
                }
                missionInProgress = false;
                break;

            default:
                System.out.println("Invalid Argument: " + stage);
                break;    
        }
    }

    // send reports
    // A variable burst of reports and commands are sent at the transition between mission stages.
    private void burstOfReports(){
        // There are a variable number of types of commands and reports for each mission
        int reports = SimulateRandomAmountOf.reports();                                       //TODO: BURST REPORT AFTER EACH STAGE.
        System.out.println(reports);
        // network.transmit(reports);
        // int commands = GroundControl.receiveBurstReports(reports, this.network);              //TODO: REPORTS ARE EITHER TELEMETRY OR DATA

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
    
    // Check for failures
    // Each stage has at least a 10% chance of failing.
    private boolean checkRunning(){
        boolean success = true;

        int failTen = SimulateRandomAmountOf.chance();
        if(failTen == 1){
            System.out.printf("!! %s system failure during %s! Request fix from GroundControl.%n", id, stage);
            success = fixSoftwareFailure();
        }
        return success;
    }

    // 25% of failures can be recovered from by sending a software upgrade
    private boolean fixSoftwareFailure(){

        Callable<Boolean> updater = new SoftwareUpdater(network);
        Future<Boolean> fixed = componentPool.submit(updater);
        boolean success;

        try {
            success = fixed.get().booleanValue();
            if(!success){
                System.out.printf("XX %s upgrade has failed during %s. %1$s aborted.%n", id, stage);
            }
            else{
                SoftwareUpdater.showUpdateProgress();
                System.out.printf("++ %s software upgrade successfully applied.%n", id);
            }
            return success;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();	
        }
    return false;
    }
}
