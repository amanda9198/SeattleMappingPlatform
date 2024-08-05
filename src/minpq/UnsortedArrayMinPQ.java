package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public UnsortedArrayMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size());
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        elements.add(new PriorityNode<>(element, priority));
    }

    @Override
    public boolean contains(E element) {
        return elements.contains(new PriorityNode<>(element, 0));
    }

    @Override
    public double getPriority(E element) {
        for (PriorityNode<E> priority : elements) {
            if (priority.getElement().equals(element)) {
                return priority.getPriority();
            }
        }
        return 0;
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // iterates and finds the min value
        PriorityNode<E> min_val = elements.get(0);
        for (int i = 0; i < elements.size(); i++) {
            if (min_val.getPriority() > elements.get(i).getPriority()) {
                min_val = elements.get(i);
            }
        }
        return min_val.getElement();
    }

    private int indexOf(E element) {
        return elements.indexOf(new PriorityNode<>(element, 0));
    }
    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return elements.remove(indexOf(peekMin())).getElement();
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        elements.get(indexOf(element)).setPriority(priority);
    }

    @Override
    public int size() {
        return elements.size();
    }
}
