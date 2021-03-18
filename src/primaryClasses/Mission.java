package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CountDownLatch;
import dataTypes.*;
import utils.SimulateRandomAmountOf;

public class Mission implements Runnable {

    // Missions given a unique ID for identification pruposes
    private String id;
    private long startTime;
    private String stage = "launch";
    private boolean missionInProgress = true;
    private Component[] components = new Component[5];
    private CountDownLatch countDownLatch;

    private int destination;

    // communication networks for a mission are a shared resource used by all mission components
    // each mission has its own network
    private Network network;
    private String[] map;
    ExecutorService componentPool = Executors.newFixedThreadPool(20);


    // Depending on the mission target, each mission must be allocated variable supplies of 
    // fuel, thrusters, instruments, control systems and powerplants
    public Mission(String id, long startTime, CountDownLatch countDownLatch){
        this.id = id;
        // missions are generated with a random start time
        this.startTime = startTime;
        // each mission has its own network
        this.network = new Network("Network " + id);
        // construct the mission vehicles from components,
        map = new String[] {"fuel", "thrusters", "powerplants", "controlSystems", "instruments"};
        this.countDownLatch = countDownLatch;

        for (int i = 0; i < map.length; i++){
            components[i] = new Component(map[i], this.network, this);
        }

        // mission destination can be approximated as a function of the fuel load for the mission
        this.destination = components[0].getSize(); //fuel amount.
    }

    @Override
    public void run() {
        System.out.println(id + " created, booting up in " + startTime / 30 + " day(s), destination = " + destination);

        for(int i = 0; i < 5; i++) {
			componentPool.execute(components[i]);
		}

        try{ 
            // When waiting a mission sleeps
            Thread.sleep(startTime); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        while(missionInProgress){
            changeStage();
        }
        
        if(stage.isEmpty()){
            // Poison pill to end network connection
            // this.network.transmit("Complete");
            System.out.printf("%s has completed successfully!%n", id);
        }

        componentPool.shutdown();
        countDownLatch.countDown();        
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

    // Check for failures
    // Each stage has at least a 10% chance of failing.
    private boolean checkRunning(){
        boolean success = true;

        int failTen = SimulateRandomAmountOf.chance();
        if(failTen == 1){
            System.out.printf("!! %s System failure during %s! Requesting fix from Ground Control.%n", id, stage);
            network.transmit(new dataTypes.PatchRequest("request"));
            
            // block until the update arrives 
            // Object update = network.receiveUpdate();
            // SoftwareUpdate sw = (SoftwareUpdate) update;

            SoftwareUpdate sw = new SoftwareUpdate(420);
            success = installUpdate(sw);
        }
        return success;
    }

    // send reports
    // A variable burst of reports and commands are sent at the transition between mission stages.
    private void burstOfReports() {
        // There are a variable number of types of commands and reports for each mission
        int reports = SimulateRandomAmountOf.reports();                                       //TODO: BURST REPORT AFTER EACH STAGE.
        network.transmit(new Report("hello"));
        // int commands = GroundControl.receiveBurstReports(reports, this.network);              //TODO: REPORTS ARE EITHER TELEMETRY OR DATA
    }

    private void simulateJourneyTime(int journeyTime){
        System.out.printf("%s in %s stage for %s month(s)!%n", id, stage, journeyTime / 1000);
        try{ 
            Thread.sleep(journeyTime); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void printSuccessStatus(String id, String stage){
        System.out.printf("%s had no system failures during %s.%n", id, stage);
    }

    public boolean installUpdate(SoftwareUpdate update){
        int failFour = SimulateRandomAmountOf.chance();

        // 25% chance of failure of install
        if(failFour <= 4){
            System.out.printf("XX %s upgrade has failed during %s. %1$s aborted.%n", id, stage);
            return false;
        }
        else{
            showUpdateProgress(update);
            System.out.printf("++ %s software upgrade successfully applied.%n", id);
            return true;
        }
    }
    
    public static void showUpdateProgress(SoftwareUpdate update) {
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        // TODO change this depending on bandwidth and update size
        int updateSize = update.getUpdateSize();

        for (int i = 0; i <= 100; i+=10) {
            System.out.print("Installing: " + i + "% " + animationChars[i % 4] + "\r");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString(){
        return id;
    }
}
