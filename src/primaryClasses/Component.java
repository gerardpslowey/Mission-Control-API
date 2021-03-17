package primaryClasses;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import utils.SimulateRandomAmountOf;

public class Component implements Runnable{

    private String compID;
    private Integer sizeAmount;
    private Integer reportRate;
    private Network network;
    private Mission mission;

    public Component(String compID, Network network, Mission mission){
        this.compID = compID;
        this.network = network;
        this.mission = mission;
        // Each component has a random report rate and size 
        this.reportRate = SimulateRandomAmountOf.days();
        this.sizeAmount = SimulateRandomAmountOf.size(compID);
    }

    public void run(){
        //sendProgressReport(); 
    }

    public String getID(){
        return compID;
    }

    public Integer getSize(){
        return sizeAmount;
    }

    public void setSize(Integer sizeAmount){
        this.sizeAmount = sizeAmount;
    }

    public Integer getReportRate(){
        return reportRate;
    }

    public void setReportRate(Integer reportRate){
        this.reportRate = reportRate;
    }

    public Network getNetwork(){
        return this.network;
    }

    // on a mission it is necessary for all mission components to transmit reports (telemetry ) on progress
    public synchronized void sendProgressReport(){ 

        Runnable message = () -> {
            System.out.printf("%s -> telemetry message -> %s %n", compID, sizeAmount);
            network.transmit(200);
        };

        Runnable command = () -> {
            System.out.printf("!! %s component awaiting command response %n", compID);
            network.transmit("200");
        };
        
        final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);

        while(mission.getMissionProgress()){
            try{
                wait();
                scheduler.schedule(message, reportRate, TimeUnit.MILLISECONDS);
                int getResponse = SimulateRandomAmountOf.chance();

                // 30% of reports require a command response
                if(getResponse <= 3){
                    scheduler.schedule(command, reportRate, TimeUnit.MILLISECONDS);
                } else {
                    notifyAll();
                }
            // the mission is paused until that command is received
            GroundControl.commandResponse(this);
            setReportRate(reportRate + 1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scheduler.shutdown();
    }

    // instruments send data on a regular basis
    public synchronized void sendData(){ 
        // TODO
    }
}