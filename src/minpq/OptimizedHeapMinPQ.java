package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public OptimizedHeapMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size() + 1);
        elementsToIndex = new HashMap<>(elementsAndPriorities.size());
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            E element = entry.getKey();
            double priority = entry.getValue();
            elements.add(new PriorityNode<>(element, priority));
            elementsToIndex.put(element, elements.size());
        }
        for (int i = elements.size() / 2; i >= 1; i--) {
            sink(i);
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        elements.add(new PriorityNode<>(element, priority));
        elementsToIndex.put(element, elements.size() - 1);
        swim(elements.size() - 1);
    }

    @Override
    public boolean contains(E element) {
        return elementsToIndex.containsKey(element);
    }

    @Override
    public double getPriority(E element) {
        return elementsToIndex.get(element);
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return elements.get(0).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        E min = peekMin();
        swap(0, elements.size() - 1);
        elements.remove(elements.size() - 1);
        elementsToIndex.remove(min);
        if (!isEmpty()) {
            sink(0);
        }
        return min;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        int index = elementsToIndex.get(element);
        double oldPriority = elements.get(index).getPriority();
        elements.set(index, new PriorityNode<>(element, priority));
        if (priority < oldPriority) {
            swim(index);
        } else {
            sink(index);
        }
    }

    @Override
    public int size() {
        return elements.size();
    }

    private void swim(int i) {
        while (i > 0 && greater((i - 1) / 2, i)) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void sink(int i) {
        int min = i;
        if (2 * i + 1 < elements.size() && greater(min, 2 * i + 1)) {
            min = 2 * i + 1;
        }
        if (2 * i + 2 < elements.size() && greater(min, 2 * i + 2)) {
            min = 2 * i + 2;
        }
        if (min != i) {
            swap(i, min);
            sink(min);
        }
    }

    private boolean greater(int i, int j) {
        return elements.get(i).getPriority() > elements.get(j).getPriority();
    }

    private void swap(int i, int j) {
        PriorityNode<E> temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
        elementsToIndex.put(elements.get(i).getElement(), i);
        elementsToIndex.put(elements.get(j).getElement(), j);
    }
}