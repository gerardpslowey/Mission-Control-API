package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Random;
import utils.SimulateRandomAmountOf;

public class GroundControl {
    private static final int MIN_MISSION_COUNT = 2;
    private static final int MAX_MISSIONS = 3;          //TODO: SET THESE TO 10 and 200

    private static Random random = new Random();

    public static void main(String[] args){

        int missionCount = random.nextInt(MAX_MISSIONS - MIN_MISSION_COUNT) + MIN_MISSION_COUNT;

        // use a thread pool for tasks
        ExecutorService missionPool = Executors.newFixedThreadPool(missionCount);
		Mission[] missions = new Mission[missionCount];

        for(int i = 0; i < missionCount; i++) {
            int startTime = SimulateRandomAmountOf.days();
			missions[i] = new Mission("M" + i, startTime);
			missionPool.execute(missions[i]);
		}

        missionPool.shutdown();

        System.out.println("All the missions have been completed!");
    }

    public static int receiveBurstReports(int reports, Network network){                    //TODO: RECEIVE BURST REPORTS AND SEND RANDOM AMOUNT OF COMMANDS BACK

        Object x = network.receive();
        int commands = SimulateRandomAmountOf.size(reports);        //return an amount of commands in range of reports.
        return commands;

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
