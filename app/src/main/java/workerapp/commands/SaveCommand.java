package workerapp.commands;



import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class SaveCommand extends AbstractCommand {

    private final Console console; 
    private final WorkerRepository worlRepository; 


    public SaveCommand(Console console, WorkerRepository workerRepository) {
        super("save", "Writes the collection data to the storage file");
        this.console = console;
        this.worlRepository = workerRepository;
    } 


    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        worlRepository.save(worlRepository.getWorkers()); 
        return 0; 
    }  


}
