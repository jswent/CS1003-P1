import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class CS1003P1 {

    public static void main(String[] args) throws Exception {
        String out = "";
        CLI cli = new CLI(args);

        if (cli.checkArgsValid() == false) {
            cli.verboseLog("Checking validity of provided arguments");
            throw new Exception("Insufficient arguments found");
        }

        if (cli.getHelp()) {
            cli.verboseLog("Checking if help menu should be output");
            cli.printHelp();
        }
        else {
            StringTokenizer st = new StringTokenizer(cli.getText(), " ");
            cli.verboseLog("Entering tokens loop of provided string");
            while (st.hasMoreTokens()) {
                String currToken = st.nextToken();
                if(isValidWord(cli.getAlphaPath(), currToken)) {
                    cli.verboseLog(currToken + " is valid word, adding to output");
                    out += currToken + " ";
                }
                else {
                    cli.verboseLog(currToken + " is invalid word, finding closest match in file");
                    out += findClosestMatch(cli.getAlphaPath(), currToken) + " ";
                }
            }

            cli.verboseLog("printing final output to sdout");
            System.out.println(out);

        }
        
    }

    public static boolean isValidWord(String alphaPath, String word) throws IOException {
        Path path = Paths.get(alphaPath);
        boolean isValid = false;

        List<String> lines = Files.readAllLines(path);
        lines.stream().map(String::toLowerCase);

        for(String s : lines) {
            if(s.equals(word)) {
                isValid = true;
            }
        }

        return isValid;
    }

    public static String findClosestMatch(String alphaPath, String word) throws IOException{
        Path path = Paths.get(alphaPath);
        Bigrams bigrams = new Bigrams();
        
        List<String> lines = Files.readAllLines(path);
        lines.stream().map(String::toLowerCase);

        double highestCoefficient = 0.0; 
        String corrWord = "";   //corresponding word to highest Sorenson Dice Coefficient

        for (String s : lines) {
            Set<String> set1 = bigrams.createBigrams(word);
            Set<String> set2 = bigrams.createBigrams(s);

            double currDice = bigrams.diceCoefficient(set1, set2);
            if (currDice > highestCoefficient) {
                highestCoefficient = currDice;
                corrWord = s;
            }
        }

        return corrWord;
    }

    
}
