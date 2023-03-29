import java.util.*;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }
        initialize(terms, weights);
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        for (int i = 0; i < terms.length; i++) {
            String curr = terms[i];
            double weight = weights[i];
            Term term = new Term(curr, weight);
            int maxIndex = Math.min(curr.length(), MAX_PREFIX);
            for (int z = 0; z <= maxIndex; z++) {
                String substring = curr.substring(0, z);
                myMap.putIfAbsent(substring, new ArrayList<>());
                myMap.get(substring).add(term);
            }
        }

        for (String curr1:myMap.keySet()) {
            Collections.sort(myMap.get(curr1), (Comparator.comparing(Term::getWeight).reversed()));
        }
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (myMap.containsKey(prefix) == false) { // doesn't contain
            return new ArrayList<>();
        }
        if (prefix.length() > MAX_PREFIX) { // length
            prefix = prefix.substring(0, MAX_PREFIX);
        }
        List<Term> all = myMap.get(prefix);
        return myMap.get(prefix).subList(0, Math.min(k, all.size()));
    }

    @Override
    public int sizeInBytes() {
        mySize = 0;
        HashSet<Term> set1 = new HashSet<>();
        for (String string1 : myMap.keySet()) {
            mySize = mySize + BYTES_PER_CHAR * string1.length();
            set1.addAll(myMap.get(string1));
        }
        for (Term term : set1) {
            mySize = mySize + BYTES_PER_CHAR * term.getWord().length() + BYTES_PER_DOUBLE;
        }
        return mySize;
    }
}