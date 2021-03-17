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
        this.sizeAmount = SimulateRandomAmountOf.size(compID);
        this.reportRate = SimulateRandomAmountOf.days();
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
                if(getResponse <= 3){
                    scheduler.schedule(command, reportRate, TimeUnit.MILLISECONDS);
                } else {
                    notifyAll();
                }
            GroundControl.commandResponse(this);
            setReportRate(reportRate + 1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scheduler.shutdown();
    }
}