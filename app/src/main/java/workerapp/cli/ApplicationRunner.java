package workerapp.cli;


import java.util.Scanner;

import workerapp.repository.CommandRegistry;
public class ApplicationRunner {

    private final Console console;
    private final InputProvider inputProvider;
    private final CommandRegistry commandRegistry;
    private final boolean isRunning;  
    private Scanner scanner; 

    private int successfulCommands = 0;
    private int failedCommands = 0;


    public ApplicationRunner(Console console, InputProvider inputProvider, CommandRegistry commandRegistry) {
        this.console = console;
        this.inputProvider = inputProvider;
        this.commandRegistry = commandRegistry;
        this.isRunning = true;
    }   



    public void start() {
        console.println("Your welcome to AppWorker. Enter 'help' for more information");
        while (isRunning) {
            scanner = inputProvider.getCurrentScanner(); 
            if(inputProvider.isInteractiveMode()){
                console.ps1();
            }
            if(!scanner.hasNextLine()) {
                if(!inputProvider.isInteractiveMode()) {
                    showScriptSummary();
                    inputProvider.disconnectCurrentFile();
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
                    inputProvider.disconnectCurrentFile();    
                } else {
                    console.printError(e.getMessage());
                }
            } 
        }
    }



    public String[] spliterInputLine(String inputLine) {
        String[] spliter = inputLine.split("\\s+", 2);  
        String commandName = spliter[0]; 
        String argument = (spliter.length > 1) ? spliter[1] : "";
        return new String[]{commandName, argument}; 
    }



    public void showScriptSummary() {
        System.lineSeparator();
        console.println("-------------------------------------------"); 
        console.println("Script Execution Summary");
        console.println("Successfully executed: " + successfulCommands);
        console.println("Failed commands: " + failedCommands);
        successfulCommands = 0; 
        failedCommands = 0;
    } 


}


