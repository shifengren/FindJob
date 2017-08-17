package algorithm.dynamicprogram;

/**
 * 求数组中最长递增子序列的长度
 */
public class LongestIncreasingSubSeq {

    /**
     *
     * LIS[i] : ary中前i个元素中，最长递增子序列的长度
     * LIS[i+1] = max(1, LIS[k]+1), ary[i+1]>ary[k], for any k<=i;
     *
     * O(N^2)
     */
    public static int solution1(int[] ary){
        int[] LIS = new int[ary.length];
        LIS[0] = 1;
        for(int i=1; i<ary.length; ++i){
            LIS[i] = 1;
            for(int j=i-1; j>=0; j--){
                if(ary[i] > ary[j] && LIS[i] < LIS[j]+1){
                    LIS[i] = LIS[j] +1;
                }
            }
        }
        int max=LIS[0];
        for(int i=1; i<ary.length; ++i){
            max = LIS[i] > max ? LIS[i] : max;
        }
        return max;
    }

    /**
     * solution1 当考察第i+1个元素时，没有考察前i个元素的分布
     *
     * 对前i个元素的任何一个递增子序列，若任何一个子序列的最大的元素比ary[i+1]小，ary[i+1]则可以加到这个序列后面
     *
     * LIS[i] : ary的前i个元素中，以`ary[i]为最大元素`的最长递增子序列的长度
     *
     * 令maxv[i]: 表示长度为i的递增子序列的最大元素的最小值
     * 长度为LIS[i]递增子序列的最大元素的最小值为maxv[LIS[i]]
     */
    public static int solution2(int[] ary){
        int[] maxv = new int[ary.length+1];
        int[] LIS = new int[ary.length];

        // 初始化
        int min = ary[0];
        for(int i=0; i<ary.length; i++) {
            LIS[i]=1;
            min = min > ary[i] ? ary[i] : min;
        }

        maxv[0] = min-1; // 长度为0的递增子序列的最大元素的最小值，边界值
        maxv[1] = ary[0]; // 长度为1的递增子序列的最大元素的最小值
        int maxlen = 1; // 最长递增子序列的长度
        int j=-1;
        for(int i=1; i<ary.length; ++i){

            for(j=maxlen; j>=0; j--){
                if(ary[i] > maxv[j]){
                    LIS[i] = j+1;
                    break;
                }
            }
            if(LIS[i] > maxlen){
                maxlen = LIS[i];
                maxv[LIS[i]] = ary[i];
            }else if(ary[i]>maxv[j] && ary[i] < maxv[j+1]){ // 以ary[i]作为终结元素的递增子序列的长度为 j+1
                maxv[j+1] = ary[i];
            }
        }
        return maxlen;
    }

    public static int solution3(int[] ary){
        int[] maxv = new int[ary.length+1];
        int[] LIS = new int[ary.length];

        // 初始化
        int min = ary[0];
        for(int i=0; i<ary.length; i++) {
            LIS[i]=1;
            min = min > ary[i] ? ary[i] : min;
        }

        maxv[0] = min-1; // 长度为0的递增子序列的最大元素的最小值，边界值
        maxv[1] = ary[0]; // 长度为1的递增子序列的最大元素的最小值
        int maxlen = 1; // 最长递增子序列的长度
        int j=-1;
        for(int i=1; i<ary.length; ++i){

            // 因为 如果 j<i, 则 maxv[j] < maxv[i]
            // 因为maxv具有单调性，因此此处可以用二分查找优化
            // j = LIS[i-1] ?
            for(j=maxlen; j>=0; j--){
                if(ary[i] > maxv[j]){ // maxv[j+1] > maxv[j]
                    LIS[i] = j+1;
                    break;
                }
            }

            // 二分优化
//            int l=0, r = maxlen;
//            j = - 1;
//            while(l <= r){
//                int mid = (r+l)/2;
//                if(ary[i] > maxv[mid]) {
//                    l = mid + 1;
//                    j = mid;
//                }
//                else if(ary[i] <= maxv[mid]) {
//                    r = mid - 1;
//                }
//
//            }

            if(LIS[i] > maxlen){
                maxlen = LIS[i];
                maxv[LIS[i]] = ary[i];
            }else if(ary[i]>maxv[j] && ary[i] < maxv[j+1]) { // 以ary[i]作为终结元素的递增子序列的长度为 j+1
                maxv[j+1] = ary[i];
            }
        }
        return maxlen;
    }

    public static void main(String[] args){

        int[] ary = {1, 2, 3, 4, 5 ,7, 8, 9};
        int l=0, r = 7;
        int prev=-1;
        int q = 10;
        while(l <= r){
            int mid = (r+l)/2;
            if(q > ary[mid]) {
                l = mid + 1;
                prev = mid;
            }
            else if(q <= ary[mid]) {
                r = mid - 1;
            }
        }
        System.out.println(prev);
    }
}
