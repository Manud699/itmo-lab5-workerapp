package workerapp.cli;

public class StartupValidator {
    
    private static final String DEFAULT_FILE = "workers_defecto.csv";

    public static String getValidFileName(String[] args, Console console) {

        if (args == null || args.length == 0 || args[0].trim().isEmpty()) {
            console.println("Warning: No file specified in arguments. Falling back to default database:'"+ DEFAULT_FILE+"'");
            return DEFAULT_FILE;
        }
        console.println("Loading database from:'" + args[0] + "'" );
        return args[0].trim();
    }
}