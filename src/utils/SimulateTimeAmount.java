package utils;
import java.util.concurrent.ThreadLocalRandom;

public class SimulateTimeAmount {
    public static synchronized int compute (int lowerLimit, int upperLimit){
        // Lower limit inclusive, upper limit exclusive.
        // We are given the info that 1s is a month.
        // 31 and 210+1 = 7 days in a week.
        // 1001 and 10,000+1 = the months in a year.
        return ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit +1);
    }
}
