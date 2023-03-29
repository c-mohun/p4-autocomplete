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
            String term = terms[i];
            double weight = weights[i];
            Term newTerm = new Term(term, weight);
            for (int prefixLength = 1; prefixLength <= term.length(); prefixLength++) {
                String prefix = term.substring(0, prefixLength);
                List<Term> termList = myMap.get(prefix);
                if (termList == null) {
                    termList = new ArrayList<>();
                    myMap.put(prefix, termList);
                }

                termList.add(newTerm);
            }
        }

        for (List<Term> termList :myMap.values()) {
            termList.sort(Comparator.comparing(Term::getWeight).reversed());
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