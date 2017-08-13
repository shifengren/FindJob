package algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 并查集
 *  主要操作：
 *  1. 合并不相交的两个集合
 *  2. 判断元素是否属于同一个集合
 * 按秩（秩是指节点高度的一个上界，并不是节点数量）合并和路径压缩
 */
public class UionFindSets {

    // fathers[i] : 表示i的“父亲”的编号
    int[] fathers = null;
    int[] rank = null;

    public UionFindSets(int size){
        fathers = new int[size];
        rank = new int[size];
        // initialization
        for(int i=0; i<size; i++){
            fathers[i] = i;
            rank[i] = 0; // Can omit this statement due to the default value of rank[i] is 0
        }
    }
    //合并时将元素少的集合合并到元素多的集合中
    // 为什么将元素少的集合合并到元素多的集合？ 这样就不用花费额外操作调整rank了
    public void union(int x, int y){
        int fx = getFather(x, true), fy = getFather(y, true);
        if(rank[fx] < rank[fy]){
            fathers[fx] = fy; // fx 的父亲是 fy
        }else{
            fathers[fy] = fx; // fy的父亲是 fx
            if(rank[fx]==rank[fy])
                ++rank[fx]; //
        }
    }

//    public int getFather(int v, boolean noPathCompress){
//        if(v == fathers[v]) return v;
//        fathers[v] = getFather(fathers[v]);
//        return fathers[v];
//    }

    public int getFather(int v, boolean isPathCompress){
        int p = v;
        while(p != fathers[p])
            p = fathers[p];
        
        while(isPathCompress && v !=p ){ // 路径压缩
            int f = fathers[v];
            fathers[v] = p;
            v = f;
        }
        return fathers[v];
    }

    public boolean isSame(int x, int y){
        return getFather(x, false) == getFather(y, false);
    }
}
