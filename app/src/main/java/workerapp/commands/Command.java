package workerapp.commands;

/**
 * Command interface that defines the structure for all commands in the application.
 */
public interface Command {

    /**
     * Executes the command with the given argument.
     * @param argms the command argument
     * @return 0 if successful, another value if validation fails
     */
    int execute(String argms);


    /**
     * Returns the name of the command.
     * @return the name of the command
     */
    String getName(); 
    String getDescription(); 


}   
