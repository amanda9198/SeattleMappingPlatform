package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        // terms are added to an ArrayList in any order
        elements.addAll(terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        List<CharSequence> matches = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return matches;
        }
        for (CharSequence word : elements) {
            if (Autocomplete.isPrefixOf(prefix, word)) {
                matches.add(word);
            }
        }
        return matches;
    }
}
