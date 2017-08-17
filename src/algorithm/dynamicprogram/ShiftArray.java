package algorithm.dynamicprogram;

/**
 * 循环右移数组，要求O(N)的时间复杂度
 */
public class ShiftArray {
    // k>0 循环右移k位
    // k<0 循环左移k位
    // 每个元素循环右移N位后，回到原来的位置
    // 因此，k > N时 循环右移k-N位等价于右移k位 => 循环右移k位等价于循环右移k%N位
    public static void cycleRightShift1(int[] ary, int k){
        k %= ary.length;
        while((k--) >= 0){
            int tmp = ary[ary.length-1];
            for(int i = ary.length-1; i>=1; --i)
                ary[i] = ary[i-1];
            ary[0] = tmp;
        }
    }

    /**
     * abcd1234 => 1234abcd
     * 方法：
     * 逆序排列 abcd: abcd1234 => dcba1234
     * 逆序排序 1234: dcba1234 =>dcba4321
     * 全部逆序 dcba4321 => 1234abcd
     *
     * O(3*N)
     */
    public static void cycleRightShift2(int[] ary, int k){
        k %= ary.length;
        reverse(ary, ary.length-k, ary.length-1);
        reverse(ary, 0,k-1);
        reverse(ary, 0, ary.length-1);
    }
    private static void reverse(int[] ary, int s, int e){
        while(s<e){
            int t = ary[e];
            ary[e] = ary[s];
            ary[s] = t;
            --e;++s;
        }
    }


    public static  void  main(String[] args){

    }
}
