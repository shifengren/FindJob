package algorithm.graph;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 优先级队列 优化
 */
public class Dijkstra {

    private static class Edge{
        int e;// 边终点
        int val; // 边权值
        Edge next;

        public Edge(int e, int val, Edge next){
            this.e = e;
            this.val = val;
            this.next = next;
        }
    }

    private static class Vertex{
        Edge head, last;
    }

    private static class Pair implements Comparable<Pair>{
        public int s, curMinDist;

        public Pair(int s, int curMinDist){
            this.s= s;
            this.curMinDist = curMinDist;
        }

        @Override
        public int compareTo(Pair b){
            return curMinDist - b.curMinDist;
        }
    }

    private int[] dist = null;
    private int n;
    private PriorityQueue<Pair> pq = null;

    public Dijkstra(int n){
        dist = new int[n];
        this.n = n;
        pq = new PriorityQueue<>();
    }

    // dist(s, y) = min(dist(s, y), dist(s, x) + dist(x, y))
    public void solver(int s0, Vertex[] vertices){
        for(int i=0; i<=this.n; i++)
            dist[i] = Integer.MAX_VALUE;
        dist[s0] = 0;

        pq.offer(new Pair(s0, dist[s0]));

        while(!pq.isEmpty()) {
            Pair curS = pq.poll();

            Edge p = vertices[curS.s].head;
            for(; p!=null; p = p.next){
                if(dist[p.e] > curS.curMinDist + p.val){
                    dist[p.e] = curS.curMinDist + p.val;
                    pq.offer(new Pair(p.e, dist[p.e]));
                }
            }
        }

    }

    public void clear(){
        Arrays.fill(dist, 0);
        n = 0;
        pq.clear();
    }

}
