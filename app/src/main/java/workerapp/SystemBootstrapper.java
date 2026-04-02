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



/**
 * SystemBootstrapper is responsible for initializing the application components and building the main application runner.
 */
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



    /**
     *  
     * @param argumentsFromMain the command-line arguments passed from the main method, used for determining the file to load/save data.
     */
    public SystemBootstrapper(String[] argumentsFromMain){
        this.argumentsFromMain = argumentsFromMain; 
    }


    /**
     * Builds the main application runner.
     * 
     * @return the initialized ApplicationRunner
     */
    public ApplicationRunner buildApplicationRunner() {
        initInfrastructure();
        initRepositories();
        initFileStructure();
        initBuildersMainObject();
        initCommands();
        loadData();
        return new ApplicationRunner(console, inputProvider, commandRegistry, scriptExecutionStack); 
    }


    /**
     * Initializes the infrastructure components.
     */
    public void initInfrastructure() {  
        this.inputProvider = new InputProvider(); 
        this.console = new StandardConsole(); 

    }


    /**
     * Initializes the repositories for managing application data and commands.
     */
    public void initRepositories() {
        this.workerRepository = new WorkerRepository(); 
        this.commandRegistry = new CommandRegistry(console);
        this.scriptExecutionStack = new ScriptExecutionStack(inputProvider, console); 
    } 


    /**
     * Initializes the file structure for loading and saving worker data, based on the command-line arguments provided at startup.
     */
    public void initFileStructure(){
        String file = StartupValidator.getValidFileName(argumentsFromMain, console);
        File targetSaveFile= new File(file);
        File targetLoadFile = workerapp.util.FindFile.findFile(new File(file));
        this.formLoad = new LoadFromCSV(targetLoadFile, console, this.workerRepository);
        this.formSave = new SaveToCSV(targetSaveFile, console, this.workerRepository);
        this.workerRepository.setFormLoad(this.formLoad);
        this.workerRepository.setFormSave(this.formSave);
    }


    /**
     * Initializes the main builders for creating application objects.
     */
    public void initBuildersMainObject() {
        this.coordinatesBuild = new CoordinatesBuilder(inputProvider, console);
        this.organizationBuild = new OrganizationBuilder(inputProvider, console);
        this.workerBuilder = new WorkerMainBuilder(inputProvider, console);
        workerBuilder.setCoordinatesBuild(coordinatesBuild);
        workerBuilder.setOrganizationBuilder(organizationBuild);
    }


    /**
     * Initializes the commands available in the application and registers them in the command registry.
     */
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


    /**
     * Loads the worker data from the file specified at startup, using the formLoad component of the worker repository.
     */
    public void loadData() {
        workerRepository.load();
    }
}
