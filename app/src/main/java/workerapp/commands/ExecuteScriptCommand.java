package workerapp.commands;

import java.io.File;
import java.io.FileNotFoundException;

import workerapp.cli.Console;
import workerapp.repository.CommandRegistry;
import workerapp.repository.ScriptExecutionStack;


public class ExecuteScriptCommand extends AbstractCommand {

    private final Console console; 
    private final ScriptExecutionStack scriptExecutionStack; 

    public ExecuteScriptCommand(CommandRegistry commandRegistry, Console console, ScriptExecutionStack scriptExecutionStack) {
        super("execute_script", "Reads and executes the script from the specified file"); 
        this.console = console; 
        this.scriptExecutionStack = scriptExecutionStack; 
    } 

    @Override
    public int execute(String argument) {
        if (!validateHasArgument(argument, console)) {
            return 1; 
        }

        File fileScript = new File(argument);
        
        if (!fileScript.exists()) {
            console.printError("Error: The specified file does not exist: " + fileScript.getName());
            return 2; 
        }
        
        if (!fileScript.canRead()) {
            console.printError("Error: Cannot read the file (permission denied): " + fileScript.getName());
            return 3; 
        }
        

        if (scriptExecutionStack.isActiveScript(fileScript.getAbsolutePath())) {
            console.printError("Error: Infinite recursion detected. The script is already running: " + fileScript.getName());
            return 4; 
        } 

        try {
            scriptExecutionStack.connectToFileScanner(fileScript);
            return 0; 
        } catch (FileNotFoundException e) {
            console.printError("Error: Could not open the file. " + e.getMessage());
            return 5;
        }
    } 
}