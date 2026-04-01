package workerapp.commands;

import java.io.File;
import java.io.FileNotFoundException;
import workerapp.cli.Console;
import workerapp.repository.CommandRegistry;
import workerapp.repository.ScriptExecutionStack;
import workerapp.util.FindFile;

/**
 * Command: ExecuteScriptCommand
 * Command description: Reads and executes the script from the specified file.
 */
public class ExecuteScriptCommand extends AbstractCommand {

    private final Console console; 
    private final ScriptExecutionStack scriptExecutionStack; 

    public ExecuteScriptCommand(CommandRegistry commandRegistry, Console console, ScriptExecutionStack scriptExecutionStack) {
        super("execute_script", "Reads and executes the script from the specified file"); 
        this.console = console; 
        this.scriptExecutionStack = scriptExecutionStack; 
    } 

    /**
     * Executes the execute_script command.
     * 
     * @param argument the command argument
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argument) {
        if (!validateHasArgument(argument, console)) {
            return 1; 
        }
        String scriptPath = argument.trim();
        File fileScript = new File(scriptPath);

        if (!fileScript.exists() && !scriptPath.contains("/") && !scriptPath.contains("\\")) {
            fileScript = new File("workerDataApp/" + scriptPath);
        }

        fileScript = FindFile.findFile(fileScript);

        if (!fileScript.exists()) {
            console.printError("Error: The specified script does not exist at: " + fileScript.getAbsolutePath());
            return 2; 
        }

        if (!fileScript.canRead()) {
            console.printError("Error: Cannot read the script (permission denied) at: " + fileScript.getAbsolutePath());
            return 3; 
        }

        if (scriptExecutionStack.isActiveScript(fileScript.getAbsolutePath())) {
            console.printError("Error: Infinite recursion detected. The script is already running: " + fileScript.getAbsolutePath());
            return 4; 
        } 

        try {
            scriptExecutionStack.connectToFileScanner(fileScript);
            console.println("Executing script from: " + fileScript.getAbsolutePath() + "...");
            return 0; 
        } catch (FileNotFoundException e) {
            console.printError("Error: Could not open the script file. " + e.getMessage());
            return 5;
        } catch (Exception e) {
            console.printError("An unexpected error occurred while executing the script: " + e.getMessage());
            return 6;
        }
    } 
}