package workerapp.model.builders;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import workerapp.cli.*;

/**
 * AbstractConsoleBuilder is an abstract class that provides common methods for building objects through console input.
 * It uses an InputProvider to get the current Scanner and a Console to interact with the user.
 * Subclasses of AbstractConsoleBuilder will implement the build() method to create specific objects based on user input.
 */
public abstract class AbstractConsoleBuilder<T>  {


    private final InputProvider inputProvider; 
    private Scanner scanner;
    private final Console console;
    


    public AbstractConsoleBuilder(InputProvider inputProvider, Console console) {
        this.inputProvider = inputProvider;
        this.console = console; 
    }

    public abstract T build();


    /**
     * Checks if the scanner has a next line available.
     * 
     * @param scanner the scanner to check.
     */
    public boolean isSafeNextLine(Scanner scanner) { 
        if(!scanner.hasNextLine()) {
            return false;
        } 
        return true; 
    }


    /**
     * Asks the user to input a string value.
     * 
     * @param prompt the prompt for the user.
     * @param restrictions the restrictions for the input.
     * @param validator the validator for the input.
     * @return the validated string value.
     */
    public String askString(String prompt, String restrictions, Predicate<String> validator) {
        while(true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.print("Enter " + prompt + " " + restrictions + ":" );
            }
            if(!isSafeNextLine(scanner)){
                console.println("Terminando programa");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            if(validator.test(inputLine)) {
                return inputLine; 
            }
            if(inputProvider.isInteractiveMode()) {
                console.printError("Invalid input '"+inputLine+"'. Please try again.");
            } else {
                throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'"); 
            }
        } 
    }



    /**
     * Asks the user to input a number value.
     * 
     * @param prompt the prompt for the user.
     * @param restrictions the restrictions for the input.
     * @param validator the validator for the input.
     * @param parse the function to parse the input string to the desired number type.
     * @return the validated number value.
     */
    public <N> N askNumber(String prompt, String restrictions, Predicate<N> validator, Function<String, N> parse) {
        while (true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.print("Enter " + prompt + " " + restrictions + ":" );
            }
            if(!isSafeNextLine(scanner)){
                console.println("Terminando programa");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            try {
                N enterUser = parse.apply(inputLine); 
                if(validator.test(enterUser)){
                    return enterUser; 
                }
                if(inputProvider.isInteractiveMode()){
                    console.println("Invalid input. Please try again");
                } else {
                    throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'");
                }
            } catch (NumberFormatException e) {
                    console.printError("Invalid number format. Please enter a valid whole number.");
                    if (inputProvider.isInteractiveMode()) {
                        continue; 
                    } else {
                        throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'");
                    }
            }
        }   
    } 



    /**
     * Asks the user to input an enum value.
     * 
     * @param prompt the prompt for the user.
     * @param valoresAceptados the array of accepted enum values.
     * @return the validated enum value.
     */
    public <E extends Enum<E>> E askEnum(String prompt, E[] valoresAceptados) {
        while(true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.println("Enter "+ prompt);
                console.print("Available options " + Arrays.toString(valoresAceptados) +":" );
            }
            if(!isSafeNextLine(scanner)){
                console.println("Terminando programa");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            for(E item : valoresAceptados) {
                if(item.name().equals(inputLine.trim().toUpperCase())) {
                    return item; 
                }  
            }
            if(inputProvider.isInteractiveMode()){
                console.printError("Invalid option. Please choose from the available values. ");
            } else {
                throw new IllegalArgumentException("Fatal script error: Invalid value for  "+ prompt + ". Received: '" + inputLine + "'");
            } 
        } 
    } 


}    