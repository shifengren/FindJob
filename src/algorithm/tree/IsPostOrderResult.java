package algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsPostOrderResult {

    /**
     * 判断输入序列ary是不是二元查找树的后序遍历序列
     * 思路：
     * 在后续遍历得到的序列中，最后一个元素为树的根结点。
     * 从头开始扫描这个序列，比根结点小的元素都应该位于序列的左半部分；
     * 从第一个大于跟结点开始到跟结点前面的一个元素为止，所有元素都应该大于跟结点，因为这部分元素对应的是树的右子树。
     * 根据这样的划分，把序列划分为左右两部分，我们递归地确认序列的左、右两部分是不是都是二元查找树
     */
    static boolean isPostOrderResult(List<Integer> ary) {

        int root = ary.get(ary.size() - 1);

        int i = 0;
        for (i = 0; i < ary.size()-1; ++i) {
            if (ary.get(i) > root) break;
        }

        int j = i;
        for (; j < ary.size() - 1; ++j) {
            if (ary.get(j) < root) return false;
        }

        boolean left = true;
        if (i > 0) {
            List<Integer> leftSubAry = ary.subList(0, i);
            left = isPostOrderResult(leftSubAry);
        }

        boolean right = true;
        if(i < ary.size()-1){
            List<Integer> rightSubAry = ary.subList(i, ary.size());
            right = isPostOrderResult(rightSubAry);
        }
        return left && right;

    }

    //
    public static void main(String[] args) {

    }
}
