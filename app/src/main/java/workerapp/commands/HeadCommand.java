package workerapp.commands;

import workerapp.cli.Console;
import workerapp.cli.formatter.TableDisplayable;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;


/**
 * Command: head
 * Description: Prints the first element of the collection.
 */
public class HeadCommand extends AbstractCommand implements TableDisplayable {

    private final WorkerRepository workerRepository;
    private final Console console;  



    public HeadCommand(WorkerRepository workerRepository, Console console) {
        super("head","Prints the first element of the collection"); 
        this.workerRepository = workerRepository; 
        this.console = console; 
    } 



    /**
     * Executes the head command.
     * 
     * @param args the command argument
     * @return 0 if successful, another value if validation fail.
     */
    @Override
    public int execute(String argms){
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        if(workerRepository.isCollectionEmpty()) {
            console.println("The collection is empty. No head element to show.");
            return 2; 
        }
        Worker firstWorker = workerRepository.getHead(); 
            console.println("First worker in the collection:");
            printWorkerTable(firstWorker, console);
            return 0; 
        } 
    
}
