package algorithm.graph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 浙大研究生复试2010年上机试题-最短路径问题
 * 问题描述：
   给你n个点，m条无向边，每条边都有长度d和花费p，给你起点s终点t，
   要求输出起点到终点的最短距离及其花费，如果最短距离有多条路线，则输出花费最少的。

 输入：输入n,m，点的编号是1~n,然后是m行，
 每行4个数 a,b,d,p，表示a和b之间有一条边，且其长度为d，花费为p。
 最后一行是两个数 s,t;起点s，终点t。n和m为0时输入结束。
 (1<n<=1000, 0<m<100000, s != t)
 输出：一行有两个数， 最短距离及其花费。
 */
/*
 最短路算法比较：
 1. Dijkstra 算法
    无法用于带负权的回路
    运行时间，依赖于其最小优先队列的采取何种具体实现决定，而最小优先队列可有以下三种实现方法：
        a. 利用从1至|V| 编好号的顶点，简单地将每一个定点存入d[v]，
           Dijkstra 算法的运行时间为O(V^2+E).
        b. 二叉/项堆实现最小优先队列
            EXTRACT-MIN(Q)的运行时间为O(V*lgV), Dijkstra 算法的运行时间为O(V*lgV+E*lgV), 若所有顶点都是从源点可达的话，O（(V+E)*lgV）=O（E*lgV
            稀疏图时，则E=O(V^2/lgV)，此Dijkstra 算法的运行时间为O(V^2)
        c. 斐波那契堆实现最小优先队列
            EXTRACT-MIN(Q)的运行时间为O(V*lgV)
            Dijkstra 算法的运行时间即为O(V*lgV+E)
 2. Bellman-Ford算法
    单源最短路，可以判断有无负权回路;
    时效性较好，时间复杂度O(VE)
 3. SPFA (shortest path faster algorithm)
    Bellman-Ford的队列优化，时效性相对好，时间复杂度O(kE)（k 远小于 V）

    在稀疏图中对单源问题来说SPFA的效率略高于 Heap+Dijkstra；
    对于无向图上的APSP(All Pairs Shortest Paths)问题，SPFA算法加上优化后效率更是远高于Heap+Dijkstra
 */

/*
   简洁起见，我们约定有向加权图G不存在负权回路，即最短路径一定存在。
   判断是否存在环：
      1. 拓扑排序，以判断是否存在负权回路。
      2. 如果某个点进入队列的次数超过V次则存在负环(SPFA)
            使用SPFA的bfs实现，判断负环，不稳定
            SPFA的dfs实现判断负环比SPFA快

   数组d记录起始节点到每个结点的最短路径`估计值`，邻接表来存储图G

   动态逼近法：设立一个先进先出的队列用来保存待优化的结点，优化时每次取出队首结点u，
   并且用u点当前的最短路径估计值对离开u点所指向的结点v进行松弛操作. SPFA算法有两个优化算法 SLF 和 LLL：
       1. SLF：Small Label First 策略，设要加入的节点是j，队首元素为i，若dist(j)<dist(i)，则将j插入队首，
          否则插入队尾。
       2. LLL：Large Label Last 策略，设队首元素为i，队列中所有dist值的平均值为x，若dist(i)>x则将i插入
          到队尾，查找下一元素，直到找到某一i使得dist(i)<=x，则将i出对进行松弛操作。

    引用网上资料，SLF 可使速度提高 15 ~ 20%；SLF + LLL 可提高约 50%。

    在实际的应用中SPFA的算法时间效率不是很稳定，为了避免最坏情况的出现，通常使用效率更加稳定的Dijkstra算法

   定理: 只要最短路径存在，SPFA算法必定能求出最小值。
   期望的时间复杂度O(ke)， 其中k为所有顶点进队的平均次数，可以证明k一般小于等于2




 */
public class SPFA {

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


    /**
     * Add edge
     * <起点,终点,边权值>
     */
    static void add(Vertex[] G, int s, int e, int val){
        Edge edge = new Edge(e, val, null);

        if(G[s].head == null){
            G[s].head = edge;
            G[s].last = edge;
        }else {
            G[s].last.next = edge;
            G[s].last = edge;
        }
    }

    /**
     * 松弛操作
     */
    static int relax(int[] dist, int s, int e, int val){
        if(dist[s]+val < dist[e]){
            dist[e] = dist[s] + val;
            return 1;
        }
        return 0;
    }

    /**
     * SPFA有负权回路返回0,否则返回1并且最短路径保存在dis[]
     * @param G graph represented by adjancent List
     * @param n number of vertexes
     * @param s0 start point
     */
    static int spfa_bfs(Vertex[] G, int n, int s0){
        Deque<Integer> queue = new ArrayDeque();
        boolean[] vis = new boolean[n];
        int[] cnt = new int[n]; // cnt[i] 节点i进入队列的次数
        int[] dist = new int[n]; // dist[i] 开始节点 到节点i的最短距离

        for(int i=0; i<dist.length; ++i){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s0] = 0;

        queue.offerLast(s0);
        vis[s0]=true;
        cnt[s0]++;

        while(!queue.isEmpty()){
            // 出队列
            int s = queue.pollFirst();
            // 不在队列中，则消除标记
            vis[s] = false;
            Edge p = G[s].head;
            while(p!=null){
                if(relax(dist, s, p.e, p.val)==1 && !vis[p.e]){
                    queue.offerLast(p.e);
                    vis[p.e] = true;
                    cnt[p.e]++;
                    if(cnt[p.e] > n){ // 存在负权环
                        return 0;
                    }
                }
                p = p.next;
            }
        }
        return 1;
    }

    static int spfa_dfs(Vertex[] G, int n, int s0){
        Deque<Integer> queue = new ArrayDeque();
        boolean[] vis = new boolean[n];
        int[] cnt = new int[n]; // cnt[i] 节点i进入队列的次数
        int[] dist = new int[n]; // dist[i] 开始节点 到节点i的最短距离

        for(int i=0; i<dist.length; ++i){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[s0] = 0;

        queue.offerLast(s0);
        vis[s0]=true;
        cnt[s0]++;

        while(!queue.isEmpty()){
            // 出栈
            int s = queue.pollLast();
            // 不在栈中，则消除标记
            vis[s] = false;
            Edge p = G[s].head;
            while(p!=null){
                if(relax(dist, s, p.e, p.val)==1 && !vis[p.e]){
                    queue.offerLast(p.e);
                    vis[p.e] = true;
                    cnt[p.e]++;
                    if(cnt[p.e] > n){ // 存在负权环
                        return 0;
                    }
                }
                p = p.next;
            }
        }
        return 1;
    }

}
