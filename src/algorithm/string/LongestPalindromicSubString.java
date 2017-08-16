package algorithm.string;


/**
 * 最长回文字串
 */
public class LongestPalindromicSubString {

    public int findLongestPalindromicSubstring(String s){
        // 填充字符, 假设字符#在s中没有出现过
        String cs = "#";
        for(int i=0; i<s.length(); ++i){
            cs += s.charAt(i);
            cs += "#";
        }

        // 保存最长的回文字符串的长度
        int maxlen = 0;
        int[] RL = new int[cs.length()];
        int maxRight=0, pos=0;

        for(int i=0; i<cs.length(); ++i){
            // 根据 i 位于maxRight的左边还是右边更新RL[i]
            if(i < maxRight){
                // i 在maxRight左边的情况
                RL[i] = Math.min(RL[2*pos-1], maxRight-i);
            }else{
                // i 在maxRight右边的情况
                RL[i]=1;
            }

            // 边界判断, 回文判断
            while(i+RL[i]<cs.length() && i-RL[i] >=0 && cs.charAt(i+RL[i])==cs.charAt(i-RL[i])){
                ++RL[i];
            }

            if(RL[i]+i-1 > maxRight){
                maxRight = RL[i] + i -1;
                pos = i;
            }

            // 更新最长回文字符串的长度
            maxlen = maxlen > RL[i] ? RL[i] : maxlen;
        }
        // 利用RL的性质
        return maxlen-1;
    }

}
