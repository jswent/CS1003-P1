import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Scenarios {

    private CLI cli;
    private int n;

    public void runTest() throws Exception {
        testDice();
        testCorrections();
        scenario1();
        scenario2();
        scenario3();
        scenario4();
        scenario5();
        scenario6();
        
    }

    public void testDice() {
        Bigrams bigrams = new Bigrams();

        System.out.print("Cheese, Cheese: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("Cheese"), bigrams.createBigrams("Cheese")));

        System.out.print("Ozgur Akgun, Alan Dearle: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("Ozgur Akgun"), bigrams.createBigrams("Alan Dearle")));

        System.out.print("Bananas, Banana: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("Bananas"), bigrams.createBigrams("Banana")));

        System.out.print("Chips, Crisps: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("Chips"), bigrams.createBigrams("Crisps")));

        System.out.print("Chocolatey, Chocolate: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("Chocolatey"), bigrams.createBigrams("Chocolate")));

        System.out.print("stravberry, strawberry: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("stravberry"), bigrams.createBigrams("strawberry")));

        System.out.print("speled, spelled: ");
        System.out.println(bigrams.diceCoefficient(bigrams.createBigrams("speled"), bigrams.createBigrams("spelled")));
    }

    public void testCorrections() throws Exception {
        System.out.print("one word that is not stravberry: ");
        System.out.println(testSentence("one word that is not stravberry"));

        System.out.print("two pluus thre makes five: ");
        System.out.println(testSentence("two pluss thre makes five"));

        System.out.print("not all work as welll as expectd: ");
        System.out.println(testSentence("not all work as welll as expectd"));

        System.out.print("a seng of soilence: ");
        System.out.println(testSentence("a seng of soilence"));
    }

    public void scenario1() {
        Bigrams bigrams = new Bigrams();

        String s1 = "test";
        String s2 = "tests";

        Set<String> set1 = bigrams.createBigrams(s1);
        Set<String> set2 = bigrams.createBigrams(s2);

        System.out.println(s1 + ": " + set1);
        System.out.println(s2 + ": " + set2);

        System.out.println("Dice coefficient: " + bigrams.diceCoefficient(set1, set2));
        System.out.println();
    }

    public void scenario2() {
        Bigrams bigrams = new Bigrams();

        String s1 = "bomb";
        String s2 = "bombing";

        Set<String> set1 = bigrams.createBigrams(s1);
        Set<String> set2 = bigrams.createBigrams(s2);

        System.out.println(s1 + ": " + set1);
        System.out.println(s2 + ": " + set2);

        System.out.println("Dice coefficient: " + bigrams.diceCoefficient(set1, set2));
        System.out.println();
    }

    public void scenario3() {
        Bigrams bigrams = new Bigrams();

        String s1 = "perfect";
        String s2 = "perfectly";

        Set<String> set1 = bigrams.createBigrams(s1);
        Set<String> set2 = bigrams.createBigrams(s2);

        System.out.println(s1 + ": " + set1);
        System.out.println(s2 + ": " + set2);

        System.out.println("Dice coefficient: " + bigrams.diceCoefficient(set1, set2));
        System.out.println();
    }

    public void scenario4() {
        Bigrams bigrams = new Bigrams();

        String s1 = "yes";
        String s2 = "no";

        Set<String> set1 = bigrams.createBigrams(s1);
        Set<String> set2 = bigrams.createBigrams(s2);

        System.out.println(s1 + ": " + set1);
        System.out.println(s2 + ": " + set2);

        System.out.println("Dice coefficient: " + bigrams.diceCoefficient(set1, set2));
        System.out.println();
    }

    public void scenario5() {
        Bigrams bigrams = new Bigrams();

        String s1 = "exact";
        String s2 = "exact";

        Set<String> set1 = bigrams.createBigrams(s1);
        Set<String> set2 = bigrams.createBigrams(s2);

        System.out.println(s1 + ": " + set1);
        System.out.println(s2 + ": " + set2);

        System.out.println("Dice coefficient: " + bigrams.diceCoefficient(set1, set2));
        System.out.println();
    }

    public void scenario6() throws Exception {
        System.out.println("i love computr scence: " + testSentence("i love computr scence"));
        System.out.println("sint andrews: " + testSentence("sint andrews"));
        System.out.println("prctcal oen: " + testSentence("prctcal oen"));
        System.out.println("sme of thse dnt wrk: " + testSentence("sme of thse dnt wrk"));
    }

    private String testSentence(String s) throws Exception {
        String[] args = {s};
        cli = new CLI(args);
        String out = "";
            
            StringTokenizer st = new StringTokenizer(cli.getText(), " ");
            while (st.hasMoreTokens()) {
                String currToken = st.nextToken();
                if(isValidWord(cli.getAlphaPath(), currToken)) {
                    out += currToken + " ";
                }
                else {
                    out += findClosestMatch(cli.getAlphaPath(), currToken) + " ";
                }
            }

        return out;
    }

    private static boolean isValidWord(String alphaPath, String word) throws IOException {
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

    private static String findClosestMatch(String alphaPath, String word) throws IOException{
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