import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class CS1003P1 {

    public static void main(String[] args) throws Exception {
        
        CLI cli = new CLI(args);

        if (cli.checkArgsValid() == false) {
            throw new Exception("Insufficient arguments found");
        }

        Bigrams bigrams = new Bigrams();
        // System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("Chips"), bigrams.createBigrams("Crips")));

        StringTokenizer st = new StringTokenizer(cli.getText(), " ");
        while (st.hasMoreTokens()) {
            String currToken = st.nextToken();
            if(isValidWord(cli.getAlphaPath(), currToken)) {
                System.out.print(currToken + " ");
            }
            else {
                System.out.print(findClosestMatch(cli.getAlphaPath(), currToken) + " ");
            }
        }
        System.out.println();
        
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
