package workerapp.commands;

import java.time.format.DateTimeFormatter;
import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;


/**
 * Command: InfoCommand
 * Command description: Displays information about the collection.
 */
public class InfoCommand extends AbstractCommand {

    private final Console console; 
    private final WorkerRepository workerRepository; 

    /**
     * Constructor for the InfoCommand class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     */
    public InfoCommand(WorkerRepository workerRepository, Console console){
        super("info", "Displays information about the collection"); 
        this.workerRepository = workerRepository; 
        this.console = console; 
    } 



    /**
     * Executes the info command.
     * 
     * @param argument the command argument
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argument){
        if(!validateNoArgument(argument, console)) {
            return 1; 
        } 
        String tipeColection = workerRepository.getTipeCollection();
        int size = workerRepository.getSize();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = workerRepository.getCreationDate().format(formatter);
        console.println("----------informacion de la Coleccion----------");
        console.println("Type: " + tipeColection);
        console.println("Initialization Date: " + formattedDate);
        console.println("Number of Elements: " + size);
        console.println("------------------------------");    
        return 0; 
    } 


}
