package workerapp.commands;


import workerapp.cli.Console;
import workerapp.repository.CommandRegistry;


/**
 * Command: HelpCommand
 * Command description: Prints a list of all available commands and their descriptions.
 */
public class HelpCommand extends AbstractCommand {

    private final Console console; 
    private final CommandRegistry commandRegistry; 


    /**
     * Constructor for the HelpCommand class.
     * @param commandManager the registry for managing commands
     * @param console the console for input/output operations
     */
    public HelpCommand(CommandRegistry commandManager, Console console){
        super("help", "Prints a list of all available commands and their descriptions");
        this.commandRegistry = commandManager; 
        this.console = console; 
    }



    /**
     * Executes the help command.
     * 
     * @param argument the command argument
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argument) {
        
        if(!validateNoArgument(argument, console)) {
            return 1; 
        }
        console.println("Commands: ");
        commandRegistry.getCommands().forEach(command -> console.printTable(command.getName(), command.getDescription()));
        return 0;  
    }
}
