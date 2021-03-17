package utils;

import java.util.concurrent.Callable;
import primaryClasses.Network;
import dataTypes.SoftwareUpdate;

public class SoftwareUpdater implements Callable<Boolean> { 

	Network network;
    
	public SoftwareUpdater(Network network) 
	{ 
		this.network = network; 
	} 

	@Override 
    public Boolean call() 
	{ 
        boolean success = true;

        try {
            int[] patchDetails = getPatchDetails();

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

            // TODO move this
            // 25% chance of failure of install
            int failFour = SimulateRandomAmountOf.chance();

            if(failFour <= 4){
                success = false;
            }
        } 
        catch (InterruptedException e) { 
            e.printStackTrace();	
        }
        
        return success;
    }

    // updates take a variable number of days to develop and is a variable size in MB.        
    public static synchronized int[] getPatchDetails(){
        int time = SimulateRandomAmountOf.days();
        int updateSize = SimulateRandomAmountOf.updateSize();

        return new int[] {updateSize, time};
    }
} 
