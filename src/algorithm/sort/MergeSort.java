package algorithm.sort;

public class MergeSort {

    public static void mergeSort(int[] A){

        int[] tmp = new int[A.length];

        int left_min, left_max, right_min, right_max, next;
        for(int step=1; step<A.length; step *= 2){
            for(left_min = 0; left_min < A.length - step; left_min = right_max){
                right_min = left_max = left_min + step;
                right_max = left_max + step;
                if(right_max > A.length) right_max = A.length;
                next = 0;
                while(left_min < left_max && right_min < right_max)
                    tmp[next++] = A[left_min] > A[right_min] ? A[right_min++] : A[left_min++];
                while(left_min < left_max)
                    A[--right_min] = A[--left_max];
                while (next > 0)
                    A[--right_min] = tmp[--next];
            }
        }
    }

    public static void main(String[] args){
        int[] a = {1, 5, 4, 6, 3};
        mergeSort(a);
        for(int i=0; i<a.length; i++){
            System.out.printf("%d\t", a[i]);
        }
    }
}
