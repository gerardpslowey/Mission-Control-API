package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Random;

public class GroundControl {
    private static final int MIN_MISSION_COUNT = 10;
    private static final int MAX_MISSIONS = 200;

    public static void main(String[] args){

        //Random random = new Random();
        //int missionCount = random.nextInt(MAX_MISSIONS - MIN_MISSION_COUNT) + MIN_MISSION_COUNT;
        int missionCount = 10;

        // use a thread pool for tasks
        ExecutorService missionPool = Executors.newFixedThreadPool(missionCount);
		Mission[] missions = new Mission[missionCount];

        for(int i = 0; i < missionCount; i++) {
            long startTime = (long)(Math.random() * 300);
			missions[i] = new Mission("Mission" + i, startTime);
			missionPool.execute(missions[i]);
		}

        missionPool.shutdown();
        // if busy wait
        while (!missionPool.isTerminated()) {
            
        }

        System.out.println("All the missions have been completed!");
    }
}
