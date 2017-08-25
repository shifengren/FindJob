package algorithm.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DijkstraArray {


    static final int INF = 0x7fffffff;
    final int MAXN = 205;
    int[] dist = new int[MAXN]; // dist[i]: 第i个节点到start节点的最短路径长度
    int[] u = new int[MAXN]; // u[i] : 第i条边的顶点1
    int[] v = new int[MAXN]; // v[i] : 第i条边的顶点2
    int[] w = new int[MAXN]; // w[i] : 第i条边的权重
    int[] first = new int[MAXN]; // first[i]: 第i个节点的next指针
    int[] next = new int[MAXN]; // next[i]: 第i条边另一个端点
    int[] pre = new int[MAXN]; // 用来记录路径
    int n, m; // 顶点个数，边个数
    int start, end;

    private static class Pair{
        int v, dist;
        Pair(int v, int dist){
            this.v = v;
            this.dist = dist;
        }
    }

    public void solver(){
        for(int i=0; i<=n; ++i){
            dist[i] = INF;
            pre[i] = 0;
        }

        dist[start] = 0;
        pre[start] = -1;

        PriorityQueue<Pair> que = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.dist-o2.dist;
            }
        });

        boolean[] done = new boolean[n]; // 表示原点到定点i的最大路径已经求得

        que.offer(new Pair(start, 0));

        while(!que.isEmpty()){
            Pair fst = que.poll();
            if(done[fst.v]) continue;
            done[fst.v] = true;
            for(int ei = first[fst.v]; ei!=-1; ei = next[ei]){ // 与fst.v邻接的其他节点
                if(dist[v[ei]] > dist[fst.v] + w[ei]){
                    dist[v[ei]] = dist[fst.v] + w[ei];
                    pre[v[ei]] = fst.v;
                    que.offer(new Pair(v[ei], dist[v[ei]]));
                }
            }
        }
    }

    public void addEdge(int u, int v, int w, int i){
        this.u[i] = u; this.v[i] = v; this.w[i] = w;
        next[i] = first[u];
        first[u] = i;
    }

    public static void main(String[] args){
        DijkstraArray slv = new DijkstraArray();
        Scanner rd = new Scanner(System.in);

        slv.n = rd.nextInt(); // 顶点个数
        slv.m = rd.nextInt(); // 边个数

        slv.start = rd.nextInt(); // 源点
        slv.end = rd.nextInt(); // 终点

        Arrays.fill(slv.first, -1);

        for(int i=0; i<slv.m; ++i){ // 建立图的邻接矩阵
            int u = rd.nextInt();
            int v = rd.nextInt();
            int w = rd.nextInt();
            slv.addEdge(u, v, w, i);
        }

        slv.solver();

        System.out.println(slv.dist[slv.end]);
        rd.close();
    }
}
