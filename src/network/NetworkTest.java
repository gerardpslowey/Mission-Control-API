package network;

import utils.SoftwareUpdater;
import utils.SoftwareUpdate;
import utils.Ping;
import java.util.Random;

public class NetworkTest {

	static Random random = new Random();

	public static void main(String[] args) 
	{
		Network network1 = new Network();

		SoftwareUpdater p1 = new SoftwareUpdater(network1); 
		InstallSoftwarePatch c1 = new InstallSoftwarePatch(network1); 

		Thread pThread = new Thread(p1); 
		Thread cThread = new Thread(c1); 

		pThread.start();
		cThread.start(); 

		// Ping ping = new Ping(network1);
		// Thread pThread = new Thread(ping);
		// pThread.start(); 

	} 
} 

// Probably be located in Component.java
class InstallSoftwarePatch implements Runnable { 

	Network network;

	public InstallSoftwarePatch(Network network) 
	{ 
		this.network = network; 
	} 

	@Override 
    public void run() { 
		Object value = network.receive();
		SoftwareUpdate update = (SoftwareUpdate) value;
		
		while(!value.equals("*")){
			SoftwareUpdater.showProgress();

			value = network.receive();
		}
		System.out.println("Update successfully installed!");
	} 
}
