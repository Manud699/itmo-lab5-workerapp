package workerapp.commands;

import workerapp.cli.Console;

/**
 * Command: ExitCommand
 * Command description: Terminates the program.
 */
public class ExitCommand extends AbstractCommand  {

    private final Console console; 

    /**
     *  Constructor for the ExitCommand class.
      * @param console the console for input/output operations
     * @param console
     */
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
        System.exit(0);
        return 1; 
    } 


}
