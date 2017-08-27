package algorithm.tree.BinaryIndexTree;

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

    // interval update, interval query
    // 定义一个数组: delta[i] 表示从 i ~ n的数字都增加了delta[i]
    // 更新区间[a, b]:
    //  delta[a] += d,  delta[b + 1] -= d

    // 查询区间[a, b]的和: sum(b) – sum(a – 1)
    // 我们求1 ~ x时，delta[1]对他的贡献是x*delta[1]，因为delta[1]是表示从1到n都增加delta[1]
    // 所以sum(x) ＝ a[1] + a[2] + … + a[x] + delta[1] * x + delta[2] * (x – 1) + delta[3] * (x – 2) + … + delta[x] ＝ segma(a[i]) + segma(delta[i] * (x – i + 1))
    // （1 <= i <= x）＝ segma(a[i]) + segma(delta[i]) * (x + 1) – segma(delta[i] * i)
    // 这样的话，就是三个前缀和累加和了
    // 令c1表示delta[i]，c2表示delta[i] * i
    // sum(x) = sigma(a[i]) + sigma(c[i])*(x+1) + sigma(c2[i])
    // 因为delta[a] += d, delta[b + 1] -= d，
    // 所以我们只需要将 c1[a] += d, c1[b + 1] -= d，c2[a] += d * a, c2[b + 1] -= d * (b + 1)


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

    // 二分模型就是在求和的过程中，利用求和函数的单调性进行二分枚举
    /**
     *例题5】给定N(N <= 100000)个编号为1-N的球，将它们乱序丢入一个“神奇的容器”中，作者会在丢的同时询问其中编号第K大的那个球，
     * “神奇的容器”都能够从容作答，并且将那个球给吐出来，然后下次又可以继续往里丟。
     现在要你来模拟这个神奇的容器的功能。可以抽象成两种操作：
     1. put x                 向容器中放入一个编号为x的球；
     2. query K             询问容器中第K大的那个球，并且将那个球从容器中去除（保证K<容器中球的总数）；

     于操作1，只是单纯地执行add(x, 1)即可
     对于操作2，我们要看第K大的数满足什么性质，由于这里的数字不会有重复，所以一个显而易见的性质就是一定有K-1个数大于它，假设它的值为x，
     那么必然满足下面的等式：sum(N) - sum( x ) == K-1，然而，满足这个等式的x可能并不止一个.
     我们需要再加一个限制条件，即满足上面等式的最小的x。于是我们二分枚举x，当满足sum(N) - sum( x ) <= K-1时，将右区间缩小（说明找的数x偏大，继续往小的找），
     否则左区间增大(说明找的数x偏小，继续往大的找)，直到找到满足条件的最小的x为止。单次操作的时间复杂度为O( logn * logn )。
     */

    /**多维度数状数组
     * 【例题6】给定一个N*N(N <= 1000)的矩形区域，执行两种操作：
     1. add x y v                                     在(x, y)加上一个值v；
     2. sum x1 y1 x2 y2                          统计矩形(x1, y1) - (x2, y2)中的值的和；

     PUIQ模型的二维版本。我们设计两种基本操作：
     1. add(x, y, v)        在(x, y)这个格子加上一个值v；
     2. sum(x, y)           求矩形区域(1, 1) - (x, y)内的值的和，
     那么(x1,y1)-(x2,y2)区域内的和可以通过四个求和操作获得，
     即 sum(x2, y2) - sum(x2, y1 - 1) - sum(x1 - 1, y2) + sum(x1 - 1, y1 - 1)。

     void add(int x,int y,int v){
         for(int i = x; i <= n; i += lowbit(i)){
             for(int j = y; j <= n; j += lowbit(j)){
             c[i][j]+= v;
         }
        }
     }

     int sum(int x,int y){
         int s =0;
         for(int i = x; i ; i -= lowbit(i)){
             for(int j = y; j ; j -= lowbit(j)){
             s += c[i][j];
            }
         }
        return s;
     }
     *
     */


}
