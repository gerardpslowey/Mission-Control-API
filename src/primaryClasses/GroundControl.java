package primaryClasses;

import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import utils.*;
import dataTypes.*;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

public class GroundControl {
    // mission controller is a shared resource used for all missions
    // at least 10 simultaneous missions.
    private static final int MIN_MISSIONS = 10;
    private static final int MAX_MISSIONS = 50;

    private static ExecutorService missionPool = Executors.newFixedThreadPool(50);

    public static void main(String[] args){

        int missionCount = SimulateRandomAmountOf.missions(MIN_MISSIONS, MAX_MISSIONS);
       
        // try {
        //     System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt"))));
        // } catch (FileNotFoundException e1) {
        //     e1.printStackTrace();
        // }

        System.out.println("Number of Simultaneous Missions: " + missionCount);
        // Each mission can be represented using threads
        // use a thread pool for tasks
        ExecutorService missionPool = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(missionCount);
		Mission[] missions = new Mission[missionCount];

        ExecutorService logPool = Executors.newSingleThreadExecutor();
        FileLogger logger = new FileLogger();
        logPool.execute(logger);

        for(int i = 0; i < missionCount; i++) {
            int startTime = SimulateRandomAmountOf.days();
			missions[i] = new Mission("M" + i, startTime, latch);
			missionPool.execute(missions[i]);
        }

        // Poll the networks with a thread each
        for(Mission mission : missions) {
            Runnable networkListener = () -> {
                while(!mission.getStageEmpty()){
                    Network missionNetwork = mission.getNetwork(); 
                    try {
                        Object obj = missionNetwork.receive();
                        process(obj, mission, logger);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        missionPool.execute(networkListener);
        }

        missionPool.shutdown();
        
        try{
            latch.await();
            logPool.shutdown();
            logger.put("*");

            boolean runner = true;
            for(Mission mission : missions) {
                //if the stage is not empty and the mission is finished, then uh oh
                if(!mission.getStageEmpty() && !mission.getMissionProgress() ){
                    // Any missions which loose progress have been hit by a solar flare
                    System.out.println("Oh No! " + mission.getID() + " hit by solar flare.");
                    runner = false;
                }
            }
            new RocketMan(runner);

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private static void process(Object obj, Mission mission, FileLogger logger){
        String missionName = mission.getID();
        Network network = mission.getNetwork();
        LocalDateTime timeStamp = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        
        if (obj instanceof Report) { 
            System.out.println("RR Report Received from " + missionName);
            String threadinfo = Thread.currentThread().getName();
            logger.put(missionName + ", " + threadinfo + ", " + obj + ", " + dtf.format(timeStamp));
        }

        if (obj instanceof Message) { 
            System.out.println("MM Message Received from " + missionName);
            String threadinfo = Thread.currentThread().getName();
            logger.put(missionName + ", " + threadinfo + ", " + obj + ", " + dtf.format(timeStamp));
        }

        if (obj instanceof PatchRequest) { 
            System.out.println("PR Patch Request Received from " + missionName + "\n");
            // 25% of failures can be recovered from by sending a software upgrade
            // Software upgrades must be transmitted from the mission controller
            missionPool.execute(new SoftwareUpdater(network));
        }

        if (obj instanceof ResponseRequest) { 
            System.out.println("RQ Response Request Received from " + missionName);
            Runnable responder = () -> {        
                System.out.println("\t" + "\uD83D\uDE00 <- sending command response to <- " + mission.getID());
                network.transmitResponse();
            };
            missionPool.execute(responder);
        }
    }
}
