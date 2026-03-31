package workerapp.commands;

import workerapp.cli.Console;

/**
 * Command: ExitCommand
 * Command description: Terminates the program.
 */
public class ExitCommand extends AbstractCommand  {

    private final Console console; 

    public ExitCommand(Console console) {
        super("exit", "Terminates the program");
        this.console = console;
    } 



    /**
     * Executes the exit command.
     * 
     * @param argms the command argument
     * @return 0 if successful, another value if validation fails
     */
    public int execute(String argms) {        
        console.println("Terminating program...");
        System.exit(0);
        return 1; 
    } 


}
