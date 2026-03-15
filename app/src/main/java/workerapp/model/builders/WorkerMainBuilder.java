package workerapp.model.builders;

import workerapp.cli.Console;
import workerapp.cli.InputProvider;
import workerapp.model.Coordinates;
import workerapp.model.WorkerIdManager;
import workerapp.model.Organization;
import workerapp.model.Position;
import workerapp.model.Status;
import workerapp.model.Worker;
import java.time.ZonedDateTime;




public class WorkerMainBuilder extends AbstractConsoleBuilder<Worker> {


    private OrganizationBuilder organization;
    private CoordinatesBuilder coordinates;


    private final long MIN_SALARY = 0; 


    public WorkerMainBuilder(InputProvider inputProvider, Console console) {
        super(inputProvider, console);
    } 




    @Override
    public Worker build() {
            return new Worker(
                            WorkerIdManager.generateID(), 
                            askName(),
                            askCoordinates(),
                            ZonedDateTime.now(),
                            askSalary(),
                            askPosition(),
                            askStatus(),
                            askOrganization());
    }



    public String askName() {
        return askString("name worker", "[it cannot be empty or null]", name -> name != null && !name.isEmpty());
    } 



    public long askSalary(){
        return askNumber("salary","[must be greater than zero]", salaryLamb ->   salaryLamb!=null && salaryLamb > MIN_SALARY, Long::parseLong);
    }



    public Status askStatus() {
        return askEnum("type of Status", (Status.values()));
    }



    public Position askPosition() {
        return askEnum("position", Position.values());
    }



    public Organization askOrganization() {
        return organization.build();
    } 



    public Coordinates askCoordinates() {
        return coordinates.build();
    }



    public void setOrganizationBuilder(OrganizationBuilder organizationBuild) {
        this.organization = organizationBuild; 
    }



    public void setCoordinatesBuild(CoordinatesBuilder coordinatesBuild) {
        this.coordinates = coordinatesBuild; 
    }

}
