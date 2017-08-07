package algorithm.stack;

import java.util.Arrays;

/**
 * 定义栈的数据结构，要求添加一个min函数，能够得到栈的最小元素。
 * 要求函数min、push以及pop的时间复杂度都是O(1)。
 *
 * 解法: 用一个栈(minDatas)保存当前栈中的最小元素
 */
public class StackWithMin {
    static final int DEFAULT_SIZE=100;
    int[] dataContainer = null;
    int[] minDatas = null;
    int size = 0;
    int capacity = 0;
    int minSize = 0;

    public StackWithMin(int capacity){
        this.capacity = capacity;
        dataContainer = new int[capacity];
        minDatas = new int[capacity];
    }
    public StackWithMin(){
        this(DEFAULT_SIZE);
    }

    /**
     * Return the minimal element in stack
     * @return minimal element
     */
    public int min(){
        return minDatas[minSize-1];
    }

    /**
     *
     */
    public void push(int ele){
        if(size+1 >= this.capacity){
            capacity = capacity + (int)(capacity / 4.0);
            dataContainer = Arrays.copyOf(dataContainer, capacity);
            minDatas = Arrays.copyOf(minDatas, capacity);
        }
        dataContainer[size++]=ele;
        if(size != 1){
            if(minDatas[minSize-1] >= ele){
                minDatas[minSize++] = ele;
            }
        }else{
            minDatas[minSize++]=ele;
        }
    }

    /**
     *
     */
    public int pop(){
        int top = dataContainer[--size];
        if(top <= minDatas[minSize-1]){
            --minSize;
        }
        return top;
    }


}
