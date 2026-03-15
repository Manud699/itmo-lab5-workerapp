package workerapp;

import java.io.File;

import workerapp.cli.*;
import workerapp.commands.*;
import workerapp.model.builders.CoordinatesBuilder;
import workerapp.model.builders.OrganizationBuilder;
import workerapp.model.builders.WorkerMainBuilder;
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
    private WorkerMainBuilder workerBuilder;
    private CoordinatesBuilder coordinatesBuild;
    private OrganizationBuilder organizationBuild;



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
        File dataBaseFile = new File(file);
        this.formLoad = new LoadFromCSV(dataBaseFile, console, this.workerRepository);
        this.formSave = new SaveToCSV(dataBaseFile, console, this.workerRepository);
        this.workerRepository.setFormLoad(this.formLoad);
        this.workerRepository.setFormSave(this.formSave);
    }



    public void initBuildersMainObject() {
        this.coordinatesBuild = new CoordinatesBuilder(inputProvider, console);
        this.organizationBuild = new OrganizationBuilder(inputProvider, console);
        this.workerBuilder = new WorkerMainBuilder(inputProvider, console);
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
