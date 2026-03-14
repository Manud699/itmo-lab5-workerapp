package workerapp.commands;

import workerapp.cli.Console;

public abstract class  AbstractCommand implements Command {

        private final String nameCommand; 
        private final String description; 


    public AbstractCommand(String nameCommand, String description) {
        this.nameCommand = nameCommand; 
        this.description = description; 
    } 



    protected boolean validateNoArgument(String argument, Console console) {
        if (!argument.trim().isEmpty()) {
            console.printError("Command '" + getName() + "' does not accept arguments.");
            return false;
        }
        return true;
    }



    protected boolean validateHasArgument(String argument, Console console) {
        if (argument.trim().isEmpty()) {
            console.printError("Command '" + getName() + "' requires an argument.");
            return false;
        }
        return true;
    }



    @Override
    public String getName() {
        return nameCommand; 
    } 



    @Override
    public String getDescription(){
        return description; 
    }




}
