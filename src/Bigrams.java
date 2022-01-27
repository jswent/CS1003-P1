import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Bigrams {

    public Set<String> createBigrams(String s) {
        s = s.toLowerCase();
        Set<String> set = new LinkedHashSet<>();
        s = s.replaceAll("", " ");
        StringTokenizer st = new StringTokenizer(s, " ");

        String s1 = st.nextToken();
        String s2 = "";

        set.add("^" + s1);

        while (st.hasMoreTokens()) {
            s2 = st.nextToken();
            set.add(s1 + s2);
            s1 = s2;
        }

        set.add(s1 + "$");
        //System.out.println(set);
        return set;
    }

    public double diceCoefficient(Set<String> set1, Set<String> set2) {
        Set<String> intersect = new HashSet<>(set1);
        intersect.retainAll(set2);
        //System.out.println(intersect.size());
        //System.out.println(set1.size() + set2.size());
        double total = intersect.size();

        return (2 * total) / (set1.size() + set2.size());
    }

}
