package algorithm.dynamicprogram;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxNumInSlidingWindow {

    private static class Pair{
        public int idx,val;
        public Pair(int idx, int val){
            this.idx = idx;
            this.val = val;
        }
    }
    /**
     *
     * @param A
     * @param k 滑动窗口大小;
     * @return 滑动窗的中最大数
     */
    public int[] calcMaxNumInSlidingWindow(int[] A, int k){
        Deque<Pair> q = new ArrayDeque<>();
        int[] res = new int[A.length - k + 1];
        int curMax = Integer.MIN_VALUE;

        for(int i=0, cnt=0; i<A.length; cnt++, i++){

            // 更新q
            while(q.peekLast().val < A[i])  q.pollLast();

            q.offerLast(new Pair(i, A[i]));

            while(!q.isEmpty() && q.peekFirst().idx < i-k+1)
                q.pollFirst();

            if(i >= k-1){
                res[i-k+1] = q.peekFirst().val;
            }
        }
        return res;
    }
}
