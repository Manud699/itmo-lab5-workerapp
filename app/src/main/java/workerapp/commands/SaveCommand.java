package workerapp.commands;



import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class SaveCommand extends AbstractCommand {

    private final Console console; 
    private final WorkerRepository workerRepository; 


    public SaveCommand(Console console, WorkerRepository workerRepository) {
        super("save", "Writes the collection data to the storage file");
        this.console = console;
        this.workerRepository = workerRepository;
    } 


    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        workerRepository.save(workerRepository.getWorkers()); 
        return 0; 
    }  


}
