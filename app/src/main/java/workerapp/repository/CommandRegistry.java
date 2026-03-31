package workerapp.repository;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workerapp.cli.Console;
import workerapp.commands.Command;


/**
 * Class CommandRegistry
 * Description: This class is responsible for managing the registration and execution of commands in the application.
 */
public class CommandRegistry {

    private final Map<String, Command> commands;    
    private final Deque<String> historyCommands;
    private final Console console; 
    private final int MAX_HISTORY_COMMANDS = 11; 


    public CommandRegistry(Console console) {
        this.console = console;
        this.commands = new HashMap<>();
        this.historyCommands = new ArrayDeque<>();  
    }


    /**
     * Executes a command by its name and argument.
     * 
     * @param commandName The name of the command to execute.
     * @param argument The argument to pass to the command.
     */
    public int executeCommand(String commandName, String argument) {
        Command command = commands.get(commandName.toLowerCase());
        if(command == null) {
            console.printError("Unrecognized input: '" + commandName + "'");
            return 1; 
        } 
        addToHistory(commandName);
        return command.execute(argument);
    }



    /**
     * @param command The command to add to the registry.
     */
    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }



    /**
     * @return A deque containing the history of executed command names.
     */
    public Deque<String> getHistoryCommands() {
        return historyCommands; 
    } 



    /**
     * @return A list of all registered commands.
     */
    public List<Command> getCommands() {
        return commands.values().stream().toList();  
    } 



    /**
     * Adds a command name to the execution history.
     * 
     * @param commandName The name of the command to add to the history.
     */
    public void addToHistory(String commandName) {
        if(historyCommands.size() == MAX_HISTORY_COMMANDS){
            historyCommands.removeFirst();        
        }  
        historyCommands.add(commandName);
    } 



}
