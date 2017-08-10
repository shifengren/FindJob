package algorithm.dynamicprogram;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 动态规划之"可作为Fibonacci数列前缀的非空子序列个数
 *
 * For problem description, see the following link
 * http://www.jianshu.com/p/0c58035a56c5
 */
public class SubSeqAsFibonaaciPrefix {
    static final int Max=1000010;
    static int[] fibs=new int[Max];
    static Map<Integer,Integer> indFibs = new HashMap<>();
    static void init(){
        fibs[0]=fibs[1]=1;
        for(int i=2; ; ++i){
            fibs[i]=fibs[i-2]+fibs[i-1];
            indFibs.put(fibs[i], i+1);// 记录fib数在序列中的位置
            if(fibs[i]>=Max)
                break;
        }
        indFibs.put(1, 2);
    }
    static void display(int[] f){
        for(int i=0; i<f.length; ++i){
            System.out.print(f[i] +" ");
        }
        System.out.println();
    }
    static void solver(){
        init();
        int[] f = new int[26];
        f[0]=1;
        final int M = 1000000007;
        Scanner scan = new Scanner(new BufferedInputStream(System.in));
        int n = scan.nextInt();
        for(int i=0; i<n; ++i){
            int x = scan.nextInt();
            if(indFibs.containsKey(x)){
                int j = indFibs.get(x);
                f[j] = (f[j-1]+f[j]) % M;
                if(x==1){
                    f[1]=(f[1]+f[0]) % M;
                }
            }
        }
        scan.close();
        int ans = 0;
        for(int i=1; i<=25; ++i){
            ans = (ans+f[i])%M;
        }
        System.out.println(ans);
    }
    public static void main(String[] args) {
        solver();
    }
}
