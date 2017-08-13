package algorithm.heap;

import java.util.Arrays;

public class BinaryHeap<K extends Comparable<K>, T> {
    protected static final int defaultCapacity = 64;
    private final int direction;
    private T[] data;
    private K[] keys;
    private int capacity;
    private int size;

    protected BinaryHeap(int capacity, int direction) {
        this.direction = direction;
        this.data = (T[]) new Object[capacity];
        this.keys = (K[]) new Object[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public void offer(K key, T value) {
        // If move room is needed, double array size
        if (size >= capacity) {
            capacity *= 2;
            data = Arrays.copyOf(data, capacity);
            keys = Arrays.copyOf(keys, capacity);
        }

        // Insert new value at the end
        data[size] = value;
        keys[size] = key;
        siftUp(size);
        size++;
    }

    protected void removeTip() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        size--;
        data[0] = data[size];
        keys[0] = keys[size];
        data[size] = null;
        siftDown(0);
    }

    protected void replaceTip(K key, T value) {
        if (size == 0) {
            throw new IllegalStateException();
        }

        data[0] = value;
        keys[0] = key;
        siftDown(0);
    }

    @SuppressWarnings("unchecked")
    protected T getTip() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        return (T) data[0];
    }

    protected K getTipKey() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        return keys[0];
    }

    private void siftUp(int c) {

        for (int p = (c - 1) / 2; c != 0 && keys[c].compareTo(keys[p])*direction > 0; c = p, p = (c - 1) / 2) {
            T pData = data[p];
            K pDist = keys[p];
            data[p] = data[c];
            keys[p] = keys[c];
            data[c] = pData;
            keys[c] = pDist;
        }
    }

    private void siftDown(int p) {
        for (int c = p * 2 + 1; c < size; p = c, c = p * 2 + 1) {
            if (c + 1 < size && keys[c].compareTo(keys[c+1])*direction > 0) {
                c++;
            }
            if (keys[c].compareTo(keys[p])*direction > 0) {
                // Swap the points
                T pData = data[p];
                K pDist = keys[p];
                data[p] = data[c];
                keys[p] = keys[c];
                data[c] = pData;
                keys[c] = pDist;
            } else {
                break;
            }
        }
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public static final class Max<K extends Comparable<K>, T> extends BinaryHeap<K, T> implements MaxHeap<K, T> {
        public Max() {
            super(defaultCapacity, 1);
        }
        public Max(int capacity) {
            super(capacity, 1);
        }
        public void removeMax() {
            removeTip();
        }
        public void replaceMax(K key, T value) {
            replaceTip(key, value);
        }
        public T getMax() {
            return getTip();
        }
        public K getMaxKey() {
            return getTipKey();
        }
    }
    public static final class Min<K extends Comparable<K>, T> extends BinaryHeap<K, T> implements MinHeap<K, T> {
        public Min() {
            super(defaultCapacity, -1);
        }
        public Min(int capacity) {
            super(capacity, -1);
        }
        public void removeMin() {
            removeTip();
        }
        public void replaceMin(K key, T value) {
            replaceTip(key, value);
        }
        public T getMin() {
            return getTip();
        }
        public K getMinKey() {
            return getTipKey();
        }
    }
}
