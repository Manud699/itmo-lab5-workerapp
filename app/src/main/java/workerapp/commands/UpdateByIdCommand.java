package workerapp.commands;

import java.util.Optional;
import workerapp.cli.Console;
import workerapp.model.Worker;
import workerapp.model.builders.WorkerMainBuilder;
import workerapp.repository.WorkerRepository;
import workerapp.util.NumberParseSafe;

/**
 * Command: UpdateByIdCommand
 * Command description: Updates a worker by ID.
 */
public class UpdateByIdCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console; 
    private final WorkerMainBuilder workerBuilder; 
    

    /**
     * Constructor for the UpdateByIdCommand class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     * @param workerBuilder the builder for creating worker instances
     */
    public UpdateByIdCommand(WorkerRepository workerRepository, Console console, WorkerMainBuilder workerBuilder) {
        super("update_by_id", "Updates a worker by ID"); 
        this.workerRepository = workerRepository; 
        this.console = console; 
        this.workerBuilder = workerBuilder; 
    } 



    /**
     * Executes the update_by_id command.
     * 
     * @param argms the command arguments
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argms) {
        if(!validateHasArgument(argms, console)) {
            console.printError("Worker ID must be provided.");
            return 1; 
        }
        Optional<Long> parsingId = NumberParseSafe.parse(argms, Long::parseLong);
        if(!parsingId.isPresent()) {
            console.printError("Invalid number format. Please enter a valid whole number.");
            return 2;
        }
        
        long workerId = parsingId.get();
        if(workerRepository.isWorkerId(workerId)) {
            Worker workerToUpdate = workerRepository.getWorkerMap().get(workerId);
            Worker newWorker = workerBuilder.build();
            workerToUpdate.setName(newWorker.getName());
            workerToUpdate.setCoordinates(newWorker.getCoordinates());
            workerToUpdate.setSalary(newWorker.getSalary());
            workerToUpdate.setPosition(newWorker.getPosition());
            workerToUpdate.setOrganization(newWorker.getOrganization());
            console.println("Worker with ID " + workerId + " was successfully updated");
            return 0; 
        }    
        console.printError("Worker ID not found in the collection '" + argms+"'" );
        return 1; 
    } 
}
