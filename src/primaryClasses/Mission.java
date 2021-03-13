public class Mission implements Runnable {

    private String id;
    private long startTime;
    private String stage = "launch";
    private boolean missionInProgress = true;
    private Component fuel;
    private Component thrusters;
    private Component powerplants;
    private Component controlSystems;
    private Component instruments;

    private int destination;

    public Mission(String id, long startTime){
        this.id = id;
        this.startTime = startTime;
        this.fuel = new Component("fuel", 0, 100 + 1);          //TODO: SURELY BETTER WAY TO CREATE COMPONENTS?
        this.thrusters = new Component("thrusters", 0, 4 + 1);
        this.powerplants = new Component("powerplants", 0, 100 + 1);
        this.controlSystems = new Component("controlSystems", 0, 10 + 1);
        this.instruments = new Component("instruments", 0, 25 + 1);

        this.destination = fuel.getAmount();
    }

    @Override
    public void run() {
        System.out.println(id + " created.");
        System.out.printf("%s is booting up in %s day(s).%n", id, startTime / 30);

        System.out.println(id + " destination = " + destination);

        try{ 
            Thread.sleep(startTime); 
        } catch (InterruptedException e) {Thread.currentThread().interrupt();}

        while(missionInProgress){
            changeStage();
        }
        if(stage.isEmpty()){
            System.out.printf("%s has been successful!%n", id);
        }
    }

    public void changeStage(){
        int journeyTime = GroundControl.simulateTimeAmount(1001, 10000 + 1);
        switch(stage) {
            case "launch":

                //if no failures
                if(failureCleared()){
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "transit";
                } else {
                    missionInProgress = false;
                }
                break;

            case "transit":
                if(failureCleared()){
                    simulateJourneyTime(journeyTime);
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "landing";
                } else {
                    missionInProgress = false;
                }
                break;

            case "landing":
                if(failureCleared()){
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
                    stage = "explore";
                } else {
                    missionInProgress = false;
                }
                break;

            case "explore":
                if(failureCleared()){
                    simulateJourneyTime(journeyTime);
                    System.out.printf("%s had no system failures during %s.%n", id, stage);
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
        System.out.printf("%s in %s stage for %s month(s)!%n", id, stage, journeyTime / 1000);
        try{ 
            Thread.sleep(journeyTime); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private boolean failureCleared(){
        boolean success = true;

        //10% chance of failure
        int failTen = GroundControl.simulateTimeAmount(1, 10+1);
        if(failTen==1){
            System.out.printf("!! %s system failure during %s! Request fix from GroundControl.%n", id, stage);
            int updateTime = GroundControl.simulateTimeAmount(31, 210+1);         //TODO: change this to developUpdate(size, time)
            success = fixSoftwareFailure(updateTime);

            if(success){
                System.out.printf("++ %s software upgrade successfully applied.%n", id);
            }
        }
        return success;
    }

    private boolean fixSoftwareFailure(int updateTime){
        boolean fixed = true;

        //Update takes a few days
        System.out.printf("?? %s upgrading in %s days.%n", id, updateTime);
        try{ 
            Thread.sleep(updateTime); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        //25% chance of failure
        int failFour = GroundControl.simulateTimeAmount(1, 4+1);
        if(failFour==1){
            System.out.printf("XX %s upgrade has failed during %s. %1$s aborted.%n", id, stage);
            fixed = false;
        }
        return fixed;
    }  
}
