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
    private int n;

    public CLI(String[] args) throws Exception {
        this.args = args;
        alphaPath = "../words_alpha.txt";
        alternatePath = false;
        verbose = false;
        help = false;
        n = 2;

        checkFlags();
    }

    //needs additional checks
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

    public String getAlphaPath() {
        return alphaPath;
    }

    public int getN() {
        return n;
    }

    public boolean getHelp() {
        return help;
    }

    public void checkFlags() throws Exception {
        for(int i = 0; i < args.length; i++) {
            if (args[i].equals("-f") || args[i].equals("--file")) {
                if (isValidPath(args[i+1])) 
                    setAlternatePath(args[i+1]);
                else 
                    throw new Exception("Invalid file path specified");
                i++;
            }

            if (args[i].equals("-n")) {
                throw new Exception("Invalid command in version main, please try in P1Ex");
            }

            if (args[i].equals("-t") || args[i].equals("--test")) {
                Scenarios scenarios = new Scenarios();
                scenarios.runTest();
            }

            if (args[i].equals("-h") || args[i].equals("--help")) {
                help = true;
            }
            
            if (args[i].equals("-v") || args[i].equals("--verbose")) {
                verbose = true;
            }
        }
    }

    public void verboseLog(String s) {
        if (verbose) {
            System.out.println(s);
        }
    }

    public void printHelp() throws IOException {
        Path path = Paths.get("../files/help.txt");
        List<String> out = Files.readAllLines(path);
        for(String s: out) {
            System.out.println(s);
        }
    }

    public String getText() throws Exception{
        if (checkArgsValid() == false) {
            throw new Exception("Insufficient arguments provided");
        }

        return args[args.length-1].toLowerCase();
    }

    public boolean isValidPath(String inp) {
        Path path = Paths.get(inp);
        return Files.exists(path);
    }

    public boolean isValidN(String inp) throws NumberFormatException {
        int parsed = Integer.parseInt(inp);
        if(parsed > 0 && parsed < 4) {
            return true;
        }
        return false;
    }

    private void setAlternatePath(String newPath) {
        alternatePath = true;
        alphaPath = newPath;
    }

}
