package utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class FileLogger implements Runnable {
    String message;
    ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    FileWriter fileWriter;
    PrintWriter printWriter;

    public FileLogger(){
        try{
            fileWriter = new FileWriter("output.dat");
            printWriter = new PrintWriter(fileWriter);
            printWriter.print("The Rocket Scientist Problem \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try{
            Thread.sleep(20);
            while(!queue.peek().equals("*")){
                System.out.println("???????????????");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                printWriter.print(queue.poll() + "makes request to network at " + dtf.format(now) + "\n");
            }
            close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 
    // putting in the queue
    public void put(String message){
        this.message = message;
        if(message.equals("*")){
            System.out.println("Closing the logger");
        } else {
            System.out.println("Logging contents");
        }
        queue.add(message);
    }

    public synchronized void close() {
        printWriter.close();
    }
}