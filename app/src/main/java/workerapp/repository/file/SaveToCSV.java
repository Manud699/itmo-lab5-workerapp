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
    
    private  String stringToFile;
    private final Console console; 
    private final WorkerRepository workers;
    

    public SaveToCSV(String  stringToFile, Console console, WorkerRepository workers) {
        this.console = console;
        this.stringToFile = stringToFile; 
        this.workers = workers; 
    }       

    @Override
    public void save()  {
        File file = new File(stringToFile);
        if(!file.exists()) {
            try {
                file.createNewFile();
                console.println("Target file missing. Creating: " + stringToFile);
            } catch (IOException e) {
                console.printError("Failed to create file:"  + e.getMessage());
            }
        }

        if(file.isDirectory()) {
            console.println("Error: Path is a directory, not a file: '" + stringToFile + "'"); 
            return; 
        }

        if (!file.canWrite()) {
            console.printError("Error: No write permissions for the specified file.");
            return; 
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {
            for (Worker worker : workers.getWorkers()) {
                String workerToSave = WorkerToCsvLine.toCsvLine(worker);
                bufferedWriter.write(workerToSave);
                bufferedWriter.newLine();
            }
            console.println("Workers successfully saved to file.");
        } catch (IOException e) {
            console.printError("I/O error while saving: "+ e.getMessage());
            return; 
        } catch(Exception e){
            console.printError("Unexpected error:" + e.getMessage());
            return;
        }
    } 
}