import java.util.Queue;
import java.util.PriorityQueue;

public class Network {
    // Each mission has its own network
    // Network availability can be checked before a message is to be sent
    // If a network is available then it can be used to transmit the full message

    private int Bandwidth;

    public Network() {
        // this.Bandwidth;
    }


    public Queue calculateBandwidth(){
        // todo random selection based on transmission width
        Queue<String> bandwidthQueue = new PriorityQueue<>(); 

        return bandwidthQueue;
    }
}
