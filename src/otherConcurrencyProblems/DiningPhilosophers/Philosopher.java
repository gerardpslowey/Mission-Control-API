package DiningPhilosophers;

// State : 2 = Eat, 1 = think
class Philosopher extends Thread {
  private Chopstick _leftChopstick;
  private Chopstick _rightChopstick;

  private String _name;
  private int _rounds;

  public Philosopher ( String name, Chopstick _left, Chopstick _right, int rounds){
    this._name = name;
    _leftChopstick = _left;
    _rightChopstick = _right;
    _rounds = rounds;
  }

  public void eat()
  {
    if(! _leftChopstick.used) {
      if(!_rightChopstick.used) {

        _leftChopstick.take();
        _rightChopstick.take();

        System.out.println(_name + " eating!");
        
        try{
          Thread.sleep(1000);
        } catch(InterruptedException ex){ }

        _rightChopstick.release();
        _leftChopstick.release();
      }
    }		
    think();
  }
  public void think(){
      System.out.println(_name + " thinking!");
      try{
        Thread.sleep(1000);
      } catch(InterruptedException ex){ }
  }
  
  @Override
  public void run(){
    for(int i=0; i<=_rounds; i++){
      eat();
    }
  }
}