import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;

public class Component implements Runnable{

    private String compID;
    private Integer size;
    private Integer reportRate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Component(String compID, int upperLimit){
        this.compID = compID;
        this.size = GroundControl.simulateTimeAmount(1, upperLimit);
        this.reportRate = GroundControl.simulateTimeAmount(31, 210+1);
    }

    public void run(){

        sendProgressReport(); 
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

    public synchronized void sendProgressReport(){

        Runnable message = () -> System.out.printf("%s telemetry message: %s left. %n", compID, size);

        while(true){
            try{
                scheduler.scheduleAtFixedRate(message, 0, reportRate, MILLISECONDS);
                int failure = GroundControl.simulateTimeAmount(1, 10+1);
                if(failure <= 3){
                    System.out.printf("!! %s component awaiting command response %n", compID);
                    wait();
                    GroundControl.commandResponse(compID);
                }

            } catch (InterruptedException e) {Thread.currentThread().interrupt();}
        }
    }
}