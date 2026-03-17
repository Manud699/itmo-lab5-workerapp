package workerapp.commands;


import workerapp.cli.Console;
import workerapp.cli.formatter.TableDisplayable;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;


public class RemoveHeadCommand extends AbstractCommand implements TableDisplayable {
    
    
    private final Console console; 
    private final WorkerRepository workerRepository; 



    public RemoveHeadCommand(WorkerRepository workerRepository, Console console) {
        super("remove_head", "Prints and removes the first element of the collection");
        this.workerRepository = workerRepository; 
        this.console = console; 
    } 



    @Override
    public int execute(String argm) {

        if(!validateNoArgument(argm, console)) {
            return 1; 
        }
        
        Worker worker = workerRepository.removeHead(); 
        
        if(worker !=null ){
            console.println("Successfully removed the first worker from the collection:");
            printWorkerTable(worker, console);
            return 0; 
        } 

        console.printError("The collection is empty.");
        return 1; 
        

    } 


}
