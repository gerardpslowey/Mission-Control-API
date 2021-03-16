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

    public Component(String compID, Network network){
        this.compID = compID;
        this.network = network;
        this.sizeAmount = SimulateRandomAmountOf.size(compID);
        this.reportRate = SimulateRandomAmountOf.days();
    }

    public void run(){
        sendProgressReport(); 
        //scheduledfuture: reportRate is initialRate and then 1000 is the amount it multiplied by.
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

    public Network getNetwork(){
        return this.network;
    }

    public synchronized void sendProgressReport(){ 

        Runnable message = () -> System.out.printf("%s telemetry message: %s left. %n", compID, sizeAmount);
        // Runnable message = () -> network.transmit(200);
        Runnable command = () -> network.transmit("response");

        final ScheduledThreadPoolExecutor scheduler = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);
        final ScheduledFuture<?> progressUpdater = scheduler.scheduleAtFixedRate(message, 2, reportRate, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {

            @Override
            public void run(){
                progressUpdater.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);


        // while(true){
        //     try{
        //         scheduler.scheduleAtFixedRate(message, 100, reportRate, MILLISECONDS);
        //         int response = SimulateRandomAmountOf.chance();
        //         if(response <= 3){
        //             //System.out.printf("!! %s component awaiting command response %n", compID);
        //             scheduler.schedule(command, 1, MILLISECONDS);
        //             wait();
        //             GroundControl.commandResponse(this);
        //         }
        //     } catch (InterruptedException e) {Thread.currentThread().interrupt();}
        // }
    }
}