package utils;

import primaryClasses.Network;
import dataTypes.SoftwareUpdate;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SoftwareUpdater implements Runnable { 

	Network network;
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	public SoftwareUpdater(Network network) 
	{ 
		this.network = network; 
	} 

	@Override 
    public void run() {

        int[] patchDetails = getPatchDetails();

        int patchSize = patchDetails[0];
        int buildTime = patchDetails[1];
        
        // Initialise patch
        System.out.println("Software Patch Requested, Beginning Build");
        System.out.println("Estimated development time: " + buildTime + " days");
        System.out.println("Estimated patch size: " + patchSize + " MB");
        
        // 33ms = 1 day
        long sleepTime = (long) buildTime * 33;

        Runnable shipPatch = () -> {
            System.out.println("Developers finished building and testing period");
            System.out.println("Patch in Network Pipeline"); 
            network.transmitUpdate(new SoftwareUpdate(patchSize));
            };

        executorService.schedule(shipPatch, sleepTime, TimeUnit.SECONDS);
    } 

    // updates take a variable number of days to develop and is a variable size in MB.        
    public static synchronized int[] getPatchDetails(){
        int time = SimulateRandomAmountOf.days();
        int updateSize = SimulateRandomAmountOf.updateSize();

        return new int[] {updateSize, time};
    }
} 
