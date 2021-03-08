package primaryClasses;

public class Mission implements Runnable {

    private String id;
    private long startTime;

    public Mission(String id, long startTime){
        this.id = id;
        this.startTime = startTime;
    }

    @Override
    public void run(){
        System.out.println("hello from " + id + ": " + startTime);
    }
    
}
