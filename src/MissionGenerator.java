import java.util.Random;

public class MissionGenerator {
    // Each "mission" should be generated with a random start time, components list, destination, network 
    // Missions have a unique ID for identification purposes
    private static final int MIN_MISSION_COUNT = 10;
    private static final int MAX_MISSIONS = 200;
	private static long startTime = System.currentTimeMillis();

    public static Integer getRandomNumber(int upperBound) {
		return new Random().nextInt(upperBound) + 1;
	}

    public static Integer generateId(int upperMissionLimit) {
		return getRandomNumber(MIN_MISSION_COUNT);
	}
}
