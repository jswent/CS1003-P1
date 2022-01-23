import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CLI {

    private String[] args;
    private String alphaPath;
    private boolean alternatePath;

    public CLI(String[] args) {
        this.args = args;
        alphaPath = "../words_alpha.txt";
        alternatePath = false;
    }

    //needs additional checks
    public boolean checkArgsValid() {
        if (args.length == 0) 
            return false;
        else
            return true;
    }

    public String getAlphaPath() throws Exception {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-f")) {
                if (isValidPath(args[i+1])) {
                    alternatePath = true;
                    alphaPath = args[i+1];
                }
                else {
                    throw new Exception("Invalid file path specified");
                }
            }
        }
        return alphaPath;
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

}
