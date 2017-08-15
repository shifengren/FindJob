package algorithm.dynamicprogram;

/**
 * 给定n个数的数组，找到所有长度大于等于k的连续子数组中平均值最大的那个。返回那个最大的平均值。
 * 1 <= k <= n <= 10000，数组中的元素在范围[-10000, 10000]之间，最后返回的答案的误差应在10^(-5)以内。
 *
 * 题解：
 * http://www.jianshu.com/p/97c976bd2f27
 */
public class MaximumAverageSubArray {


    // O(n * log(maxv-minv)/eps)
    public double findMaxAvg(int[] A, int k) {
        double left = Integer.MAX_VALUE;
        double right = Integer.MIN_VALUE;

        // 初始化 二分区间
        for (int i = 0; i < A.length; ++i) {
            left = Math.min(left, (double) A[i]);
            right = Math.max(right, (double) A[i]);
        }


        while (right - left > 1e-6) {
            // 求出当前区间的中值
            double mid = (right + left) / 2;

            // 求数组的累加和
            double[] sumAi = new double[A.length + 1];
            sumAi[0] = 0;
            for (int i = 0; i < A.length; i++) {
                sumAi[i + 1] = sumAi[i] + A[i] - mid;
            }

            // 求长度大于等于k的最大和子数组
            // 找长度大于等于k的最大和子数组等价于找i,j，满足i-j>=k，且使s(i)-s(j)最大
            // 最大化s(i),最小化s(j)
            double preMin = 0;
            double sumMax = Integer.MIN_VALUE;
            for (int i = k; i <= A.length; ++i) {
                sumMax = Math.max(sumMax, sumAi[i] - preMin);
                preMin = Math.min(preMin, sumAi[i - k + 1]);
            }

            // 判断是否存在长度大于等于k的字数组，其平均值大于等于 mid(sumMax>=0)
            if (sumMax >= 0)
                left = mid;
            else right = mid;

        }
        return left;
    }

    public static void main(String[] args) {

    }

}
