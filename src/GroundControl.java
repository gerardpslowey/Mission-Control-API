import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.*;

public class GroundControl {

    private static final int MIN_MISSION_COUNT = 10;
    private static long startTime = System.currentTimeMillis();


    
    public static void main(String[] args){
        // Each mission can be represented using threads
        // Thread t = new Thread (spaceMission, spaceMission.missionName);
        // t.start();

        ExecutorService missionPool = Executors.newFixedThreadPool(MIN_MISSION_COUNT);         //TODO: Set randomly to 10 or more
		Mission[] missions = new Mission[MIN_MISSION_COUNT];

        for(int i = 0; i < MIN_MISSION_COUNT; i++) {
			missions[i] = new Mission("Mission" + i, startTime);

			missionPool.execute(missions[i]);
		}

        while(true){
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000 + 100));
            } catch (InterruptedException e){}

            //Mission.enterStage
            //Mission.checkFailure
            //produceSoftwarePatch
            //sendCommandResponse
        }


    // When a stage fails then a software patch must be sent.
    // takes a variable number of days to develop
    // variable size in mb
    // related to the bandwidth queue
    public static void produceSoftwarePatch() {
        long numDays = (long)(Math.random() * 300);
        int softwareSize = 5;                               //TODO: random software size
    }

    // 30% of reports require a command response and the mission is paused until that command is received. 
    public static void sendCommandResponse() {

    }    
}
