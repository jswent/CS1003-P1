import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Bigrams {

    //creats Set of bigrams
    public Set<String> createBigrams(String s) {
        s = s.toLowerCase();    //translates provided string to lowercase for string comparison
        Set<String> set = new LinkedHashSet<>();
        s = s.replaceAll("", " ");  //puts spaces between all characters to be used with StringTokenizer
        StringTokenizer st = new StringTokenizer(s, " ");

        String s1 = st.nextToken();
        String s2 = "";

        set.add("^" + s1);  //adds top character

        while (st.hasMoreTokens()) {
            s2 = st.nextToken();
            set.add(s1 + s2);
            s1 = s2;
        }

        set.add(s1 + "$");  //adds tail character
        return set;
    }

    //calculates dice coefficient between two Sets
    public double diceCoefficient(Set<String> set1, Set<String> set2) {
        Set<String> intersect = new HashSet<>(set1);
        intersect.retainAll(set2);
        double total = intersect.size();

        return (2 * total) / (set1.size() + set2.size());
    }

}
