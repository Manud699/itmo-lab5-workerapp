package workerapp.commands;

import workerapp.cli.Console;
import workerapp.model.builders.WorkerMainBuilder;
import workerapp.repository.WorkerRepository;

/**
 * Command: AddCommand
 * Command description: Adds a new worker to the collection.
 */
public class AddCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console; 
    private final WorkerMainBuilder formWorker; 



    public AddCommand(WorkerRepository workerRepository, Console console, WorkerMainBuilder formWorker) {
        super("add", "Adds a new worker to the collection");
        this.workerRepository = workerRepository;
        this.console = console; 
        this.formWorker = formWorker;   
    } 



    /**
     * Executes the add command.
     * 
     * @param argms the command argument
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        workerRepository.add(formWorker.build());
        console.println("Worker successfully added to the collection.");
        return 0; 
    }
    
}
