import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import static java.util.concurrent.TimeUnit.*;
import utils.SimulateTimeAmount;
import network.Network;

public class Component implements Runnable{

    private String compID;
    private Integer size;
    private Integer reportRate;
    private Network network;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Component(String compID, Network network, int upperLimit){
        this.compID = compID;
        this.network = network;
        this.size = SimulateTimeAmount.compute(1, upperLimit);
        this.reportRate = SimulateTimeAmount.compute(31, 210+1);
    }

    public void run(){

        // sendProgressReport(); 
        //scheduledfuture: reportRate is initialRate and then 1000 is the amount it multiplied by.
    }

    public String getID(){
        return compID;
    }

    public Integer getSize(){
        return size;
    }

    public void setSize(Integer size){
        this.size = size;
    }

    public Integer getReportRate(){
        return reportRate;
    }

    public Network getNetwork(){
        return this.network;
    }

    public synchronized void sendProgressReport(){ 

        //Runnable message = () -> System.out.printf("%s telemetry message: %s left. %n", compID, size);
        Runnable message = () -> network.transmit(200);
        Runnable command = () -> network.transmit("response");

        while(true){
            try{
                scheduler.scheduleAtFixedRate(message, 0, reportRate, MILLISECONDS);
                int response = SimulateTimeAmount.compute(1, 10+1);
                if(response <= 3){
                    //System.out.printf("!! %s component awaiting command response %n", compID);
                    scheduler.schedule(command, 1, MILLISECONDS);
                    wait();
                    GroundControl.commandResponse(this);
                }
            } catch (InterruptedException e) {Thread.currentThread().interrupt();}
        }
    }
}