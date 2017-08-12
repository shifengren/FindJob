package algorithm.dynamicprogram;

/**
 * 个整型数组，将其划分为和相同的4个切片，例如：{ 2, 3, 5, 1, 2, 2, 1, 1, 3 }，
 * 切片操作后划分为：{2,3}，{5}，{1,2,2}，{1,1,3}，也就找到所谓的四等分点。
 * 只不过输出结果为true或者false（是否能得到这样的4个切片）。同时要求时间复杂度和空间复杂度为o(n)。
 */
public class FindQuarterPoint {
    static boolean solve(int[] A){
        // 求和
        int sum = 0;
        for(int i: A){
            sum += i;
        }
        // 是否可以化为4个切片
        int sliceSum = sum / 4;
        if(sliceSum * 4 != sum){
            return false;
        }
        // 用于保存每个切片的结束索引
        int[] sliceIndex = new int[4];
        int sliceCount = 0;
        int tmpSum = 0;
        for(int i=0; i<A.length; ++i){
            tmpSum += A[i];
            if(tmpSum == sliceSum){
                sliceIndex[sliceCount] = i;
                sliceCount++;
                tmpSum=0;
            }
        }
        if(sliceIndex[3] == A.length-1){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        int[] a = { 2, 3, 5, 1, 2, 2, 1, 1, 3 };

        System.out.println(solve(a));
    }
}
