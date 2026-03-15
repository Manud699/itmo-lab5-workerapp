package workerapp.model;

import java.util.Deque;

public class WorkerIdGenerator {

    private static long nextId = 1L; 

    public static long generateID() {
        return nextId++; 
    }


    public static void setId(long lastId) {
        if (lastId >= nextId) {
            nextId = lastId + 1;
        }
    }


    public static void syncWithExistingWorkers(Deque<Worker> loadedWorkers) {
        long maxId = loadedWorkers.stream().mapToLong(Worker::getId).max().orElse(0); 
        nextId = maxId+1;                               
    }


}
