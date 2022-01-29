import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CLI {

    private String[] args;
    private String alphaPath;
    private boolean alternatePath;
    private boolean help;
    private boolean verbose;

    //constructor
    public CLI(String[] args) throws Exception {
        this.args = args;
        alphaPath = "../words_alpha.txt";
        alternatePath = false;
        verbose = false;
        help = false;

        checkFlags();
    }

    //checks if provided arguments are valid
    public boolean checkArgsValid() {
        for (int i = 0; i < args.length - 1; i++) {
            if (!(args[i].equals("-f") || args[i].equals("--file") || args[i].equals("-n") 
                || args[i].equals("-t") || args[i].equals("--test") || args[i].equals("-h") 
                || args[i].equals("--help") || args[i].equals("-v") || args[i].equals("--verbose"))) {
                    return false;
                }
        }

        if (args.length == 0) 
            return false;
        else
            return true;
    }

    //returns path of words file
    public String getAlphaPath() {
        return alphaPath;
    }

    public boolean getHelp() {
        return help;
    }

    //if flags sent to program, checks and responds appropriately
    public void checkFlags() throws Exception {
        for(int i = 0; i < args.length; i++) {
            //flag for changing file path
            if (args[i].equals("-f") || args[i].equals("--file")) {
                if (isValidPath(args[i+1])) 
                    setAlternatePath(args[i+1]);
                else 
                    throw new Exception("Invalid file path specified");
                i++;
            }

            //sets n-gram value, used in P1Ex
            if (args[i].equals("-n")) {
                throw new Exception("Invalid command in version main, please try in P1Ex");
            }

            //runs created test cases from Scenarios
            if (args[i].equals("-t") || args[i].equals("--test")) {
                Scenarios scenarios = new Scenarios();
                scenarios.runTest();
            }

            //outputs help menu
            if (args[i].equals("-h") || args[i].equals("--help")) {
                help = true;
            }
            
            //sets verbose logging
            if (args[i].equals("-v") || args[i].equals("--verbose")) {
                verbose = true;
            }
        }
    }

    //outputs provided string is verbose logging is enabled
    public void verboseLog(String s) {
        if (verbose) {
            System.out.println(s);
        }
    }

    //prints help menu from help file
    public void printHelp() throws IOException {
        Path path = Paths.get("../files/help.txt");
        List<String> out = Files.readAllLines(path);
        for(String s: out) {
            System.out.println(s);
        }
    }

    //gets string sent with program execution
    public String getText() throws Exception{
        if (checkArgsValid() == false) {
            throw new Exception("Insufficient arguments provided");
        }

        return args[args.length-1].toLowerCase();
    }

    //checks if provided file path is valid
    public boolean isValidPath(String inp) {
        Path path = Paths.get(inp);
        return Files.exists(path);
    }

    //sets alternate file path to path specified
    private void setAlternatePath(String newPath) {
        alternatePath = true;
        alphaPath = newPath;
    }

}
