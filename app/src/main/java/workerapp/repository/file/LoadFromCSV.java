package workerapp.repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import workerapp.api.WorkerMapper;
import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class LoadFromCSV implements FormLoad {

    private String toFile;
    private final Console console;
    private final WorkerRepository workerRepository; 

    public LoadFromCSV(String toFile, Console console, WorkerRepository workerRepository) {
        this.toFile = toFile; 
        this.console = console;
        this.workerRepository = workerRepository;
    }

    @Override
    public void load() {
        File file = new File(toFile);
        if (file.isDirectory()) {
            console.printError("Error: Path is a directory, not a file: '" + toFile + "'");
            return;
        }
        if (!file.exists()) {
            console.printError("Error: Target file does not exist: '" + toFile + "'");
            return;
        }  
        if (!file.canRead()) {
            console.printError("Error: No read permissions for file: '" + toFile + "'");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;   
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    workerRepository.add(WorkerMapper.fromCsvLine(line));
                } catch (Exception e) {
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