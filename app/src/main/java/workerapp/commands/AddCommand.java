package workerapp.commands;


import workerapp.cli.Console;
import workerapp.model.build.WorkerBuilder;
import workerapp.repository.WorkerRepository;


public class AddCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console; 
    private final WorkerBuilder formWorker; 
    

    public AddCommand(WorkerRepository workerRepository, Console console, WorkerBuilder formWorker) {
        super("add", "Adds a new worker to the collection");
        this.workerRepository = workerRepository;
        this.console = console; 
        this.formWorker = formWorker;   
    } 



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
