package workerapp.repository.file;

import java.io.File;
import workerapp.cli.Console;


/**
 * Class FileValidator
 * Provides static methods to validate file paths for reading and writing operations
 */
public class FileValidator {



    /**
     * Validates if the given file is suitable for reading.
     * Checks if the file exists, is not a directory, and has read permissions.
     *
     * @param file the File object to validate
     * @param console the Console object for logging messages
     * @return true if the file is valid for reading, false otherwise
     */
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
    


    /**
     * Validates if the given file is suitable for writing.
     * Checks if the file exists, is not a directory, and has write permissions.
     *
     * @param file the File object to validate
     * @param console the Console object for logging messages
     * @return true if the file is valid for writing, false otherwise
     */
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