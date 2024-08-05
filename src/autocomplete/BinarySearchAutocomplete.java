package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        elements.addAll(terms);
        Collections.sort(elements, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        int start = Collections.binarySearch(elements, prefix, CharSequence::compare);
        List<CharSequence> matches = new ArrayList<>();
        if (start < 0) {
            start = -(start + 1);
        }
        for (int i = start; i < elements.size(); i++) {
            CharSequence wordPrefix = elements.get(i);
            if (Autocomplete.isPrefixOf(prefix, wordPrefix)) {
                matches.add(wordPrefix);
            }
        }
        return matches;
    }
}
