import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class CS1003P1 {

    public static void main(String[] args) throws Exception {
        String out = "";    //final output string, doesn't buffer output
        CLI cli = new CLI(args);    //instantiates new CLI object and sends provided args

        //checks if args sent to program are valid, exits program if not
        if (cli.checkArgsValid() == false) {
            cli.verboseLog("Checking validity of provided arguments");
            throw new Exception("Insufficient or invalid arguments found");
        }

        //checks if help menu is run
        if (cli.getHelp()) {
            cli.verboseLog("Checking if help menu should be output");
            cli.printHelp();
        }
        else {
            StringTokenizer st = new StringTokenizer(cli.getText(), " ");   //splits provded sentence in StringTokenizer
            cli.verboseLog("Entering tokens loop of provided string");
            while (st.hasMoreTokens()) {
                String currToken = st.nextToken();
                //checks if current word is valid in specified words file
                if(isValidWord(cli.getAlphaPath(), currToken)) {
                    cli.verboseLog(currToken + " is valid word, adding to output");
                    out += currToken + " "; //if word is valid, adds to output
                }
                else {
                    cli.verboseLog(currToken + " is invalid word, finding closest match in file");
                    //if word is invalid, finds closest match
                    out += findClosestMatch(cli.getAlphaPath(), currToken) + " ";
                }
            }

            cli.verboseLog("printing final output to sdout");
            System.out.println(out);    //prints output to stdout

        }
        
    }

    //checks if provided word is valid in specified file
    public static boolean isValidWord(String alphaPath, String word) throws IOException {
        Path path = Paths.get(alphaPath);
        boolean isValid = false;

        List<String> lines = Files.readAllLines(path);  //imports text file into list, inefficient
        lines.stream().map(String::toLowerCase);    //puts all words into lowercase, redundant

        //loops through lines list and checks if word is located
        for(String s : lines) {
            if(s.equals(word)) {
                isValid = true;
            }
        }

        return isValid;
    }

    //finds closest word in words file to provided string
    public static String findClosestMatch(String alphaPath, String word) throws IOException{
        Path path = Paths.get(alphaPath);
        Bigrams bigrams = new Bigrams();
        
        List<String> lines = Files.readAllLines(path);
        lines.stream().map(String::toLowerCase);

        double highestCoefficient = 0.0; 
        String corrWord = "";   //corresponding word to highest Sorenson Dice Coefficient

        //for each word in text file
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
