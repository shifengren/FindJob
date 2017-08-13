package algorithm.heap;

public interface MaxHeap<K extends Comparable<K>, T> {
    // supported operations
    int size();
    void offer(K key, T val);
    void replaceMax(K key, T val);
    void removeMax();
    T getMax();
    K getMaxKey();
}
