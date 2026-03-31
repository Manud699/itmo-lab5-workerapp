package workerapp.repository.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import workerapp.cli.Console;
import workerapp.mappersCsv.WorkerToCsvLine;
import workerapp.model.Worker;
import workerapp.repository.WorkerRepository;

/**
 * Class SaveToCSV
 * This class is responsible for saving the collection of Worker objects to a CSV file.
 */
public class SaveToCSV implements FormSave {
    
    private final File file;
    private final Console console; 
    private final WorkerRepository workers;

    public SaveToCSV(File file, Console console, WorkerRepository workers) {
        this.file = file;
        this.console = console;
        this.workers = workers; 
    }       

    /**
     * Saves the collection of Worker objects to the CSV file.
     */
    @Override
    public void save(){
        if(!FileValidator.isValidForWrite(this.file, console)) return;
        if(!this.file.exists()){
            boolean isCreated = createFile(this.file);
            if (!isCreated) return;
        }
        executeSaveProtocol(this.file);
    }

    /**
     * @param targetFile the file to create
     * @return true if the file was created successfully or already exists, false otherwise
     */
    public boolean createFile(File targetFile){
        try {
            File parentDirectory = targetFile.getParentFile();
            if(parentDirectory != null && !parentDirectory.exists()){
                parentDirectory.mkdirs(); 
            } 
            if(!targetFile.createNewFile()) {
                console.println("File already exists at: " + targetFile.getAbsolutePath());
                return true; 
            }  
            console.println("Notice: Created save file at: " + targetFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            console.printError("Failed to create file at '" + targetFile.getAbsolutePath() + "': " + e.getMessage());
            return false;
        } catch (Exception e) {
            console.printError("Unexpected error while attempting to save at '" + targetFile.getAbsolutePath() + "'.");
            return false; 
        } 
    } 

    /**
     * Executes the save protocol for the specified file.
     * @param file the file to save the workers to
     */
    public void executeSaveProtocol(File file) {
    try {
        writeWorkersToFile(file);
        // <-- CAMBIADO a getAbsolutePath()
        console.println("Workers successfully saved to file: " + file.getAbsolutePath());
    } catch (IOException e) {
        console.printError("I/O error while saving to '" + file.getAbsolutePath() + "': " + e.getMessage());
    } catch(Exception e) {
        console.printError("An unexpected error occurred while saving.");
        } 
    }  

    /**
     * Writes the collection of Worker objects to the specified file in CSV format.
     * @param file the file to write the workers to
     * @throws IOException if an I/O error occurs while writing to the file
     */
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