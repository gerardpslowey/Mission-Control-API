import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
            int startTime = simulateTimeAmount(31, 210+1);
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

    public static synchronized int simulateTimeAmount(int lowerLimit, int upperLimit){
        // Lower limit inclusive, upper limit exclusive.
        // We are given the info that 1s is a month.
        // 31 and 210+1 = 7 days in a week.
        // 1001 and 10,000+1 = the months in a year.
        return ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit +1);
    }

    public static synchronized int[] developUpdate(int lowerLimit, int upperLimit){     //TODO: implement this
        int time = simulateTimeAmount(lowerLimit, upperLimit);
        int updateSize = random.nextInt(500);

        return new int[] {updateSize, time};
    }
}
