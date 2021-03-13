package network;

import utils.SoftwarePatch;
import java.util.concurrent.BlockingQueue; 

// Probably located in ground control
public class ProduceSoftwareUpdate implements Runnable { 

	protected BlockingQueue<String> obj; 
    int[] patchDetails = SoftwarePatch.developUpdate(1, 6);

	public ProduceSoftwareUpdate(BlockingQueue<String> obj) 
	{ 
		this.obj = obj; 
	} 

	@Override 
    public void run() 
	{ 
        try {
            int patchSize  = patchDetails[0];
            int buildTime = patchDetails[1];
            
            System.out.println("Software Patch Requested, Beginning Build");
            System.out.println("Estimated development time: " + buildTime + " months");
            System.out.println("Estimated patch size: " + patchSize + " mb");
             
            Thread.sleep(buildTime * 1000);
            System.out.println("Developers finished building and testing period");
            obj.put("update"); 
            obj.put("*");
            System.out.println("Patch in Network Pipeline"); 

        } 
        catch (InterruptedException e) { 
            Thread.currentThread().interrupt();	
        }
    }
    


} 
