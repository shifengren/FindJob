package algorithm.dynamicprogram;


import java.util.Scanner;

/**
 *    输入一个整形数组，数组里有正数也有负数。
     数组中`连续`的一个或多个整数组成一个子数组，每个子数组都有一个和。
     求所有子数组的和的最大值。要求时间复杂度为O(n)。

     例如输入的数组为1, -2, 3, 10, -4, 7, 2, -5，和最大的子数组为3, 10, -4, 7, 2，
     因此输出为该子数组的和18。

     其他解法以及详细的证明，见我的博客: www.jianshu.com/p/04c03059e538
 */
public class MaxSumSubArray {

    /**
     * dp: f[i] = max(f[i-1]+ary[i], ary[i])
     * @param ary
     * @return the maximal sum of sub ary
     */
    static int maxSumSubAry(int[] ary){
        int maxSum = 0, localSum=0;
        for(int i=0; i<ary.length; ++i){
            localSum += ary[i];
            if(localSum > maxSum){
                maxSum = localSum;
            }else if(localSum < 0){
                localSum = 0;
            }
        }
        return maxSum;
    }

    /**
     * dp: f[i] = max(f[i-1]+ary[i], ary[i])
     * @param ary
     * @return start position and end position
     */
    static int[] maxSumSubAry2(int[] ary){
        int maxSum = 0, localSum=0;
        int sidx=0, eidx=0;
        int p = 0;
        for(int i=0; i<ary.length; ++i){
            localSum += ary[i];
            if(localSum > maxSum){
                maxSum = localSum;
                sidx = p;
                eidx = i;
            }else if(localSum < 0){
                localSum = 0;
                p = i;
            }
        }
        return new int[]{sidx, eidx};
    }

    /**
     * 该算法对数组中全部时负数的情况，也能返回数组中最大的数，而不像上面的算法只能返回0
     * @param A
     * @return
     */
    public static int maxSum(int[] A){

        int s=A.length-1, e=A.length-1; // [s, e]
        int p =0;
        int nStart = A[A.length-1];
        int nAll = A[A.length - 1];
        for(int i = A.length-2; i>=0; --i){
            if(nStart < 0){
                nStart = 0;
                p = i;
            }
            nStart += A[i];
            if(nStart > nAll){
                if(nStart==A[i]) e = p;
                nAll = nStart;
                s = i;
            }
        }
        System.out.printf("sidx=%d, eidx=%d\n", s, e);
        return nAll;
    }
    public static int maxSum1(int[] A){
        int[] start = new int[A.length];
        int[] all = new int[A.length];

        all[A.length-1] = A[A.length-1];
        start[A.length-1] = A[A.length - 1];
        for(int i = A.length-2; i>=0; --i){
            start[i] = Math.max(A[i], A[i] + start[i+1]);
            all[i] = Math.max(start[i], all[i+1]);
        }
        return all[0];
    }

    public static int maxSum2(int[] A){
        int nStart = A[A.length-1];
        int nAll = A[A.length-1];
        for(int i = A.length-2; i>=0; --i){
            nStart = Math.max(A[i], A[i] + nStart);
            nAll = Math.max(nStart , nAll);
        }
        return nAll;
    }

    /**
     * The correctness should be validated in the future!!!
     * @param A
     * @return
     */
    public static int maxSumCycle(int[] A){
        int s1 = maxSum(A);

        int s2 = 0;

        int nAll = A[A.length-2];
        int nStart = A[A.length-2];
        for(int i=A.length-1; i>=0; --i){
            s2 += A[i];

            // Find maximum abs value from range 1~A.length-2
            if(i>=1 && i<=A.length-3){
                nStart = Math.min(nStart, A[i] + nStart);
                nAll = Math.min(nStart, nAll);
            }

        }
        if(nAll>0) nAll = 0;
        return Math.max(s1, Math.max(s2, s2 + nAll));

    }

    public static void main(String[] args){

        int[] ary = {1, -2, 3, 10, -4, 7, 2, -5};
//        int[] ary = {0, -2, 3, 5, -1, 2};
//        int[] ary = {-9, -2, -3, -5, -3};
//        int[] ary = {1, -2, 3, 5, -3, 2};
//        int sum = maxSumSubAry(ary);
//        System.out.printf("%d\n", sum);
        System.out.printf("%d\n", maxSum(ary));
//        int[] se = maxSumSubAry2(ary);
//        System.out.println(se[0]+", "+se[1]);
    }
}
