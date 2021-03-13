import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;

public class Component implements Runnable{

    private String compID;
    private Integer amount;
    private Integer reportRate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Component(String compID, int lowerLimit, int upperLimit){
        this.compID = compID;
        this.amount = GroundControl.simulateTimeAmount(lowerLimit, upperLimit);
        this.reportRate = GroundControl.simulateTimeAmount(31, 210+1);
    }

    public void run(){
        //TODO: Set up scheduled executor of reportRate for sending telemetry/reports

        Runnable beeper = () -> System.out.printf("%s: sending telemetry %n", compID);
        ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, reportRate, 10000, MILLISECONDS);
        
        Runnable canceller = () -> beeperHandle.cancel(false);
        scheduler.schedule(canceller, 1, HOURS);

        //scheduledfuture: reportRate is initialRate and then 10,000 is the amount it multiplied by.
    }

    public String getID(){
        return compID;
    }

    public Integer getAmount(){
        return amount;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public Integer getReportRate(){
        return reportRate;
    }

    public String sendTelemetry(Integer reportRate){
        //TODO: SEND telemetry TO WHO AND WHEN?
        return "";
    }

    public String sendReport(Integer reportRate){
        //TODO: SEND report TO WHO AND WHEN?
        //only going to be called by Instrument.
        return "";
    }
}