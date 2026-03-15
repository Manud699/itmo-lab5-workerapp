package workerapp.repository.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import workerapp.cli.Console;
import workerapp.mappersCsv.WorkerToCsvLine;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;

public class SaveToCSV implements FormSave {
    
    private final File file;
    private final Console console; 
    private final WorkerRepository workers;

    public SaveToCSV(File file, Console console, WorkerRepository workers) {
        this.file = file;
        this.console = console;
        this.workers = workers; 
    }       

    @Override
    public void save() {
        File targetFile = this.file;
    
        if (!FileValidator.isValidForWrite(targetFile, console)) {
            targetFile = new File("dafault.csv");
            if (!FileValidator.isValidForWrite(targetFile, console)) {
                console.printError("Critical Error: dafault path is also blocked. Data cannot be saved.");
                return;
            }
        }

        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
                console.println("Notice: Created save file at: " + targetFile.getAbsolutePath());
            } catch (IOException e) {
                console.printError("Failed to create file: " + e.getMessage());
                return;
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFile))) {
            for (Worker worker : workers.getWorkers()) {
                String workerToSave = WorkerToCsvLine.toCsvLine(worker);
                bufferedWriter.write(workerToSave);
                bufferedWriter.newLine();
            }
            console.println("Workers successfully saved to file: " + targetFile.getName());
        } catch (IOException e) {
            console.printError("I/O error while saving: " + e.getMessage());
        } catch (Exception e) {
            console.printError("Unexpected error: " + e.getMessage());
        }
    } 
}