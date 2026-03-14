package workerapp.commands;



import workerapp.cli.Console;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;

public class HeadCommand extends AbstractCommand implements TableDisplayable {

    private final WorkerRepository workerRepository;
    private final Console console;  



    public HeadCommand(WorkerRepository workerRepository, Console console) {
        super("head","Prints the first element of the collection"); 
        this.workerRepository = workerRepository; 
        this.console = console; 
    } 



    @Override
    public int execute(String argms){

        if(!validateNoArgument(argms, console)) {
            return 1; 
        } 
        Worker worker = workerRepository.getHead(); 
            if(worker !=null ){
            console.println("First worker in the collection:");
            printWorkerTable(worker, console);
            return 0; 
        } 
        console.printError("The collection is empty.");
        return 1; 

    } 
}
