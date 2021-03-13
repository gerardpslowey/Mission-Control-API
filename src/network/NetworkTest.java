package network;

import utils.SoftwarePatch;

import java.util.concurrent.LinkedBlockingQueue; 
import java.util.concurrent.BlockingQueue; 

public class NetworkTest {

	public static void main(String[] args) 
	{ 
		BlockingQueue<String> bqueue = new LinkedBlockingQueue<>(); 

		ProduceSoftwareUpdate p1 = new ProduceSoftwareUpdate(bqueue); 
		InstallSoftwarePatch c1 = new InstallSoftwarePatch(bqueue); 

		Thread pThread = new Thread(p1); 
		Thread cThread = new Thread(c1); 

		pThread.start();
		cThread.start(); 
	} 
} 

// Probably be located in Component.java
class InstallSoftwarePatch implements Runnable { 

	BlockingQueue<String> obj; 
    boolean running = true;

	public InstallSoftwarePatch(BlockingQueue<String> obj) 
	{ 
		this.obj = obj; 
	} 

	@Override 
    public void run() { 
        try { 
            String value = obj.take();

            while(!value.equals("*")){
                SoftwarePatch.showProgress();

                value = obj.take();
            }
        } 

        catch (InterruptedException e) { 
            Thread.currentThread().interrupt();        } 
	} 

}
