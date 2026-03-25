package workerapp.cli.formatter;

import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import workerapp.cli.Console;
import workerapp.model.Worker;
import workerapp.model.Coordinates;
import workerapp.model.Organization;

public interface TableDisplayable {

    int MAX_NAME_LEN = 10;
    int MAX_COORD_PART_LEN = 7;
    int MAX_SALARY_LEN = 8;
    int MAX_POS_LEN = 9;
    int MAX_STATUS_LEN = 8;
    int MAX_ORG_NAME_LEN = 13;
    int MAX_TURNOVER_LEN = 5;
    int MAX_EMP_LEN = 5;

    String ROW_FORMAT = "%-4d | %-10s | %-15s | %-16s | %-8s | %-9s | %-8s | %-13s | %-6s | %-5s";
    String HEADER_FORMAT = "%-4s | %-10s | %-15s | %-16s | %-8s | %-9s | %-8s | %-13s | %-6s | %-5s";



    default void printSeparator(Console console) {
        console.println("-".repeat(120)); 
    }



    default void printHeader(Console console) {
        console.println(String.format(HEADER_FORMAT, 
                "ID", "NAME", "COORD[X-Y]", "CREATION DATE", "SALARY", "POSITION", "STATUS", "ORG. NAME", "TURN.O", "EMP."));
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
            console.println(formatWorkerRow(worker));
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




    default String formatWorkerRow(Worker worker) {
        String safeName = worker.getName();
        if (safeName.length() > MAX_NAME_LEN) {
            safeName = safeName.substring(0, MAX_NAME_LEN - 1) + "."; 
        }
        Coordinates coordinates = worker.getCoordinates();
        double xVal = (double) coordinates.getX();
        double yVal = coordinates.getY().doubleValue(); 
        
        String xStr = String.format(Locale.US, "%.1f", xVal);
        if (xStr.length() > MAX_COORD_PART_LEN) xStr = xStr.substring(0, 4);
        
        String yStr = String.format(Locale.US, "%.1f", yVal);
        if (yStr.length() > MAX_COORD_PART_LEN) yStr = yStr.substring(0, 4);
        
        String coordsStr = xStr + "; " + yStr; 

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateStr = worker.getCreationDate().format(formatter);
        
        String salaryStr = String.valueOf(worker.getSalary());
        if (salaryStr.length() > MAX_SALARY_LEN) salaryStr = salaryStr.substring(0, 5);

        String safePos = worker.getPosition().name();
        if (safePos.length() > MAX_POS_LEN) safePos = safePos.substring(0, 6);

        String safeStatus = worker.getStatus().name();
        if (safeStatus.length() > MAX_STATUS_LEN) safeStatus = safeStatus.substring(0, 5);

    
        Organization organization = worker.getOrganization();
        String orgName = organization.getFullName();
        if (orgName.length() > MAX_ORG_NAME_LEN) orgName = orgName.substring(0, 7) +".";
        String orgTurnover = String.format(Locale.US, "%.1f", organization.getAnnualTurnover());
        if (orgTurnover.length() > MAX_TURNOVER_LEN) orgTurnover = orgTurnover.substring(0, MAX_TURNOVER_LEN);
        String orgEmp = String.valueOf(organization.getEmployeesCount());
        if (orgEmp.length() > MAX_EMP_LEN) orgEmp = orgEmp.substring(0, 2);
        
        return String.format(Locale.US, ROW_FORMAT, 
                worker.getId(), safeName, coordsStr, dateStr, salaryStr, 
                safePos, safeStatus, orgName, orgTurnover, orgEmp);
    }



}