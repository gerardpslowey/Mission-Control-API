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
			missions[i] = new Mission("M" + i, simulateTimeAmount(31, 210+1));
			missionPool.execute(missions[i]);
		}

        missionPool.shutdown();
        // if busy wait
        while (!missionPool.isTerminated()) {
            //terminated
        }

        System.out.println("All the missions have been completed!");
    }

    public static synchronized int simulateTimeAmount(int lowerLimit, int upperLimit){

        // Lower limit inclusive, upper limit exclusive.
        // We are given the info that 1000ms is a month.
        // 31 and 210+1 chosen to replicate 7 days in a week.
        // 1001 and 10,000+1 chosen to replicate the months in a year.
        return ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit +1);
    }
}
