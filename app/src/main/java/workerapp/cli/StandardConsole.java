package workerapp.cli;


public  class StandardConsole implements Console  {

    
    private final String PS1 = "$ "; 
    private final String PS2 = "> ";  



    @Override
    public  void print(Object object) {
        System.out.print(object);
    } 



    @Override
    public void println(Object object) {
        System.out.println(object);
        System.out.flush();
    }



    @Override
    public void printTable(Object elementleft, Object elementRight) {
        System.out.printf(" %-20s%-1s%n", elementleft, elementRight);
    }


    @Override
    public void printError(Object message) {
    System.out.println("\u001B[31mError: " + message + "\u001B[0m"); 
}



    @Override
    public void ps2() {
        print(PS2);
    }



    public void ps1() {
        print(PS1);
    } 







}
