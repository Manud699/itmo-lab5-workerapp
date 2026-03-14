package workerapp.api;

import workerapp.model.Worker;

public class WorkerToCsvLine {
    
    public static String toCsvLine(Worker worker) {            //Necesito crear una Exeption que retorne si el formato no es el esperado
        
        StringBuilder sb = new StringBuilder();
        sb.append(worker.getId()).append(",");
        sb.append(worker.getName()).append(",");
        sb.append(worker.getCoordinates().getX()).append(",");
        sb.append(worker.getCoordinates().getY()).append(",");
        sb.append(worker.getCreationDate().toString()).append(",");
        sb.append(worker.getSalary()).append(",");
        sb.append(worker.getPosition().name()).append(","); 
        sb.append(worker.getStatus().name()).append(",");
        sb.append(worker.getOrganization().getFullName()).append(",");
        sb.append(worker.getOrganization().getAnnualTurnover()).append(",");
        sb.append(worker.getOrganization().getEmployeesCount()); 
        return sb.toString();
    }
}
