import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Logger implements Runnable {

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    BufferedWriter br;
    File file = new File("output.dat");
    private volatile boolean exit = false;

    @Override
    public void run() {

        try{
            br = new BufferedWriter(new FileWriter(file));
            br.write("The Rocket Scientist Problem");
            br = new BufferedWriter(new FileWriter(file, true));    //TODO
            while(!exit){
                if(queue.peek() != null) {
                    br.write(queue.poll());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        exit = true;
    }

    public static void log(String component, String threadID, String networkType, String message) {
        String logMessage = "Mission Component " + component + " with (" + threadID + ") makes request to network " + networkType + " at time " + dtf.format(now) + " for message " + message;
        queue.add(logMessage);
    }
}