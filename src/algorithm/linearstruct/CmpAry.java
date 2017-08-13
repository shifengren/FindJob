package algorithm.linearstruct;

/**
 * 等长数组A,B， 所含元素相同，但顺序不同, 假设同一个数组中不含有相同的元素。
 * 要求，同一数组中的数字不能比较
 * 只能取A数组中的某值和B数组中的某值比较，比较结果为大于，等于和小于
 *
 * 实现算法：正确匹配A,B数组中的元素
 */
public class CmpAry {

    static void simpleSolver(int[] A, int[] B){

        for(int i=0; i<A.length; ++i){
            for(int j=0; j<A.length; ++j){
                if(A[i] == B[j]){
                    System.out.printf("a[%d] = b[%d]\n", i, j);
                    break;
                }
            }
        }
    }

    private static class Cache{
        int idxB;
        char op; // B[idxB] op A[idxA]
        int idxA;
    }

    static void fastSolver(int[] A, int[] B){
        Cache[] C = new Cache[A.length];
        for(int i=0; i<C.length; ++i) C[i] = new Cache();

        int ai = A[0]; // randomly select a value from A

        int back = A.length-1, front = 0;

        int[] d = new int[A.length];
        int idx=0;

        for(int i=0; i<B.length; i++){
            if(B[i] > ai){
               C[back].idxB = i;
               C[back].op = '>';
               C[back--].idxA = 0;
            }else if(B[i] < ai){
                C[front].idxB = i;
                C[front].op = '<';
                C[front++].idxA = 0;
            }else{
                d[idx++]=i;
            }
        }
        for(int i=front; i<=back; ++i){
            C[i].idxB = d[--idx];
            C[i].idxA = 0;
            C[i].op = '=';
        }
        int bj = B[d[idx]];
        int i=0;
        for(int k=1; k<A.length; k++){
            if(A[k] > bj){
                for (i=back+1; i<C.length; ++i){
                    if(A[k]==B[C[i].idxB]){
                        C[i].idxA = k;
                        C[i].op = '=';
                        break;
                    }
                }
                swap(C[i], C[back+1]);
                back--;
                bj = A[k];
            }else if(A[k] < bj){
                for (i=front-1; i>=0; --i){
                    if(A[k]==B[C[i].idxB]){
                        C[i].idxA = k;
                        C[i].op = '=';
                        break;
                    }
                }
                swap(C[i], C[front-1]);
                front++;
                bj = A[k];
            }
            // 调整C数组，使front和back之间都带有=符号
            // 令bj = A[k]
            // 根据假设，同一个数组中不含有相同的元素，则上面的if语句必然有一个成立，能找到等于A[k]的数

            // 二分思想

        }
    }

    static void swap(Cache a, Cache b){
        Cache tmp = a;
        a = b;
        b = tmp;
    }
}
