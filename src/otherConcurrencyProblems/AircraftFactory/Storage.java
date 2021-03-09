import java.util.ArrayDeque;
import java.util.Queue;

public class Storage {
    Factory factory;
    Queue<Robot> waitingList;
    int parts = 0;

    public Storage(Factory factory) {
        this.factory = factory;
        waitingList = new ArrayDeque<>();
    }

    public synchronized void addParts(int n) {
        if (n <= 0) {
            throw new InternalError("Storage: addParts must get an positive integer as argument.");
        }
        parts += n;
        System.out.println("Restock "+n+" ("+parts+")");
        clearQueue();
    }

    public void getPart(Robot robot) {
        factory.execute(() -> {
            // System.out.println("Thread "+Thread.currentThread().getId()+": "+robot+" arrives on storage.");
            synchronized (this) {
                if (parts == 0) {
                    System.out.println("Thread "+Thread.currentThread().getId()+": "+robot+" storage is empty. Wait for new pieces...");
                    if (! factory.running()) {
                        System.out.println("Thread "+Thread.currentThread().getId()+": Due to covid-19, no new piece will arrive. The factory is forced to close.");
                        System.exit(42);
                    }
                    waitingList.add(robot);
                } else {
                    --parts;
                    robot.hasPart();
                }
            }
        });
    }

    private synchronized void clearQueue() {
        while (waitingList.peek() != null) {
            getPart(waitingList.poll());
        }
    }
}