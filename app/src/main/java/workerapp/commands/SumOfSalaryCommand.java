package workerapp.commands;



import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class SumOfSalaryCommand extends AbstractCommand {
    


    private final  WorkerRepository workerRepository;
    private final Console console; 
    
    
    public SumOfSalaryCommand(WorkerRepository workerRepository, Console console) {
        super("sum_of_salary","Displays the sum of the salaries of all elements in the collection");
        this.workerRepository = workerRepository; 
        this.console = console;
    } 



    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        console.println("Total salary of all workers: " + workerRepository.sumOfSalary());
        return 0; 

    } 

}
