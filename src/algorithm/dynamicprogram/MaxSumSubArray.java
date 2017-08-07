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
        for(int i=0; i<ary.length; ++i){
            localSum += ary[i];
            if(localSum > maxSum){
                maxSum = localSum;
                eidx = i;
            }else if(localSum < 0){
                localSum = 0;
                sidx = i+1;
                eidx = 0;
            }
        }
        return new int[]{sidx, eidx};
    }




    public static void main(String[] args){

        int[] ary = {1, -2, 3, 10, -4, 7, 2, -5};
        int sum = maxSumSubAry(ary);
        int[] se = maxSumSubAry2(ary);
        System.out.println(se[0]+", "+se[1]);
    }
}
