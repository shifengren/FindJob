package algorithm.dynamicprogram;

public class Factorial {

    public static int numOfZeroINFactorial_1(int N){
        int res=0;
        for(int i=1; i<=N; ++i){
            int j = i;
            while(j % 5 == 0){
                res++;
                j /= 5;
            }
        }
        return res;
    }

    public static int numOfZeroINFactorial_2(int N){
        int res=0;
        while(N!=0){
            res += (N/5);
            N /= 5;
        }
        return res;
    }

    public static int solution1(int N){
        int res=0;
        while(N!=0){
            res += (N >> 1);
            N >>= 1;
        }
        return res+1;
    }




}
