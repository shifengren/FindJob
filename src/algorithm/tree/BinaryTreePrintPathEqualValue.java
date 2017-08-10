package algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 从树的根结点开始往下访问一直到叶结点所经过的所有结点形成一条路径。
 * 打印出和与输入整数相等的所有路径。
 */
public class BinaryTreePrintPathEqualValue {

    static private class BSTNode{
        int value;
        BSTNode left;
        BSTNode right;
    }

    static void printPath(List<BSTNode> stack){
        for(BSTNode n : stack){
            System.out.print(n.value +"\t");
        }
        System.out.print("\n");
    }

    static void pre_order_route(BSTNode root, int specValue, List<BSTNode> stack, int preValue){
        if(root == null) return;

        int curValue = preValue + root.value;
        stack.add(root);

        boolean isHaveLeaf = (null == root.left) && (null == root.right);

        if(curValue== specValue && isHaveLeaf){
            printPath(stack);
        }

        if(null != root.left){
            pre_order_route(root.left, specValue, stack, curValue);
        }
        if(null != root.right){
            pre_order_route(root.right, specValue, stack, curValue);
        }
        stack.remove(stack.size()-1);
    }

    static void print_route(BSTNode root, int specValue){
        List<BSTNode> ary = new ArrayList<>();
        pre_order_route(root, specValue, ary, 0);
    }

}
