package workerapp.commands;

import workerapp.cli.Console;
import workerapp.repository.CommandRegistry;

public class HistoryCommand extends AbstractCommand {

    private final Console console; 
    private final CommandRegistry commandRegistry; 



    public HistoryCommand(CommandRegistry commandRegistry, Console console){
        super("history","Prints the last 11 executed commands (without their arguments)");
        this.commandRegistry = commandRegistry; 
        this.console = console; 
    }



    @Override
    public int execute(String argument){
        if(!validateNoArgument(argument, console)) {
            return 1; 
        }
        console.println("----------------- Command History -----------------");
        commandRegistry.getHistoryCommands().stream().forEach(console::println);
        return 0;
    }



}
