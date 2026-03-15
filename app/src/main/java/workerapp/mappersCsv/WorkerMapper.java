package workerapp.mappersCsv;

import workerapp.model.Coordinates;
import workerapp.model.Organization;
import workerapp.model.Position;
import workerapp.model.Status;
import workerapp.model.Worker;
import workerapp.model.WorkerValidator; 

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class WorkerMapper {

    public static Worker fromCsvLine(String csvLine) throws Exception {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV line cannot be null or empty");
        }

        String[] parts = csvLine.split(",", -1);

        if (parts.length < 11) {
            throw new IllegalArgumentException("CSV line does not have the correct number of columns (expected 11): " + csvLine);
        }

        try {
            long id = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            float x = Float.parseFloat(parts[2].trim()); 
            double y = Double.parseDouble(parts[3].trim());
            Coordinates coordinates = new Coordinates(x, y);
            
            ZonedDateTime creationDate = ZonedDateTime.parse(parts[4].trim());
            long salary = Long.parseLong(parts[5].trim());
            
            Position position = Position.valueOf(parts[6].trim().toUpperCase());   
            Status status = Status.valueOf(parts[7].trim().toUpperCase());
            
            String orgFullName = parts[8].trim();
            float orgAnnualTurnover = Float.parseFloat(parts[9].trim());
            int orgEmployeesCount = Integer.parseInt(parts[10].trim());
            Organization organization = new Organization(orgFullName, orgAnnualTurnover, orgEmployeesCount);
            
            Worker worker = new Worker(id, name, coordinates, creationDate, salary, position, status, organization);
            
            WorkerValidator.validate(worker);
        
            return worker;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing a number in line: " + csvLine, e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing the date. It must be in ISO-8601 format: " + parts[4], e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data error in CSV line: " + e.getMessage(), e);
        }
    }
}