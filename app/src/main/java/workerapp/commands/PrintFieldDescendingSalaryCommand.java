package workerapp.commands;

import workerapp.cli.Console;
import workerapp.repository.WorkerRepository;

public class PrintFieldDescendingSalaryCommand extends AbstractCommand {


    private final WorkerRepository workerRepository; 
    private final Console console; 



    public PrintFieldDescendingSalaryCommand(WorkerRepository workerRepository, Console console){
        super("print_field_descending_salary", "print the salary field values of all the elements in descending order");
        this.workerRepository = workerRepository; 
        this.console = console;
    }
    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        } 
        workerRepository.printFieldDescendingSalary(console);
        return 0; 
    }
}
