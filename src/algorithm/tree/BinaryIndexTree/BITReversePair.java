package algorithm.tree.BinaryIndexTree;

public class BITReversePair {
    // 假设ary中都是正数
    private static int solution(int[] ary){
        int res = 0;
        for(int i=0 ;i<ary.length; ++i){
            res = ary[i] > res ? ary[i] :res;
        }
        BinaryIndexTree bit = new BinaryIndexTree(res);
        res = 0;
        for(int i=ary.length-1; i>=0; i--){
            res += bit.sum(ary[i]-1);
            bit.add(ary[i], 1);
        }
        return res;
    }

    public static void main(String[] args){

    }

}
