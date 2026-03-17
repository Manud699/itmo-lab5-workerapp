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
    public void save(){
        if(!FileValidator.isValidForWrite(this.file, console)) return;
        if(!this.file.exists()){
            boolean isCreated = createFile(this.file);
            if (!isCreated) return;
        }
        executeSaveProtocol(this.file);
    }



    public boolean createFile(File targetFile){
        try {
            if(!targetFile.createNewFile()) {
                console.println("File already exists.");
                return true; 
            }  
            console.println("Notice: Created save file at:" + targetFile.getName() );
            return true;
        } catch (IOException e) {
            console.printError("Failed to create file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            console.printError("Unexpected error while attempting to save.");
            return false; 
        } 
    } 



    public void executeSaveProtocol(File file) {
    try {
        writeWorkersToFile(file);
        console.println("Workers successfully saved to file:" + file.getName());
    } catch (IOException e) {
        console.printError("I/O error while saving: " + e.getMessage());
    } catch(Exception e) {
        console.printError("An unexpected error occurred.");
        } 
    }  



    public void writeWorkersToFile(File file) throws IOException{
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for(Worker worker : workers.getWorkers()){
                String lineToSave = WorkerToCsvLine.toCsvLine(worker);
                bufferedWriter.write(lineToSave);
                bufferedWriter.newLine();
            }
        }
    }

}