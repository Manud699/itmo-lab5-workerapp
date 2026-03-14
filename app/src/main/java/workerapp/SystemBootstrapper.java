package workerapp;

import workerapp.cli.*;
import workerapp.commands.*;
import workerapp.model.build.CoordinatesBuild;
import workerapp.model.build.OrganizationBuild;
import workerapp.model.build.WorkerBuilder;
import workerapp.repository.CommandRegistry;
import workerapp.repository.ScriptExecutionStack;
import workerapp.repository.WorkerRepository;
import workerapp.repository.file.FormLoad;
import workerapp.repository.file.FormSave;
import workerapp.repository.file.LoadFromCSV;
import workerapp.repository.file.SaveToCSV;

public class SystemBootstrapper {

    private InputProvider inputProvider; 
    private Console console;
    private CommandRegistry commandRegistry; 
    private WorkerRepository workerRepository; 
    private ScriptExecutionStack scriptExecutionStack; 
    private String[] argumentsFromMain;
    private FormLoad formLoad; 
    private FormSave formSave; 
    private WorkerBuilder workerBuilder;
    private CoordinatesBuild coordinatesBuild;
    private OrganizationBuild organizationBuild;



    public SystemBootstrapper(String[] argumentsFromMain){
        this.argumentsFromMain = argumentsFromMain; 
    }



    public ApplicationRunner buildApplicationRunner() {
        initInfrastructure();
        initRepositories();
        initFileStructure();
        initBuildersMainObject();
        initCommands();
        loadData();
        return new ApplicationRunner(console, inputProvider, commandRegistry); 
    }



    public void initInfrastructure() {  
        this.inputProvider = new InputProvider(); 
        this.console = new StandardConsole(); 
    }



    public void initRepositories() {
        this.workerRepository = new WorkerRepository(); 
        this.commandRegistry = new CommandRegistry(console);
        this.scriptExecutionStack = new ScriptExecutionStack(inputProvider, console); 
    } 



    public void initFileStructure(){
        String file = StartupValidator.getValidFileName(argumentsFromMain, console);
        this.formLoad = new LoadFromCSV(file, console, this.workerRepository);
        this.formSave = new SaveToCSV(file, console, this.workerRepository);
        this.workerRepository.setFormLoad(this.formLoad);
        this.workerRepository.setFormSave(this.formSave);
    }



    public void initBuildersMainObject() {
        this.coordinatesBuild = new CoordinatesBuild(inputProvider, console);
        this.organizationBuild = new OrganizationBuild(inputProvider, console);
        this.workerBuilder = new WorkerBuilder(inputProvider, console);
        workerBuilder.setCoordinatesBuild(coordinatesBuild);
        workerBuilder.setOrganizationBuilder(organizationBuild);
    }



    public void initCommands() {   
        commandRegistry.addCommand(new AddCommand(workerRepository, console, workerBuilder));
        commandRegistry.addCommand(new ShowCommand(workerRepository, console));
        commandRegistry.addCommand(new RemoveByIdCommand(workerRepository, console));
        commandRegistry.addCommand(new HeadCommand(workerRepository, console));
        commandRegistry.addCommand(new RemoveHeadCommand(workerRepository, console));
        commandRegistry.addCommand(new SumOfSalaryCommand(workerRepository, console));
        commandRegistry.addCommand(new HelpCommand(commandRegistry, console));
        commandRegistry.addCommand(new HistoryCommand(commandRegistry, console));
        commandRegistry.addCommand(new ClearCommand(workerRepository, console));
        commandRegistry.addCommand(new InfoCommand(workerRepository, console));
        commandRegistry.addCommand(new UpdateByIdCommand(workerRepository, console, workerBuilder));
        commandRegistry.addCommand(new SaveCommand(console, workerRepository));
        commandRegistry.addCommand(new ExitCommand(console));
        commandRegistry.addCommand(new ExecuteScriptCommand(commandRegistry, console, scriptExecutionStack));
        commandRegistry.addCommand(new PrintFieldDescendingSalaryCommand(workerRepository, console));
        commandRegistry.addCommand(new RemoveAllByPosition(workerRepository, console));
    }



    public void loadData() {
        workerRepository.load();
    }
}
