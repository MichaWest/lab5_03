package commands;
/**
 * CommandWrapper class for command parsing
 */
public class CommandWrapper {
    private final String arg;
    private final String com;

     public CommandWrapper(String c, String a){
         this.arg =a;
         this.com = c;
     }

     public CommandWrapper(String c){
         this.com =c;
         this.arg = null;
     }
    /**
     * method for get argument of command
     */
     public String getArg(){
         return arg;
     }
    /**
     * method for get command
     */
     public String getCom(){
         return com;
     }
}
