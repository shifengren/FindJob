package algorithm.tree;

/**
 * Implementation RB-Tree for learning
 *
 * http://www.cs.princeton.edu/~rs/talks/LLRB/
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        boolean color;

        Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public Node get(Node root, Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;
            else if (cmp < 0) x = x.left;
            else x = x.right;
        }
        return null;
    }

    public void put(Key key, Value val) {

    }

    public Node min(Node root) {
        Node x = root;
        while (x != null) {
            x = x.left;
        }
        if (x == null) return null;
        else return x;
    }

    public void insert(Key key, Value val){
        root = insert(root, key, val);
        root.color = BLACK;
    }

    private Node insert(Node h, Key key, Value val) {
        if(h==null)
            return new Node(key, val, RED); // insert a red node at bottom

        // split 4-nodes one the way down;
        if(isRed(h.left) && isRed(h.right))
            colorFlip(h);

        int cmp = key.compareTo(h.key);
        if(cmp ==0) h.val = val;
        else if(cmp < 0)
            h.left = insert(h.left, key, val);
        else h.right = insert(h.right, key, val);

        // fix right-leaning reds on the way up
        if(isRed(h.right))
            h = rotateLeft(h);

        // fix two reds in a row on the way up
        if(isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);

        // split 4-nodes one the way up;
//        if(isRed(h.left) && isRed(h.right))
//            colorFlip(h);

        return h;

    }

    private Node fixUp(Node h){
        // rotate-left right-leaning reds
        if(isRed(h.right))
            h = rotateLeft(h);

        // rotate-right red-red paris;
        if(isRed(h.left.left) && isRed(h.left))
            h = rotateRight(h);

        //split 4-nodes
        if(isRed(h.left) && isRed(h.right))
            h = colorFlip(h);
        return h;
    }

    public void deleteMin(){
        root = deleteMin(root);
        root.color = BLACK;
    }
    private Node deleteMin(Node h){
        if(h.left == null) return null;

        if(!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);

        return fixUp(h);
    }

    public void deleteMax(){
        root = deleteMax(root);
        root.color = BLACK;
    }
    private Node deleteMax(Node h){
        if(isRed(h.left))
            h = rotateRight(h);

        if(h.right ==null)
            return null;

        if(!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.left = deleteMax(h.left);
        return fixUp(h);
    }
    private Node moveRedRight(Node h){
        colorFlip(h);
        if(isRed(h.left.left)){
            h = rotateRight(h);
            colorFlip(h);
        }
        return h;
    }

    private Node moveRedLeft(Node h){
        colorFlip(h);
        if(isRed(h.right.left)){
            h.right = rotateRight(h.right);
            h = rotateRight(h);
            colorFlip(h);
        }
    }


    private void delete(Key key){
        root = delete(root, key);
        root.color = BLACK;
    }
    private Node delete(Node h, Key key){
        if(key.compareTo(h.key) <0){
            if(!isRed(h.left) && !isRed(h.left.left)){
                h = moveRedRight(h);
            }
            h.left = delete(h.left, key);
        }else{
            if(isRed(h.left))
                h = rotateRight(h);
            if(key.compareTo(h.key) ==0 && h.right==null)
                return  null;
            if(!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if(key.compareTo(h.key)==0){
                h.val = get(h.right, min(h.right).key).val;
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return fixUp(h);
    }

    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        return x;
    }

    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        return x;
    }

    // flip the colors of the three nodes
    private Node colorFlip(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
        return h;
    }

    public static void main(String[] args) {

    }
}
