import java.util.Random;
    
public class Component {
    
    // All mission components transmit reports (telemetry) on progress and instruments send data on a regular basis
    // this is limited by bandwidth and subject to increasing delays as the mission travels further away from Earth.  
    
    //====================================================================================================
    // 30% of reports require a command response and the mission is paused until that command is received.
    // Reports can be telemetry (100-10k bytes, frequent) or data (100k-100MB, periodic)

    // Each component should have a random report rate and size for the mission.

    // Each mission is allocated variable supplies of 
    // fuel, thrusters, instruments, control systems and powerplants.
    Random random = new Random();

    public int fuel() {
        // assign a random fuel level in a range
        // the fuel level decides the destination
        final int MAX_FUEL_LIMIT = 200;
        final int MIN_FUEL_LIMIT = 20;

        return getRandomAllocated(MAX_FUEL_LIMIT, MIN_FUEL_LIMIT);
    }

    public int thrusters(){
        final int MAX_THRUSTERS = 20;
        final int MIN_THRUSTERS = 4;

        return getRandomAllocated(MAX_THRUSTERS, MIN_THRUSTERS);
    }

    public int instruments(){
        final int MAX_INSTRUMENTS = 20;
        final int MIN_INSTRUMENTS = 4;

        return getRandomAllocated(MAX_INSTRUMENTS, MIN_INSTRUMENTS);
    }

    public int controlSystems(){
        final int MAX_CONTROL_SYSTEMS = 4;
        final int MIN_CONTROL_SYSTEMS = 1;

        return getRandomAllocated(MAX_CONTROL_SYSTEMS, MIN_CONTROL_SYSTEMS);
    }

    public int powerPlants(){
        final int MAX_CONTROL_SYSTEMS = 6;
        final int MIN_CONTROL_SYSTEMS = 2;

        return getRandomAllocated(MAX_CONTROL_SYSTEMS, MIN_CONTROL_SYSTEMS);    }

    // get random value in rnage
    private int getRandomAllocated(int minimum, int maximum) {
        return random.nextInt(maximum - minimum) + minimum;
    }

    // When on a mission it is necessary for all mission components to transmit reports (telemetry ) on progress  
    // instruments send data on a regular basis, but this is limited by bandwidth 
    // subject to increasing delays as the mission travels further away from Earth.

    //  There are a variable number of types of commands and reports for each mission. 
    // Reports can be telemetry (100-10k bytes, frequent) or data (100k-100MB, periodic)
    public void transmitTelemetry(){
        // TODO
    }
}
