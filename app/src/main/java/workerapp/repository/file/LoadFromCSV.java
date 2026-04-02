package workerapp.repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import workerapp.cli.Console;
import workerapp.mappersCsv.WorkerMapper;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;


/**
 * Class LoadFromCSV
 * Is responsible for loading Worker data from a CSV file into the WorkerRepository
 */
public class LoadFromCSV implements FormLoad {

    private final File file;
    private final Console console;
    private final WorkerRepository workerRepository; 


    /**
     * Constructor for LoadFromCSV.
     * @param file the CSV file to load data from
     * @param console the console for outputting messages and errors during the loading process
     * @param workerRepository the repository to load the Worker data into
     */
    public LoadFromCSV(File file, Console console, WorkerRepository workerRepository) {
        this.file = file;
        this.console = console;
        this.workerRepository = workerRepository;
    }


    /**
     * Loads the worker data from the CSV file into the WorkerRepository, with validation and error handling for file access and data parsing issues.
     */
    @Override
    public void load() {
        if (!FileValidator.isValidForRead(file, console)) {
            return; 
        }
        boolean isSucess = executeLoadProtocol();
        if (isSucess) {
            console.println("Workers loaded successfully. Total: " + workerRepository.getSize());
        } 
    }



    /**
     * @return true if the loading process completed successfully, false otherwise
     */
    private boolean executeLoadProtocol() {
        int lineNumber = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                processSingleLine(line, lineNumber);
            }
            return true;
        } catch (FileNotFoundException e) {
            console.printError("File access error: " + e.getMessage());
            return false; 
        } catch (Exception e) {
            console.printError("Unexpected error during load: " + e.getMessage());
            return false;
        }
    }



    /**
     *      
     * @param line the line from the CSV file to process
     * @param lineNumber the line number in the CSV file for error reporting
     */
    private void processSingleLine(String line, int lineNumber) {
        try {
            Worker worker = WorkerMapper.fromCsvLine(line);        
            if (workerRepository.isWorkerId(worker.getId())) {
                console.printError("Warning on line " + lineNumber + ": Duplicate ID found '" + worker.getId() + "'. Skipping.");
                return; 
            }
            workerRepository.add(worker);
        } catch (IllegalArgumentException e) {
            console.printError("Validation error on line " + lineNumber + ": " + e.getMessage());
        } catch (Exception e) {
            console.printError("Data parsing error on line " + lineNumber + ": " + e.getMessage());
        }
    }
}