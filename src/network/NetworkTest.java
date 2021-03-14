package network;

import utils.SoftwareUpdater;

public class NetworkTest {

	public static void main(String[] args) 
	{
		Network network = new Network();
		System.out.println(network.getBandwidth());

		SoftwareUpdater p1 = new SoftwareUpdater(network); 
		InstallSoftwarePatch c1 = new InstallSoftwarePatch(network); 

		Thread pThread = new Thread(p1); 
		Thread cThread = new Thread(c1); 

		pThread.start();
		cThread.start(); 
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
		String value = network.receive();

		while(!value.equals("*")){
			SoftwareUpdater.showProgress();

			value = network.receive();
		}
		System.out.println("Update successfully installed!");
	} 
}
