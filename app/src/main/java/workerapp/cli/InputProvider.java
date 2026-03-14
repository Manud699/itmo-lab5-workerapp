package workerapp.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class InputProvider {


    private final Deque<Scanner> scanners;
    private boolean isInteractiveMode = true; 

    public InputProvider() {
        this.scanners = new ArrayDeque<>();
        this.scanners.push(new Scanner(System.in)); 
    }



    public void connectToFile(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        scanners.push(fileScanner);
        this.isInteractiveMode = false; 
    }



    public void disconnectCurrentFile() {
        if (scanners.size() > 1) {
            scanners.pop().close();
            this.isInteractiveMode = true; 
        }
    }



    public Scanner getCurrentScanner() {
        return scanners.peek(); 
    }



    public boolean isInteractiveMode() {
        return this.isInteractiveMode;
    }

}