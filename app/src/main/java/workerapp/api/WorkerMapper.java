package workerapp.api;

import workerapp.model.Coordinates;
import workerapp.model.Organization;
import workerapp.model.Position;
import workerapp.model.Status;
import workerapp.model.Worker;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class WorkerMapper {

    public static Worker fromCsvLine(String csvLine) throws Exception {
        // 1. Bug Fix: Prevenir que intenten parsear líneas completamente vacías o nulas
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV line cannot be null or empty");
        }

        // 2. Bug Fix: El "-1" asegura que si la última columna está vacía, no se borre del arreglo
        String[] parts = csvLine.split(",", -1);

        if (parts.length < 11) {
            throw new IllegalArgumentException("CSV line does not have the correct number of columns (expected 11): " + csvLine);
        }

        try {
            long id = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            float x = Float.parseFloat(parts[2].trim()); 
            double y = Double.parseDouble(parts[3].trim());
            Coordinates coordinates = new Coordinates(x, y);
            ZonedDateTime creationDate = ZonedDateTime.parse(parts[4].trim());
            long salary = Long.parseLong(parts[5].trim());
            if (salary <= 0) {
                throw new IllegalArgumentException("Salary must be greater than 0");
            }
            String positionStr = parts[6].trim();
            if (positionStr.isEmpty()) throw new IllegalArgumentException("Position cannot be empty");
            Position position = Position.valueOf(positionStr.toUpperCase());   
            String statusStr = parts[7].trim();
            if (statusStr.isEmpty()) throw new IllegalArgumentException("Status cannot be empty");
            Status status = Status.valueOf(statusStr.toUpperCase());
            String orgFullName = parts[8].trim();
            float orgAnnualTurnover = Float.parseFloat(parts[9].trim());
            int orgEmployeesCount = Integer.parseInt(parts[10].trim());
            Organization organization = new Organization(orgFullName, orgAnnualTurnover, orgEmployeesCount);
            return new Worker(id, name, coordinates, creationDate, salary, position, status, organization);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing a number in line: " + csvLine, e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing the date. It must be in ISO-8601 format: " + parts[4], e);
        } catch (IllegalArgumentException e) {
            if (e.getMessage() != null && !e.getMessage().startsWith("No enum constant")) {
                throw e; 
            }
            throw new IllegalArgumentException("Data validation error (possibly invalid Enum value): " + e.getMessage(), e);
        }
    }
}