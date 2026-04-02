package workerapp.model.builders;

import workerapp.cli.Console;
import workerapp.cli.InputProvider;
import workerapp.model.Coordinates;
import workerapp.model.WorkerIdGenerator;
import workerapp.model.Organization;
import workerapp.model.Position;
import workerapp.model.Status;
import workerapp.model.Worker;
import java.time.ZonedDateTime;



/**
 * Builder for creating Worker {@link Worker}.
 */
public class WorkerMainBuilder extends AbstractConsoleBuilder<Worker> {


    private OrganizationBuilder organization;
    private CoordinatesBuilder coordinates;


    private final long MIN_SALARY = 0; 

    /**
     * Constructor for the WorkerMainBuilder class.
     * @param inputProvider the input provider for managing input sources
     * @param console the console for input/output operations
     */
    public WorkerMainBuilder(InputProvider inputProvider, Console console) {
        super(inputProvider, console);
    } 

    /**
     * Builds a new Worker instance{@link Worker}.
     * @return the created Worker instance{@link Worker}.
     */
    public Worker build() {
            return new Worker(
                            WorkerIdGenerator.generateID(), 
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
