package algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *  输入一棵二元查找树，将该二元查找树转换成一个排序的双向链表。

    要求不能创建任何新的结点，只调整指针的指向。

            10

         /      \

         6       14

       /   \    /   \

      4   8  12    16

      转换成双向链表
      4=6=8=10=12=14=16。
 */
public class BinaryTreeToSortedDoubleLinkedList {

    /**
     *
     * @param root
     * @return
     */
    static BSTree.BSTreeNode toDBList(BSTree.BSTreeNode root){

        if(root==null || (root.left==null&&root.right==null))
            return root;

        toDBList(root.left);
        BSTree.BSTreeNode pleft = root.left;
        while(pleft.right != null){
            pleft = pleft.right;
        }
        root.left = pleft;
        pleft.right = root;

        toDBList(root.right);
        BSTree.BSTreeNode pright = root.right;
        while(pright.left!=null){
            pright = pright.left;
        }
        root.right = pright;
        pright.left = root;

        BSTree.BSTreeNode p = root;
        while(null != p.left){
            p = p.left;
        }
        return p;
    }

    public static void main(String[] args){
        List<Integer> ary = new ArrayList<>();
        ary.add(10);
        ary.add(6); ary.add(14);
        ary.add(4); ary.add(8); ary.add(12); ary.add(16);

        BSTree bst = new BSTree();
        bst.buildBSTree(ary);

        bst.inOrder();
        System.out.println();

        BSTree.BSTreeNode p = toDBList(bst.root);

        while (p.right!=null){
            System.out.print(p.value +"=");
            p = p.right;
        }
        System.out.println(p.value + "\nDone!");
    }
}


class BSTree{

    static class BSTreeNode{
        int value;
        BSTreeNode left;
        BSTreeNode right;

        public BSTreeNode(int val){
            value = val;
        }

    }

    // data member
    public BSTreeNode root = null;

    public BSTree(int val){
        root = new BSTreeNode(val);
    }
    public BSTree(){}

    public void buildBSTree(List<Integer> list){

        for(Integer ele : list) {
            insert(ele);
        }
    }

    public void insert(int ele){

        BSTreeNode root = this.root;
        if(root == null){
            this.root = new BSTreeNode(ele);
            return;
        }

        while(root != null){
            if(root.value < ele){
                if (root.right==null){
                    root.right = new BSTreeNode(ele);
                    break;
                }
                root = root.right;
            }else{
                if(root.left ==null){
                    root.left = new BSTreeNode(ele);
                    break;
                }
                root = root.left;
            }
        }
    }

    public int maxElement(){
        BSTreeNode root = this.root;
        while(root.right!=null){
            root = root.right;
        }
        return root.value;
    }

    public int minElement(){
        BSTreeNode root = this.root;
        while(root.left!=null){
            root = root.left;
        }
        return root.value;
    }

    public void inOrder(){
        BSTreeNode root = this.root;
        if(root!=null)
            inOrder_(root);
    }
    private void inOrder_(BSTreeNode root){

        if(root.left!=null)
            inOrder_(root.left);

        System.out.print(root.value+", ");

        if(root.right!=null)
            inOrder_(root.right);

    }

}