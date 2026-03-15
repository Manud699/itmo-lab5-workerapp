package workerapp.repository.file;

import java.io.File;
import workerapp.cli.Console;

public class FileValidator {


    public static boolean isValidForRead(File file, Console console) {
        if (file.isDirectory()) {
            console.printError("Error: Path is a directory: '" + file.getAbsolutePath() + "'");
            return false;
        }
        if (!file.exists()) {
            console.println("Notice: Database file does not exist yet. Starting with an empty collection.");
            return false;
        }
        if (!file.canRead()) {
            console.printError("Error: No read permissions for: '" + file.getAbsolutePath() + "'");
            return false;
        }
        return true;
    }
    


    public static boolean isValidForWrite(File file, Console console) {
        if (file.isDirectory()) {
            console.printError("Error: Path is a directory. Cannot save.");
            return false;
        }
        if (!file.exists()) {
            console.printError("Target file does not exist '" + file+"'");
            return false; 
        }
        if (!file.canWrite()) {
            console.printError("Error: No write permissions for: '" + file.getAbsolutePath() + "'. Cannot save.");
            return false;
        }
        return true;
    }
}