package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import utils.SimulateRandomAmountOf;

import dataTypes.*;
import utils.SoftwareUpdater;

public class GroundControl {
    // mission controller is a shared resource used for all missions
    // at least 10 simultaneous missions.
    private static final int MIN_MISSIONS = 2;
    private static final int MAX_MISSIONS = 5;          //TODO: SET THESE TO 10 and 200

    private static ExecutorService missionPool = Executors.newFixedThreadPool(50);

    public static void main(String[] args){

        int missionCount = SimulateRandomAmountOf.missions(MIN_MISSIONS, MAX_MISSIONS);
        
        System.out.println("Number of Simultaneous Missions: " + missionCount);
        // Each mission can be represented using threads
        // use a thread pool for tasks
		Mission[] missions = new Mission[missionCount];

        for(int i = 0; i < missionCount; i++) {
            int startTime = SimulateRandomAmountOf.days();
			missions[i] = new Mission("M" + i, startTime);
			missionPool.execute(missions[i]);
        }

        // Poll the networks with a thread each
        for(Mission mission : missions) {
            Runnable runnable = () -> {
                Network missionNetwork = mission.getNetwork(); 
                    try {
                        Object obj = missionNetwork.receive();
                        String name = missionNetwork.getName();
                        process(obj, name, missionNetwork);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };

            missionPool.execute(runnable);
        }

        missionPool.shutdown();
        // System.out.println("All the missions have completed!");
    }

    private static void process(Object obj, String missionName, Network network){
        if (obj instanceof Report) { 
            System.out.println("RR Report Received from " + missionName);
            System.out.println("\t" + "Contents: " + obj);
        }

        if (obj instanceof Message) { 
            System.out.println("MM Message Received from " + missionName + "\n" + "\t" + "Contents: " + obj + "\n");
        }

        if (obj instanceof PatchRequest) { 
            System.out.println("PR Patch Request Received from " + missionName + "\n");
            // 25% of failures can be recovered from by sending a software upgrade
            // Software upgrades must be transmitted from the mission controller
            missionPool.execute(new SoftwareUpdater(network));
        }
    }

    public static synchronized void commandResponse(Component component){
        Network network = component.getNetwork();
        Object x = network.receive();

        if(x instanceof String){
            System.out.println("\uD83D\uDE00 <- command response to <- " + component.getID());
            component.notifyAll();
        }
    }
}
