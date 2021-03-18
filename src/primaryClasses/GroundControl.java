package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import utils.*;
import dataTypes.*;

public class GroundControl {
    // mission controller is a shared resource used for all missions
    // at least 10 simultaneous missions.
    private static final int MIN_MISSIONS = 10;
    private static final int MAX_MISSIONS = 50;

    private static ExecutorService missionPool = Executors.newFixedThreadPool(50);

    public static void main(String[] args){

        int missionCount = SimulateRandomAmountOf.missions(MIN_MISSIONS, MAX_MISSIONS);
        
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
            Runnable runnable = () -> {
                Network missionNetwork = mission.getNetwork(); 
                    try {
                        Object obj = missionNetwork.receive();
                        String name = missionNetwork.getName();
                        process(obj, name, missionNetwork, logger);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

            missionPool.execute(runnable);
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
                    
                    System.out.println("Oh No! " + mission.getID() + " failed.");
                    runner = false;
                }
            }
            new RocketMan(runner);

        } catch (InterruptedException e){
            //e.printStackTrace();	
            Thread.currentThread().interrupt();
        }
    }

    private static void process(Object obj, String missionName, Network network, FileLogger logger){
        if (obj instanceof Report) { 
            System.out.println("RR Report Received from " + missionName);
            System.out.println("\t" + "Contents: " + obj);
            String threadinfo = Thread.currentThread().getName();
            logger.put(missionName + ", " + threadinfo + ", " + obj);
        }

        if (obj instanceof Message) { 
            System.out.println("MM Message Received from " + missionName + "\n" + "\t" + "Contents: " + obj + "\n");
            String threadinfo = Thread.currentThread().getName();
            logger.put(missionName + ", " + threadinfo + ", " + obj);
        }

        if (obj instanceof PatchRequest) { 
            System.out.println("PR Patch Request Received from " + missionName + "\n");
            // 25% of failures can be recovered from by sending a software upgrade
            // Software upgrades must be transmitted from the mission controller
            missionPool.execute(new SoftwareUpdater(network));
        }
    }

    public static synchronized void commandResponse(Mission mission){
        Network network = mission.getNetwork();
        // Object x = network.receive();

        System.out.println("\uD83D\uDE00 <- command response to <- " + mission.getID());
        network.transmitUpdate("Response");
        // mission.notifyAll();
    }
}
