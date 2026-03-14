package workerapp.model;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Worker implements Comparable<Worker> {
    
    private long id;
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long salary; //Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Organization organization; //Поле не может быть nul


    public Worker(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, long salary, Position position, Status status, Organization organization) {
        this.id = id; 
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;  
        this.salary = salary; 
        this.position = position;
        this.status = status; 
        this.organization = organization;
    } 
    
    


    //getter
    public long getId() {
        return id; 
    } 



    public String getName() {
        return name; 
    } 



    public Coordinates getCoordinates(){
        return coordinates;

    } 



    public ZonedDateTime getCreationDate() {
        return creationDate; 
    }



    public long getSalary() {
        return salary; 
    }



    public Position getPosition() {
        return position; 
    }
    
    
    public Status getStatus() {
        return status; 
    }



    public Organization getOrganization() {
        return organization; 
    } 
    



    //setters
    public void setId(long id) {
        this.id = id; 
    } 



    @Override
    public int compareTo(Worker otherWorker){
        return (int) (this.id - otherWorker.id);
    }  



    @Override
    public boolean equals(Object object) {
        if(object instanceof Worker otherWorker) {
            return this.id == otherWorker.id; 
        } 
        return false; 
    }



    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    } 


    @Override
    public String toString() {
        // 1. Limpieza de datos base
        String safeName = (name != null && name.length() > 15) ? name.substring(0, 12) + "..." : name;
        
        String coordsStr = (coordinates != null) ? "(" + coordinates.getX() + ", " + coordinates.getY() + ")" : "N/A";
        if (coordsStr.length() > 12) coordsStr = coordsStr.substring(0, 9) + "...";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateStr = (creationDate != null) ? creationDate.format(formatter) : "N/A";
        
        String safePos = (position != null) ? position.name() : "N/A";
        String safeStatus = (status != null) ? status.name() : "N/A";

        
        String orgName = "N/A";
        String orgTurnover = "N/A";
        String orgEmp = "N/A";
        
        if (organization != null) {
            orgName = organization.getFullName();
            if (orgName.length() > 15) orgName = orgName.substring(0, 12) + "...";
            
            // Formateamos el float para que se vea limpio (ej. 1500000.0)
            orgTurnover = String.format("%.1f", organization.getAnnualTurnover());
            orgEmp = String.valueOf(organization.getEmployeesCount());
        }

        // 3. Plantilla expandida (Agregamos %-12s para Turnover y %-9s para Employees)
        return String.format("%-4d | %-15s | %-12s | %-16s | %-8d | %-18s | %-15s | %-15s | %-12s | %-9s", 
                id, 
                safeName, 
                coordsStr, 
                dateStr, 
                salary, 
                safePos, 
                safeStatus, 
                orgName,
                orgTurnover,
                orgEmp);
    }

}
