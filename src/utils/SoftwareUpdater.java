package utils;

import java.util.concurrent.Callable;
import java.util.Random;
import primaryClasses.Network;
import dataTypes.SoftwareUpdate;

public class SoftwareUpdater implements Callable<Boolean> { 

    static Random random = new Random();         
	Network network;

    int[] patchDetails = getPatchDetails();

	public SoftwareUpdater(Network network) 
	{ 
		this.network = network; 
	} 

	@Override 
    public Boolean call() 
	{ 
        boolean success = true;

        try {
            int patchSize  = patchDetails[0];
            int buildTime = patchDetails[1];
            
            System.out.println("Software Patch Requested, Beginning Build");
            System.out.println("Estimated development time: " + buildTime + " days");
            System.out.println("Estimated patch size: " + patchSize + " MB");
            
            // 33ms = 1 day
            Thread.sleep((long) buildTime * 33);
            System.out.println("Developers finished building and testing period");
            System.out.println("Patch in Network Pipeline"); 
            network.transmit(new SoftwareUpdate(patchSize)); 
            network.transmit("*");

            // 25% chance of failure of install
            int failFour = SimulateRandomAmountOf.chance();

            if(failFour <= 4){
                success = false;
            }
        } 
        catch (InterruptedException e) { 
            e.printStackTrace();
            // Thread.currentThread().interrupt();	
        }
        return success;
    }

    // updates take a variable number of days to develop and is a variable size in MB.        
    public static synchronized int[] getPatchDetails(){
        int time = SimulateRandomAmountOf.days();
        int updateSize = random.nextInt(500);

        return new int[] {updateSize, time};
    }


    // TODO chnage this depending on bandwidth and update size
    public static void showUpdateProgress() {
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
