import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NgramGenerator {
    
    private int n;

    public NgramGenerator(int n) {
        this.n = n;
    }

    public NgramGenerator() {
        this.n = 2;
    }

    public Set<String> createNgrams(String s) {
        s = s.toLowerCase();

        List<String> ngrams = new ArrayList<>();
        if (s.length() < n && n == 2) {
            ngrams.add(s);
        }
        else if (s.length() < n) {
            return new HashSet<>();
        }

        for (int i = 0; i < s.length() - n + 1; i++) {
            ngrams.add(s.substring(i, i + n));
        }

        String lastElement = ngrams.get(ngrams.size()-1);

        if (n == 1) {
            ngrams.set(0, "^" + ngrams.get(0));
            ngrams.set(ngrams.size()-1, lastElement + "$");
        }
        else {
            ngrams.add(0, "^" + ngrams.get(0).substring(0, n-1));
            ngrams.add(ngrams.size(), lastElement.substring(lastElement.length() - n + 1) + "$");
        }

        return new HashSet<>(ngrams);
    }

    public double diceCoefficient(Set<String> set1, Set<String> set2) {
        Set<String> intersect = new HashSet<>(set1);
        intersect.retainAll(set2);

        double total = intersect.size();

        return (2 * total) / (set1.size() + set2.size());
    }

}