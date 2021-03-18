package primaryClasses;

import java.util.concurrent.Executors;
import java.lang.System.Logger;
import java.util.concurrent.ExecutorService;
import utils.SimulateRandomAmountOf;
import dataTypes.SoftwareUpdate;

import dataTypes.*;

public class GroundControl {
    // mission controller is a shared resource used for all missions
    // at least 10 simultaneous missions.
    private static final int MIN_MISSIONS = 2;
    private static final int MAX_MISSIONS = 20;          //TODO: SET THESE TO 10 and 200

    public static void main(String[] args){

        int missionCount = SimulateRandomAmountOf.missions(MIN_MISSIONS, MAX_MISSIONS);
        
        System.out.println("Number of Simultaneous Missions: " + missionCount);
        // Each mission can be represented using threads
        // use a thread pool for tasks
        ExecutorService missionPool = Executors.newFixedThreadPool(50);
		Mission[] missions = new Mission[missionCount];


        for(int i = 0; i < missionCount; i++) {
            int startTime = SimulateRandomAmountOf.days();
			missions[i] = new Mission("M" + i, startTime);
			missionPool.execute(missions[i]);
        }

        // Poll the networks with a thread each
        for(Mission mission : missions) {
            missionPool.execute(new Runnable() {
                @Override
                public void run(){
                    Network missionNetwork = mission.getNetwork(); 

                    try {
                        Object obj = missionNetwork.receive();
                        String name = missionNetwork.getName();
                        process(obj, name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }         
            });
        }

        missionPool.shutdown();
        // System.out.println("All the missions have completed!");

    }

    // poll the networks
    // public static void listenOnNetwork(Mission[] missions){

    // }

    private static void process(Object obj, String missionName){
        if (obj instanceof Report) { 
            System.out.println("Report Received from " + missionName + "\n" + "\t" + "Contents: " + obj);
        }
        else if (obj instanceof Message) { 
            System.out.println("Message Received from " + missionName + "\n" + "\t" + "Contents: " + obj + "\n");
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

    // software updates
    // Software upgrades must be transmitted from the mission controller
    public static void transmitSoftwareUpgrade(Component component){
        SoftwareUpdate update = new SoftwareUpdate(2);
        Network network = component.getNetwork();
        network.transmit(update);
    }
}
