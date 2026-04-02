package workerapp.commands;

import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;


/**
 * Command: SaveCommand 
 * Command description: Writes the collection data to the storage file.
 */
public class SaveCommand extends AbstractCommand {

    private final Console console; 
    private final WorkerRepository workerRepository; 


    /**
     * Constructor for the SaveCommand class.
     * @param console the console for input/output operations
     * @param workerRepository the repository for managing workers
     */
    public SaveCommand(Console console, WorkerRepository workerRepository) {
        super("save", "Writes the collection data to the storage file");
        this.console = console;
        this.workerRepository = workerRepository;
    } 



    /**
     * Executes the save command.
     *
     * @param argms the command arguments
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        workerRepository.save(); 
        return 0; 
    }  


}
