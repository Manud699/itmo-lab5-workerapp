package workerapp.commands;

import workerapp.cli.Console;
import workerapp.cli.formatter.TableDisplayable;
import workerapp.repository.WorkerRepository;


/**
 * Command: ShowCommand
 * Command description: Displays all elements of the collection.
 */
public class ShowCommand extends AbstractCommand implements TableDisplayable {

    private WorkerRepository workerRepository; 
    private final Console console;  


    /**
     * Constructor for the ShowCommand class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     */
    public ShowCommand(WorkerRepository workerRepository, Console console) {
        super("show", "Displays all elements of the collection");
        this.workerRepository = workerRepository;
        this.console = console; 
    } 


    /**     
     *  Executes the show command.
     * 
     * @param argument the command argument
     * @return 0 if successful, 1 if validation fails or the collection is empty
     */
    @Override
    public int execute(String argument) {
        if(!validateNoArgument(argument, console)){
            return 1; 
        }
        if (workerRepository.isCollectionEmpty()) {
            console.println("The collection is empty.");
            return 1;
        }

        printWorkerTable(workerRepository.getWorkersSortedById(), console);
        return 0;
    }

}
