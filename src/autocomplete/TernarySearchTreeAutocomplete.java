package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        for (CharSequence prefix : terms) {
            if (prefix.length() > 0) {
                overallRoot = addAllHelper(prefix, overallRoot, 0);
            }
        }
    }

    private Node addAllHelper(CharSequence terms, Node overallRoot, int counter) {
        char curr = terms.charAt(counter);
        if (overallRoot == null) {
            overallRoot = new Node(curr);
        }
        if (curr < overallRoot.data) {
            overallRoot.left = addAllHelper(terms, overallRoot.left, counter);
        } else if (curr > overallRoot.data) {
            overallRoot.right = addAllHelper(terms, overallRoot.right, counter);
        } else if (counter < terms.length() - 1) {
            overallRoot.mid = addAllHelper(terms, overallRoot.mid, counter + 1);
        } else if (counter == terms.length() - 1) {
            overallRoot.isTerm = true;
        }
        return overallRoot;
    }

    private Node get(CharSequence prefix, Node overallRoot, int counter) {
        if (overallRoot == null) {
            return null;
        }
        char curr = prefix.charAt(counter);
        if (curr < overallRoot.data) {
            return get(prefix, overallRoot.left, counter);
        } else if (curr > overallRoot.data) {
            return get(prefix, overallRoot.right, counter);
        } else if (counter < prefix.length() - 1) {
            return get(prefix, overallRoot.mid, counter + 1);
        } else {
            return overallRoot;
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        List<CharSequence> matches = new ArrayList<>();
        if (prefix == null || prefix.isEmpty()) {
            return matches;
        }
        Node prefixNode = get(prefix, overallRoot, 0);
        if (prefixNode == null) {
            return matches;
        } else if (prefixNode.isTerm) {
            matches.add(prefix);
        }
        collect(matches, prefixNode.mid, prefix.toString());
        return matches;
    }

    private void collect(List<CharSequence> result, Node overallRoot, String prefix) {
        if (overallRoot == null) {
            return;
        }
        collect(result, overallRoot.left, prefix);
        if (overallRoot.isTerm) {
            result.add(prefix + overallRoot.data);
        }
        collect(result, overallRoot.mid, prefix + overallRoot.data);
        collect(result, overallRoot.right, prefix);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
