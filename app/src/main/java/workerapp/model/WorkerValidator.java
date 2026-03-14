package workerapp.model;

public class WorkerValidator  {
    
    private final Worker worker;

    private final String wrongName = "Name no puede ser null";
    private final String worker_idNull = "el id no puede ser null";


    public WorkerValidator(Worker worker) {
        this.worker = worker; 
    }






}
