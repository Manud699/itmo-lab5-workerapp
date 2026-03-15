package workerapp.commands;

import workerapp.cli.Console;
import workerapp.cli.formatter.TableDisplayable;
import workerapp.repository.WorkerRepository;



public class ShowCommand extends AbstractCommand implements TableDisplayable {

    private WorkerRepository workerRepository; 
    private final Console console;  



    public ShowCommand(WorkerRepository workerRepository, Console console) {
        super("show", "Displays all elements of the collection");
        this.workerRepository = workerRepository;
        this.console = console; 
    } 



    @Override
    public int execute(String argument) {
        if(!validateNoArgument(argument, console)){
            return 1; 
        }
        if (workerRepository.isCollectionEmpty()) {
            console.println("The collection is empty.");
            return 1;
        }

        printWorkerTable(workerRepository.getWorkers().stream().toList(), console);
        return 0;
    }

}
