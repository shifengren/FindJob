package algorithm.heap;

public interface MinHeap<K extends Comparable<K>, T> {
    // supported operations
    int size();
    void offer(K key, T val);
    void replaceMin(K key, T val);
    void removeMin();
    T getMin();
    K getMinKey();
}
