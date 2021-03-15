package utils;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.util.Random;

public class Logger {
    Random random = new Random();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();    
  
    public synchronized void log(String networkType, Object component, String threadID, String message) {
        System.out.print("Mission Component " + component + " with (" + threadID + ") makes request to network " + networkType + " at time " + dtf.format(now) + " for message " + message);
    }
}
