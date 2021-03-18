package primaryClasses;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ScheduledFuture;
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
        sendProgressReport();
        // System.out.println("Hello from " + compID + ", in mission " + mission); 
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

    // on a mission it is necessary for all mission components to transmit reports (telemetry) on progress
    public synchronized void sendProgressReport(){ 
        Runnable message = () -> System.out.printf("%s %s telemetry message: %s left. %n", mission, compID, sizeAmount);

        final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);
        final ScheduledFuture<?> progressUpdater = scheduler.scheduleAtFixedRate(message, 2, 20, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {

            @Override
            public void run(){
                progressUpdater.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
    }

    // instruments send data on a regular basis
    public synchronized void sendData(){ 
        // TODO
    }
}