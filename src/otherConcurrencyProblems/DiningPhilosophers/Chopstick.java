package DiningPhilosophers;

class Chopstick{

	public boolean used;
	public String _name;

	public Chopstick(String _name){
		this._name = _name;
	}

	public synchronized void take() {
		System.out.println("Using " + _name );
		this.used = true;
	}
	public synchronized void release() {
		System.out.println("Released " + _name );
		this.used = false ;
	}
}