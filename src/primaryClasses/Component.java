package primaryClasses;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.LinkedBlockingDeque ;

public class Component implements Runnable{

    private String compID;
    private String reportRate;

    public Component(String id){
        compID = id;
    }
    
    public void run(){

        LinkedBlockingDeque<Integer> components = new LinkedBlockingDeque<>();

        // a fuel level of 0-100%
        int fuel = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        components.add(fuel);

        // 4 thrusters
        int thrusters = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        components.add(thrusters);

        //powerplant as 0-100%
        int powerplants  = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        components.add(powerplants );

        //0-10 control systems?
        int controlSystems  = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        components.add(controlSystems );

        //25 max instruments
        int instruments  = ThreadLocalRandom.current().nextInt(0, 25 + 1);
        components.add(instruments );

        System.out.println("Component list = " + components);


        
        //TODO: Each component needs to be given a random report rate as well.
    }
}
