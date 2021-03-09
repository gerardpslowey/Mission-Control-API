package primaryClasses;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
//import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GroundControl {
    // private static final int MIN_MISSION_COUNT = 10;
    // private static final int MAX_MISSIONS = 200;
    // private static final int MAX_MISSIONS = 200;

    public static void main(String[] args){

        //Random random = new Random();
        //int missionCount = random.nextInt(MAX_MISSIONS - MIN_MISSION_COUNT) + MIN_MISSION_COUNT;
        int missionCount = 2;

        // use a thread pool for tasks
        ExecutorService missionPool = Executors.newFixedThreadPool(missionCount);
		Mission[] missions = new Mission[missionCount];

        for(int i = 0; i < missionCount; i++) {
            int startTime = ThreadLocalRandom.current().nextInt(31, 300 +1);
            //long startTime = (long)(Math.random() * 300);
			missions[i] = new Mission("M" + i, startTime);
			missionPool.execute(missions[i]);
		}

        missionPool.shutdown();
        // if busy wait
        while (!missionPool.isTerminated()) {
            //terminated
        }

        System.out.println("All the missions have been completed!");
    }

    public static int requestSoftwareFix(){
        int updateTime = ThreadLocalRandom.current().nextInt(31, 300 +1);
        return updateTime;
    }
}
