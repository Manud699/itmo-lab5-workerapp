package workerapp.commands;

import workerapp.cli.Console;
import workerapp.model.Worker;
import workerapp.model.build.WorkerBuilder;
import workerapp.repository.WorkerRepository;

public class UpdateByIdCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console; 
    private static final long INVALID_NUMBER_FORMAT = -1L;
    private final WorkerBuilder workerBuilder; 
    

    public UpdateByIdCommand(WorkerRepository workerRepository, Console console, WorkerBuilder workerBuilder) {
        super("update_by_id", "Updates a worker by ID"); 
        this.workerRepository = workerRepository; 
        this.console = console; 
        this.workerBuilder = workerBuilder; 
    } 



    @Override
    public int execute(String argms) {
        if(!validateHasArgument(argms, console)) {
            console.printError("Worker ID must be provided.");
            return 1; 
        }
        long workerId = parseWorkerId(argms);
        if (workerId == INVALID_NUMBER_FORMAT) {
            console.printError("Invalid number format. Please enter a valid whole number");
            return -1; 
        }
        if(workerRepository.isWorkerId(workerId)) {
            console.println("Updating worker with ID " + workerId + "...");
            Worker newWorker = workerBuilder.build();
            workerRepository.updateWorkerById(workerId, newWorker);
            console.println("Worker with ID " + workerId + " was successfully updated");
            return 0; 
        }    
        console.printError("Worker ID not found in the collection '" + argms+"'" );
        return 1; 
    } 



    public long parseWorkerId(String workerId) {
        try {
            return Long.parseLong(workerId.trim());
        } catch (NumberFormatException e) {
            return INVALID_NUMBER_FORMAT;
        }
    } 

}
