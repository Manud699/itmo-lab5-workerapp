package workerapp.cli;

import java.util.Scanner;
import workerapp.repository.CommandRegistry;
import workerapp.repository.ScriptExecutionStack;

/**
 * ApplicationRunner is responsible for running the command-line interface of the application.
 */
public class ApplicationRunner {

    private final Console console;
    private final InputProvider inputProvider;
    private final CommandRegistry commandRegistry;
    private final boolean isRunning;  
    private final ScriptExecutionStack scriptExecutionStack;
    private Scanner scanner; 

    private int successfulCommands = 0;
    private int failedCommands = 0;


    /**
     * Initializes the ApplicationRunner with the specified dependencies.
     * @param console the console for input/output operations
     * @param inputProvider the input provider for managing input sources
     * @param commandRegistry the registry for managing commands
     * @param scriptExecutionStack the stack for managing script execution
     */
    public ApplicationRunner(Console console, InputProvider inputProvider, CommandRegistry commandRegistry, ScriptExecutionStack scriptExecutionStack) {
        this.console = console;
        this.inputProvider = inputProvider;
        this.commandRegistry = commandRegistry;
        this.isRunning = true;
        this.scriptExecutionStack = scriptExecutionStack;
    }   


    /**
     * Starts the application runner, continuously reading user input and executing commands until the program is terminated.
     */
    public void start() {
        console.println("Your welcome to AppWorker. Enter 'help' for more information");
        while (isRunning) {
            scanner = inputProvider.getCurrentScanner(); 
            if(inputProvider.isInteractiveMode()){
                console.ps1();
            }
            if(!scanner.hasNextLine()) {
                if(!inputProvider.isInteractiveMode()) {
                    scriptExecutionStack.exitCurrentScript();
                    if (inputProvider.isInteractiveMode()) {
                        showScriptSummary();
                    }
                    continue; 
                } else {
                    break; 
                }
            }
            String inputLine = scanner.nextLine(); 
            if(inputLine.trim().isEmpty()) {
                continue; 
            }
            
            if(!inputProvider.isInteractiveMode()){
                console.println(inputLine);
            }

            String[] commandAndArgument = spliterInputLine(inputLine);
            String commandName = commandAndArgument[0];
            String argument = commandAndArgument[1]; 

            try {
                int exitCode = commandRegistry.executeCommand(commandName, argument);
                if(!inputProvider.isInteractiveMode()){
                    if(exitCode == 0) {
                        successfulCommands++; 
                    } else {
                        failedCommands++; 
                    }
                }
            } catch (IllegalArgumentException e) {
                if (!inputProvider.isInteractiveMode()) {
                    console.printError("Script execution aborted. " + e.getMessage());
                    scriptExecutionStack.exitCurrentScript();
                    if (inputProvider.isInteractiveMode()) {
                        showScriptSummary();
                    }
                } else {
                    console.printError(e.getMessage());
                }
            } 
        }
    }



    /**
     * Splits the input line into command name and argument.
     * 
     * @param inputLine The raw input line from the user.
     * @return An array where the first element is the command name and the second element is the argument.
     */
    public String[] spliterInputLine(String inputLine) {
        String[] spliter = inputLine.split("\\s+", 2);  
        String commandName = spliter[0]; 
        String argument = (spliter.length > 1) ? spliter[1] : "";
        return new String[]{commandName, argument}; 
    }



    /**
     * Displays a summary of script execution, showing the number of successfully executed commands and failed commands.
     */
    public void showScriptSummary() {
        System.lineSeparator();
        console.println("---------------------------------------------------"); 
        console.println("Script Execution Summary");
        console.println("Successfully executed: " + successfulCommands);
        console.println("Failed commands: " + failedCommands);
        successfulCommands = 0; 
        failedCommands = 0;
    } 


}


