package algorithm.tree;

/**
 * 树状数组，或者二分索引树
 * 详细解释
 * http://blog.csdn.net/rylq56m9/article/details/51537068
 *
 * 支持两种操作
 * 1. add(i, 1): 对第i个元素的值自增1, 时间复杂度 O(log(n))
 * 2. sum(i): 统计[1....i]的元素的和 时间复杂度 O(log(n))
 *
 * 使用这两种操作设计下面三种操作:
 * 1. 插入(Insert)，对应的是 add(i, 1)，时间复杂度O( logn )
 * 2. 删除(Delete), 对应的是 add(i, -1), 时间复杂度O( logn )
 * 3. 询问(Query), 由于sum( i )能够统计[1...i]元素值的和
 *
 */
public class BinaryIndexTree {

    private int[] C = null;
    public BinaryIndexTree(int size){
        C = new int[size];
    }

    // O(log(n))
    public void add(int i, int v){
        for(int j=i; j<=C.length; j += lowbit(j)){
            C[j] += v;
        }
    }
    // O(log(n))
    public int sum(int i){ // sum (A[1...i])
        int s = 0;
        for(int j=i; j>=0; j -= lowbit(j))
            s += C[j];
        return s;
    }
    private int lowbit(int i){
        return i & (-i);
    }

    // point update, interval query
    /**
     * 一个长度为n(n <= 500000)的元素序列，一开始都为0，现给出三种操作：
     * 1. add x v :    给第x个元素的值加上v；     (  a[x] += v )
       2. sub x v :    给第x个元素的值减去v；     (  a[x] -= v )
       3. sum x y：   询问第x到第y个元素的和；  ( print sum{ a[i] | x <= i <= y } ) <=> sum(y) - sum(x-1)
     */

    // Interval update, point query
    /**一个长度为n(n <= 500000)的元素序列，一开始都为0，现给出两种操作：
     * 1. add x y v :    给第x个元素到第y个元素的值都加上v；(  a[i] += v, 其中 x <= i <= y ) <=> add(x, v) 和 add(y+1, -v) ?
       2. get x：        询问第x个元素的值；   (  print  a[x] <=> (sum(x)-sum(x-1)) )
     */


    // 逆序问题
    /**
     * 给定一个长度为n(n <= 500000)的排列a[i]，求它的逆序对对数。1 5 2 4 3 的逆序对为(5,2)(5,3)(5,4)(4,3)，所以答案为4
     * 来看一个给定n个元素的排列 X0 X1 X2 … Xn-2 Xn-1,对于某个 Xi 元素，
     * 如果想知道以它为"首"的逆序对的对数( 形如(Xi Xj) 的逆序对)，
     * 就是需要知道 Xi+1 … Xn-2 Xn-1 这个子序列中小于 Xi 的元素的个数。
     * 那么我们只需要对这个排列从后往前枚举，
     * 每次枚举到 Xi 元素时，执行cnt += sum(Xi-1)，
     * 然后再执行add(Xi, 1)，n个元素枚举完毕，得到的cnt值就是我们要求的逆序数了。总的时间复杂度O(nlogn)。
     *
     * 这个模型和之前的区别在于它不是将原数组的下标作为树状数组的下标，
     * 而是`将元素本身作为树状数组的下标`。逆序模型作为树状数组的一个经典思想有着非常广泛的应用。
     */
}
