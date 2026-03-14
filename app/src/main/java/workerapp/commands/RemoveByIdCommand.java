package workerapp.commands;

import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class RemoveByIdCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;  
    private final Console console; 
    private final long INVALID_NUMBER_FORMAT = -1L; 


    public RemoveByIdCommand( WorkerRepository workerRepository, Console console){
        super("remove_by_id", "Removes an element from the collection by its ID"); 
        this.console = console;  
        this.workerRepository = workerRepository;  
    }  



    @Override 
    public int execute(String argument) {
            if(!validateHasArgument(argument, console)) {
                console.printError("Please provide the ID of the worker to remove.");
                return 1; 
            }
            long workerId = parseWorkerId(argument);
            if (workerId == (int)INVALID_NUMBER_FORMAT) {
                console.printError("Invalid number format. Please enter a valid whole number.");
                return 1; 
            }
            if(workerRepository.isCollectionEmpty()) {
                console.printError("The collection is empty.");
            }
            if(workerRepository.removeById((workerId))) {
                console.println("Worker with ID '" + workerId + "'' has been successfully removed.");
                return 0; 
            } 
            console.printError("Worker with ID " + "'"+ workerId + "'"+" not found.");
            return 1; 
        
        
        
    } 



    public long parseWorkerId(String workerId) {
        try {
            return Long.parseLong(workerId);
        } catch (NumberFormatException e) {
            return INVALID_NUMBER_FORMAT;
        }
    } 

}
