import java.util.Random;
    
    // this.startTime;
    // this.componentsList;
    // this.detination;
    // this.network;
    // this.id;    
    
    //====================================================================================================
    // All mission components transmit reports (telemetry) on progress and instruments send data on a regular basis, but this is limited by
    // bandwidth and subject to increasing delays as the mission travels further away from Earth.

    //====================================================================================================
    // 30% of reports require a command response and the mission is paused until that command is received.
    // Software upgrades must be transmitted from the mission controller.
    //Reports can be telemetry (100-10k bytes, frequent) or data (100k-100MB, periodic)

    //Each component should have a random report rate and size for the mission.

    // fuel supply decides the destination
    public int fuel(){
        // todo
        // assign a random fuel level in a range
        // the fuel level decides the destination
    }

    public int thrusters(){
        // todo
    }

    public int instruments(){
        // todo
    }

    public int controlSystems(){
        // todo
    }

    public int powerPlants(){
        // todo
    }
}
