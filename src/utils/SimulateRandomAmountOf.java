package utils;
import java.util.concurrent.ThreadLocalRandom;

public final class SimulateRandomAmountOf {
    // Lower limit inclusive, upper limit exclusive.
    // We are given the info that 1s is a month.
    // 31 and 210+1 = 7 days in a week.
    // 1001 and 10,000+1 = the months in a year.

    private SimulateRandomAmountOf(){
        //not called
    }

    public static synchronized int days(){
        // int lowerLimit = 33;
        // int upperLimit = 210;
        return ThreadLocalRandom.current().nextInt(33, 210 +1);
    }

    public static synchronized int months(){
        // int lowerLimit = 1_001;
        // int upperLimit = 12_000;
        return ThreadLocalRandom.current().nextInt(1_001, 9_000 +1);        //TODO: SHOULD THIS BE 12_000?
    }

    public static synchronized int chance(){
        // int lowerLimit = 1;
        // int upperLimit = 10;
        return ThreadLocalRandom.current().nextInt(1, 10 +1);
    }

    public static synchronized int size(String id){
        int lowerLimit = 1;
        int upperLimit = 10;
        switch(id) {
            case "fuel":
                upperLimit = 10_000;
                break;
            case "thrusters":
                upperLimit = 10;
                break;
            case "powerplants":
                upperLimit = 100;
                break;
            case "controlSystems":
                upperLimit = 100;
                break;
            case "instruments":
                upperLimit = 1_000;
                break;
            default:
                break;
        }
        return ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit +1);
    }
}
