package workerapp.commands;

import java.util.Optional;

import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;
import workerapp.util.NumberParseSafe;

public class RemoveByIdCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;  
    private final Console console; 


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
        if(workerRepository.isCollectionEmpty()) {
            console.printError("The collection is empty.");
            return 0;
        }
        Optional<Long> parseId = NumberParseSafe.parse(argument, Long::parseLong); 
        if (!parseId.isPresent()) {
            console.printError("Invalid number format. Please enter a valid whole number.");
            return 2; 
        }
        long workerId = parseId.get();
        if(workerRepository.removeById((workerId))) {
            console.println("Worker with ID '" + workerId + "'' has been successfully removed.");
            return 0; 
        } 
        console.printError("Worker with ID " + "'"+ workerId + "'"+" not found.");
        return 3;   
    } 
}
