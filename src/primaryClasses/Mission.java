package primaryClasses;
import java.util.concurrent.ThreadLocalRandom;

public class Mission implements Runnable {

    private String id;
    private long startTime;
    private String stage = "launch";
    private boolean missionInProgress = true;

    public Mission(String id, long startTime){
        this.id = id;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        System.out.println(id + " created.");
        System.out.println(String.format("%s is booting up in %s day(s).", id, startTime / 30));

        try{ Thread.sleep(startTime); } catch (InterruptedException e) {Thread.currentThread().interrupt();}

        while(missionInProgress){
            changeStage();
        }
        if(stage.isEmpty()){
            System.out.println(String.format("%s has been successful!", id));
        } else {
            System.out.println(String.format("%s has failed...", id)); 
        }
    }

    public void changeStage(){
        int journeyTime = ThreadLocalRandom.current().nextInt(1001, 10000 +1);
        switch(stage) {
            case "launch":

                //if no failures
                if(checkFailure()){
                    System.out.println(String.format("%s had no system failures during %s.", id, stage));
                    stage = "transit";
                } else {
                    missionInProgress = false;}
                break;

            case "transit":
                if(checkFailure()){
                    simulateJourneyTime(journeyTime);
                    System.out.println(String.format("%s had no system failures during %s.", id, stage));
                    stage = "landing";
                } else {
                    missionInProgress = false;}
                break;

            case "landing":
                if(checkFailure()){
                    System.out.println(String.format("%s had no system failures during %s.", id, stage));
                    stage = "explore";
                } else {
                    missionInProgress = false;}
                break;

            case "explore":
                if(checkFailure()){
                    simulateJourneyTime(journeyTime);
                    System.out.println(String.format("%s had no system failures during %s.", id, stage));
                    stage = "";
                }
                missionInProgress = false;
                break;

            default:
                System.out.println("Invalid Argument: " + stage);
                break;    
        }
    }
    private void simulateJourneyTime(int journeyTime){

        System.out.println(String.format("%s in transmit for %s month(s)!", id, journeyTime / 1000));
        try{ Thread.sleep(journeyTime); } catch (InterruptedException e) {Thread.currentThread().interrupt();}
    }
    
    private boolean checkFailure(){

        boolean success = true;

        //10% chance of failure, lower limit inclusive, upper limit exclusive.
        int failTen = ThreadLocalRandom.current().nextInt(1, 10+1);
        if(failTen==1){
            System.out.println(String.format("!! %s system failure during %s! Request fix from GroundControl.", id, stage));
            int updateTime = GroundControl.requestSoftwareFix();
            success = fixSoftwareFailure(updateTime);
            if(success){
                System.out.println(String.format("%s software upgrade successfully applied.", id));
            }
        }
        return success;
    }

    private boolean fixSoftwareFailure(int updateTime){

        boolean fixed = true;

        //Update takes a few days
        System.out.println(String.format("%s upgrading in %s days.", id, updateTime));
        try{ Thread.sleep(updateTime); } catch (InterruptedException e) {Thread.currentThread().interrupt();}
        
        //25% chance of failure --> 1,2,3,4
        int failFour = ThreadLocalRandom.current().nextInt(1, 4+1);
        if(failFour==1){
            System.out.println(String.format("!! %s upgrade has failed during %s. %1$s aborted.", id, stage));
            fixed = false;
        }
        return fixed;
    }  
}
