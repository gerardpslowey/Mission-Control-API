package DiningPhilosophers;

public class DiningPhilosophers {
    public static void main(String[] args){
    
        int rounds=2;

        System.out.println("Each Philosopher will eat " + String.valueOf(rounds) + " times.");
    
        Chopstick[] chopsticks = new Chopstick[5];

        //initialize the chopsticks
        for(int i=0; i < chopsticks.length; i++){
            chopsticks[i] = new Chopstick("Chopstick "+i);
        }
        Philosopher[] philosophers = new Philosopher[5];
        for(int i=0; i < philosophers.length; i++){
            int chop = i % philosophers.length;
            int stick = (i + 1) % philosophers.length;
            philosophers[i] = new Philosopher("Person " + i, chopsticks[chop], chopsticks[stick], rounds);
        }

        // philosophers[0] = new Philosopher("Person 0: ", chopsticks[0], chopsticks[1], rounds);
        // philosophers[1] = new Philosopher("Person 1: ", chopsticks[1], chopsticks[2], rounds);
        // philosophers[2] = new Philosopher("Person 2: ", chopsticks[2], chopsticks[3], rounds);
        // philosophers[3] = new Philosopher("Person 3: ", chopsticks[3], chopsticks[4], rounds);
        // philosophers[4] = new Philosopher("Person 4: ", chopsticks[4], chopsticks[0], rounds);

        for(int i=0;i<philosophers.length;i++){
            System.out.println("Thread "+ i + " has started");
            Thread t= new Thread(philosophers[i]);
            t.start();
        }
    }
}


