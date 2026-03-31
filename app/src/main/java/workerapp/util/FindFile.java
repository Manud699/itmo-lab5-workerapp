package workerapp.util;

import java.io.File;

/**
 * Class FindFile
 * This class provides a utility method to find a file, checking both the given path and a fallback path one level up.
 */
public class FindFile {

    /**
     * Finds a file by checking the given path and a fallback path one level up.
     * @param fileToCheck the file to check
     * @return the found file
     */
    public static File findFile(File fileToCheck) {
        if (!fileToCheck.exists()) {
            File fallbackFile = new File("../" + fileToCheck.getPath());
            if (fallbackFile.exists()) {
                return fallbackFile; 
            } 
        }
        return fileToCheck;
    }
}