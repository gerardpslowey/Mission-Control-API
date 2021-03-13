package utils;

import java.util.Random; 
import java.util.concurrent.ThreadLocalRandom;

public class SoftwarePatch {   
    static Random random = new Random();         
  
    public int getDevelopmentTime() {
        return (random.nextInt(500) + 100);     
    }     
    
    public int getPatchSize() {
        return (random.nextInt(10 - 1 + 1) + 1);     
    } 

    public static synchronized int simulateTimeAmount(int lowerLimit, int upperLimit){
        // Lower limit inclusive, upper limit exclusive.
        // We are given the info that 1s is a month.
        // 31 and 210+1 = 7 days in a week.
        // 1001 and 10,000+1 = the months in a year.
        return ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit + 1);
    }

    public static synchronized int[] developUpdate(int lowerLimit, int upperLimit){
        int time = simulateTimeAmount(lowerLimit, upperLimit);
        int updateSize = random.nextInt(500);

        return new int[] {updateSize, time};
    }

    public static void showProgress() {
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        for (int i = 0; i <= 100; i+=10) {
            System.out.print("Installing: " + i + "% " + animationChars[i % 4] + "\r");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Update successfully installed!");
    }

}