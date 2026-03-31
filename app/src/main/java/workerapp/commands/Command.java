package workerapp.commands;

/**
 * Command interface that defines the structure for all commands in the application.
 */
public interface Command {

    int execute(String argms);
    String getName(); 
    String getDescription(); 


}   
