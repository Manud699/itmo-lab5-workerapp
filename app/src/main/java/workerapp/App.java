package workerapp;

import workerapp.cli.ApplicationRunner;

public class App {
    public static void main(String[] args) {
        SystemBootstrapper systemBootstrapper = new SystemBootstrapper(args); 
        ApplicationRunner applicationRunner = systemBootstrapper.buildApplicationRunner(); 
        applicationRunner.start();
    }
}