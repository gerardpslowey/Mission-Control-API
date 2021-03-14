package utils;

import network.Network;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class SoftwareUpdater implements Runnable { 

    static Random random = new Random();         
	Network network;
    int[] patchDetails = developUpdate(1, 6);

	public SoftwareUpdater(Network network) 
	{ 
		this.network = network; 
	} 

	@Override 
    public void run() 
	{ 
        try {
            int patchSize  = patchDetails[0];
            int buildTime = patchDetails[1];
            
            System.out.println("Software Patch Requested, Beginning Build");
            System.out.println("Estimated development time: " + buildTime + " months");
            System.out.println("Estimated patch size: " + patchSize + " MB");
             
            Thread.sleep(buildTime * 1000);
            System.out.println("Developers finished building and testing period");
            System.out.println("Patch in Network Pipeline"); 
            network.transmit(new SoftwareUpdate(patchSize)); 
            network.transmit("*");

        } 
        catch (InterruptedException e) { 
            Thread.currentThread().interrupt();	
        }
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
