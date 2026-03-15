package workerapp.repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import workerapp.cli.Console;
import workerapp.mappersCsv.WorkerMapper;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;

public class LoadFromCSV implements FormLoad {

    private final File file;
    private final Console console;
    private final WorkerRepository workerRepository; 

    public LoadFromCSV(File file, Console console, WorkerRepository workerRepository) {
        this.file = file;
        this.console = console;
        this.workerRepository = workerRepository;
    }


    
    @Override
    public void load() {
        if (!FileValidator.isValidForRead(file, console)) {
            return; 
        }
        Set<Long> loadedIds = new HashSet<>();
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;   
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Worker worker = WorkerMapper.fromCsvLine(line);
                    if (!loadedIds.add(worker.getId())) {
                        console.printError("Warning on line " + lineNumber + ": Duplicate ID found '" + worker.getId() + "''. Skipping.");
                        continue;
                    }
                    workerRepository.add(worker);
                } catch (IllegalArgumentException e) {
                    console.printError("Validation error on line " + lineNumber + ": " + e.getMessage());
                } catch(Exception e) {
                    console.printError("Data parsing error on line " + lineNumber + ": " + e.getMessage());
                }
            }
            console.println("Workers loaded successfully. Total: " + workerRepository.getSize());   
        } catch (FileNotFoundException e) {
            console.printError("File access error: " + e.getMessage());
        } catch (Exception e) {
            console.printError("Unexpected error during load: " + e.getMessage());
        }
    }
}