package algorithm.stack;

import java.util.Arrays;

/**
 * 定义栈的数据结构，要求添加一个min函数，能够得到栈的最小元素。
 * 要求函数min、push以及pop的时间复杂度都是O(1)。
 * <p>
 * 解法: 用一个栈(minDatas)保存当前栈中的最小元素
 */
public class StackWithMin {
    static final int DEFAULT_SIZE = 100;
    int[] dataContainer = null;
    int[] minDatas = null;

    int size = 0;
    int capacity = 0;
    int minSize = 0;

    public StackWithMin(int capacity) {
        this.capacity = capacity;
        dataContainer = new int[capacity];
        minDatas = new int[capacity];
    }

    public StackWithMin() {
        this(DEFAULT_SIZE);
    }

    /**
     * Return the minimal element in stack
     *
     * @return minimal element
     */
    public int min() {
        if(minSize>=1)
            return minDatas[minSize-1];
        else return -1;
    }

    /**
     *
     */
    public void push(int ele) {
        if (size >= this.capacity) {
            capacity = capacity + (int) (capacity / 4.0);
            dataContainer = Arrays.copyOf(dataContainer, capacity);
            minDatas = Arrays.copyOf(minDatas, capacity);
        }

        dataContainer[size++] = ele;

        if (size != 1) {
            if (minDatas[minSize - 1] >= ele) {
                minDatas[minSize++] = ele;
            }
        } else {
            minDatas[minSize++] = ele;
        }
    }

    /**
     *
     */
    public int pop() {
        int top = dataContainer[--size];
        if (top <= minDatas[minSize-1]) {
            --minSize;
        }
        return top;
    }

    public void show() {
        System.out.println("stack:");
        for (int i = 0; i < size; ++i) {
            System.out.print(dataContainer[i] + " ");
        }
        System.out.println("\nmin stack:");
        for (int i = 0; i < minSize; ++i) {
            System.out.print(minDatas[i] + " ");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        int[] ary = {7, 9, 6, 5, 8, 4};

        StackWithMin smin = new StackWithMin();
        for (int el : ary) {
            smin.push(el);
//            System.out.println("///////////////////////////////////////////////////");
//            smin.show();
        }
        System.out.println("pop");
        System.out.println("Size:" + smin.size);
        System.out.println("minSize:" + smin.minSize);

        smin.show();
        int len = smin.size;
        for(int i=0; i<len; i++){

            System.out.println("MIn:"+smin.min() );
            System.out.println("TOP:"+smin.pop() );

            smin.show();
            System.out.println("///////////////////////////////////////////////////");
        }
    }

}
