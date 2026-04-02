package workerapp;
import workerapp.cli.ApplicationRunner;


/**
 * Main class of the application. It initializes the SystemBootstrapper and starts the application runner.
 * @author manu_d699
 */
public class App {
    public static void main(String[] args) {
        SystemBootstrapper systemBootstrapper = new SystemBootstrapper(args); 
        ApplicationRunner applicationRunner = systemBootstrapper.buildApplicationRunner(); 
        applicationRunner.start();
    }
}