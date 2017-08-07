package algorithm.dynamicprogram;


import java.util.Scanner;

/**
 * 对于一个序列a=a[1], a[2] ,..., a[n]。
 * 任意子序列可表示为{aPk, aPk+1, ..., aPk+m}，其中 1<= Pk < Pk+1< Pk+m<=n，
 * 对于给定的序列，求不同的子序列的个数。
 *
 * 详细解释见博客: http://www.jianshu.com/p/3d061dbe0c99
 */
public class NumberOfDifferentSubAry {

    public static void main(String[] args) throws Exception {
        final int MaxLen = 99999;
        int[] last = new int[MaxLen]; // 假设初始化为0
        int[] f = new int[MaxLen];


        Scanner rd = new Scanner(System.in);
        // Read inputs
        int n = rd.nextInt(); // read the length of the seq
        for (int i = 1; i <= n; ++i) {
            int x = rd.nextInt();
            if (last[x] != 0) {
                f[i] = f[i - 1] * 2 - f[last[x]];
            } else {
                f[i] = f[i - 1] * 2 + 1;
            }

            last[x] = i;
        }
        rd.close();
    }
}
