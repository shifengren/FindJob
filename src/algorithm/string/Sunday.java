package algorithm.string;


public class Sunday {

    /**
     * @param S 母串
     * @param T 模式串
     * @return
     */
    static int sunday(String S, String T) {
        int[] moveLen = getMoveLength(T);

        int i = 0; // S 下标
        int j = 0;// T 下标
        while (j < T.length() && i < S.length()) {

            for (j = 0; j < T.length() && i + j < S.length() && S.charAt(i + j) == T.charAt(j); ++j) ;

            // 匹配成功
            if (j == T.length()) return i;

            // S中没有找到含T的字符串
            if (i + T.length() >= S.length()) return -1;

            //
            i += moveLen[S.charAt(i + T.length())];

        }
        return -1;

    }

    static int[] getMoveLength(String T) {
        int[] moveLen = new int[256]; // 字符的种类数目
        //   默认S中的任何字符均不出现在T中，那么每次移动的距离为T的长度 + 1
        for (int i = 0; i < moveLen.length; ++i) {
            moveLen[i] = T.length() + 1;
        }

        //查找能够出现在T中的字符，若一个字符出现多次，选择最右位置的字符，所以T的下标遍历从0开始
        for (int i = 0; i < T.length(); i++) {
            moveLen[T.charAt(i)] = T.length() - i;
        }
        return moveLen;
    }

    public static void main(String[] args) {
        String S = "SEARCHSUBSTRING";
        String T = "SUBSTRING";

        System.out.print(sunday(S, T));
    }

}
