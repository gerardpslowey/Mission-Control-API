package utils;

import network.Network;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class SoftwareUpdater implements Callable<Boolean> { 

    static Random random = new Random();         
	Network network;
    int[] patchDetails = developUpdate(31, 210+1);

	public SoftwareUpdater(Network network) 
	{ 
		this.network = network; 
	} 

	@Override 
    public Boolean call() 
	{ 
        boolean fixed = true;

        try {
            int patchSize  = patchDetails[0];
            int buildTime = patchDetails[1];
            
            System.out.println("Software Patch Requested, Beginning Build");
            System.out.println("Estimated development time: " + buildTime + " days");
            System.out.println("Estimated patch size: " + patchSize + " MB");
             
            Thread.sleep(buildTime * 33);
            System.out.println("Developers finished building and testing period");
            System.out.println("Patch in Network Pipeline"); 
            network.transmit(new SoftwareUpdate(patchSize)); 
            network.transmit("*");

            // 25% chance of failure of install
            int failFour = SimulateTimeAmount.compute(1, 4+1);

            if(failFour == 1){
                fixed = false;
            }
        } 
        catch (InterruptedException e) { 
            Thread.currentThread().interrupt();	
        }
        return fixed;
    }

    public static int getPatchSize() {
        return (random.nextInt(500));     
    } 

    public static synchronized int getDevelopmentTime(int lowerLimit, int upperLimit){
        // Lower limit inclusive, upper limit exclusive.
        // We are given the info that 1s is a month.
        // 31 and 210+1 = 7 days in a week.
        // 1001 and 10,000+1 = the months in a year.
        return ThreadLocalRandom.current().nextInt(lowerLimit, upperLimit + 1);
    }

    public static synchronized int[] developUpdate(int lowerLimit, int upperLimit){
        int time = getDevelopmentTime(lowerLimit, upperLimit);
        int updateSize = getPatchSize();

        return new int[] {updateSize, time};
    }

    public static void showProgress() {
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        for (int i = 0; i <= 100; i+=10) {
            System.out.print("Installing: " + i + "% " + animationChars[i % 4] + "\r");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();	
            }
        }
    }
} 
