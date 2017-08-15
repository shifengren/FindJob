package algorithm.dynamicprogram;

import algorithm.heap.BinaryHeap;

/**
 * 题目：给定一个长度为N的整数数组，只允许用乘法，不能用除法，计算任意(N-1)个数的组合中乘积最大的一组。
 *
 * 朴素算法： O(n^2)
 * 找出所有N-1个数的组合，分别计算乘积；N个数共有N个 (N-1)个数的组合, 太耗时
 *
 * 详细解释：
 * http://www.jianshu.com/p/0b143101b39a
 */
public class MaxProductOfSubArray {

    /**
     * 空间换时间 时空复杂度O(N)
     *
     * @param A
     * @return
     */
    public int findMaxProduct(int[] A) {
        int[] s = new int[A.length];
        int[] e = new int[A.length];

        s[0] = A[0];
        for (int i = 1; i < A.length; ++i) {
            s[i] = s[i - 1] * A[i];
        }
        e[A.length - 1] = A[A.length - 1];
        for (int i = A.length - 2; i >= 0; --i) {
            e[i] = e[i + 1] * A[i];
        }
        int maxPro = Math.max(s[A.length - 1], e[0]);
        for (int i = 1; i < A.length - 1; ++i) {
            int tmp = s[i - 1] * e[i + 1]; // n-1个数的乘积
            maxPro = maxPro < tmp ? tmp : maxPro;
        }
        return maxPro;
    }

    public int findMaxProduct2(int[] A) {
        int np = 0, nn = 0, nz = 0; // 数组A中正数的个数，负数的个数，0的个数
        int min_abs_pv = Integer.MAX_VALUE, min_abs_nv = Integer.MAX_VALUE; // 数组中绝对值最小的正数和负数
        int max_abs_nv = 0; // 数组中绝对值最大的负数
        for (int i = 0; i < A.length; ++i) {
            if (A[i] > 0) {
                ++np;
                min_abs_pv = min_abs_pv > A[i] ? A[i] : min_abs_pv;
            } else if (A[i] < 0) {
                ++nn;
                min_abs_nv = min_abs_nv > -A[i] ? -A[i] : min_abs_nv;
                max_abs_nv = max_abs_nv < -A[i] ? -A[i] : max_abs_nv;
            } else ++nz;


        }

        if (nz >= 2) return 0;

        int signOfP = 0;
        if (nn % 2 == 0) signOfP = 1;
        else signOfP = -1;

        int maxPro = 1;
        if (signOfP == 0) {

            for (int i = 0; i < A.length; ++i) {
                if (A[i] != 0) maxPro *= A[i];
            }
            return maxPro > 0 ? maxPro : 0;
        } else if (signOfP == -1) {
            for (int i = 0; i < A.length; ++i) {
                if (!(A[i]<0 && Math.abs(A[i]) == min_abs_nv)) // 去掉绝对值最大负数
                    maxPro *= A[i];
            }
            return maxPro;
        } else { // P为正数
            if (np > 0) { // 数组中存在正数
                for (int i = 0; i < A.length; ++i) {
                    if (!(A[i]>0 && A[i] != min_abs_pv))
                        maxPro *= A[i];
                }
                return maxPro;
            } else {// 数组中不存在正数
                for (int i = 0; i < A.length; ++i) {
                    if (!(A[i]<0 && Math.abs(A[i]) == max_abs_nv))
                        maxPro *= A[i];
                }
                return maxPro;
            }
        }

    }

}
