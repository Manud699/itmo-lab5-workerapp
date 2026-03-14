package workerapp.commands;

import java.util.List;
import workerapp.cli.Console;
import workerapp.model.Worker;

public interface TableDisplayable {


    default void printSeparator(Console console) {
        String separator = "-".repeat(151);
        console.println(separator);
    }



    default void printHeader(Console console) {
        console.println(String.format("%-4s | %-15s | %-12s | %-16s | %-8s | %-18s | %-15s | %-15s | %-12s | %-9s", 
                "ID", "NAME", "COORDINATES", "CREATION DATE", "SALARY", "POSITION", "STATUS", "ORG. NAME", "TURNOVER", "EMPLOYEES"));
    }



    default void printWorkerTable(List<Worker> workers, Console console) {
        if (workers == null || workers.isEmpty()) {
            console.printError("No workers to display.");
            return;
        }
        printSeparator(console);
        printHeader(console);
        printSeparator(console);
        
        for (Worker worker : workers) {
            console.println(worker.toString());
        }
        printSeparator(console);
    }



    default void printWorkerTable(Worker worker, Console console) {
        if (worker == null) {
            console.printError("Worker is null.");
            return;
        }
        printWorkerTable(List.of(worker), console);
    }
}