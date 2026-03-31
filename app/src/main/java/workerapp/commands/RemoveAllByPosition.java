package workerapp.commands;

import workerapp.cli.Console;
import workerapp.model.Position;
import workerapp.repository.WorkerRepository;


/**
 * Command: remove_all_by_position
 * Description: Removes all elements from the collection whose position field is equivalent to the specified one.
 */
public class RemoveAllByPosition extends AbstractCommand  {
    
    private final WorkerRepository workerRepository; 
    private final Console console;



    public RemoveAllByPosition(WorkerRepository workerRepository, Console console) {
        super("remove_all_by_position", "Removes all elements from the collection whose position field is equivalent to the specified one");
        this.workerRepository = workerRepository; 
        this.console = console; 
    }


    /**
     * Executes the remove_all_by_position command.
     * 
     * @param argm the command arguments
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argm){
        if(!validateHasArgument(argm, console)){
            console.printError("");
            return 1; 
        }
        if(workerRepository.isCollectionEmpty()){
            console.println("The collection is empty");
            return 2; 
        }
        
        Position position = convertToStatusEnum(argm); 
        if(position == null){
            console.printError("Error: '" + argm + "' is not a valid position.");
            return 3; 
        }

        boolean removed = workerRepository.getWorkers().removeIf(worker -> worker.getPosition() == position);
        if (removed) {
            console.println("Successfully removed all workers with position: " + position.name());
            return 0; 
        }   
        console.println("No workers found with the specified position. Nothing was removed.");
        return 4; 
    }



    /**
     * Converts a string to a Position enum value.
     * 
     * @param argm the string to convert
     * @return the corresponding Position enum value, or null if invalid
     */
    public Position convertToStatusEnum(String argm) {
        try {
            return Position.valueOf(argm.trim().toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null; 
        }
    } 

}
