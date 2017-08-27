package algorithm.tree.BinaryIndexTree;

import sun.nio.ch.sctp.SctpNet;

import java.util.Scanner;

/**
 * POJ 3468
 * http://blog.csdn.net/rylq56m9/article/details/51537068
 */
public class IUIQ {
    private static int MAXN = 100010;
    static long[] num = new long[MAXN], // num[i]: 1~i的数字的和
                  c1 = new long[MAXN],
                  c2 = new long[MAXN];

    static int n, q;

    static void update(long[] X, int i, long p){
        while(i <= n){
            X[i] += p;
            i += (i & (-i));
        }
    }
    static long sum(long[] X, int i){
        long ret = 0;
        while(i > 0){
            ret += X[i];
            i -= (i&-i);
        }
        return ret;
    }
    static long query(int x){
        long res = num[x] + (x+1)*sum(c1, x) - sum(c2, x);
        return res;
    }

    public static void main(String[] args){
        Scanner rd = new Scanner(System.in);
        while(rd.hasNext()){
            n = rd.nextInt();
            q = rd.nextInt();
            for(int i=1; i<=n; ++i){
                num[i] = num[i-1] + rd.nextLong();
            }
            while((q--)>0){
                String s = rd.next();
                String[] ss = s.split(" ");
                if(ss[0].charAt(0) == 'Q'){
                    int a = Integer.parseInt(ss[1]);
                    int b = Integer.parseInt(ss[2]);
                    System.out.println(query(b) - query(a-1));
                }else{
                    int a = Integer.parseInt(ss[1]);
                    int b = Integer.parseInt(ss[2]);
                    int c = Integer.parseInt(ss[3]);
                    update(c1, a, c);
                    update(c1, b+1, -c);
                    update(c2, a, c*a);
                    update(c2, b+1, -c*(b+1));
                }
            }
        }

        rd.nextInt();
    }
}
