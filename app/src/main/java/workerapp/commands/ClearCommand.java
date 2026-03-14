package workerapp.commands;

import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class ClearCommand extends AbstractCommand {
    
    private  WorkerRepository workerRepository; 
    private Console console; 

    public ClearCommand(WorkerRepository workerRepository, Console console) {
        super("clear", "Clears all elements from the collection");
        this.workerRepository = workerRepository;
        this.console = console; 
    } 



    @Override
    public int execute(String argms) {

        if(!validateNoArgument(argms, console)) {
            return 1;
        } 

        workerRepository.clear();
        console.println("Collection cleared successfully");
        return 0; 
    } 





}
