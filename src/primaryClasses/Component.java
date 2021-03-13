public class Component implements Runnable{

    private String compID;
    private Integer amount;
    private Integer reportRate;

    public Component(String compID, int lowerLimit, int upperLimit){
        this.compID = compID;
        this.amount = GroundControl.simulateTimeAmount(lowerLimit, upperLimit);
        this.reportRate = GroundControl.simulateTimeAmount(0, 20+1);
    }

    public void run(){
        //TODO: Set up scheduled executor of reportRate for sending telemetry/reports
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