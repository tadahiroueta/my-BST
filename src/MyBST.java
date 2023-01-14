import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyBST {

    private BSTNode root;

    private class BSTNode {
        Integer val;
        BSTNode left, right;

        public BSTNode(Integer val) {
            this.val = val;
            left = right = null;
        }

        @Override
        public String toString() {
            return val.toString();
        }
    }

    public int size() {
        return size(root);
    }
        
    private int size( BSTNode node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    public void insert(Integer n) { root = insert(n, root); } 

    private BSTNode insert(Integer n, BSTNode node) {
        if (node == null || node.val == n) return new BSTNode(n);

        if (n < node.val) node.left = insert(n, node.left);
        else node.right = insert(n, node.right);

        return node;
    }

    public boolean contains(int n) { return contains(root, n); }

    private boolean contains( BSTNode node, int n) {
        if (node == null) return false;
        if (node.val == n) return true;
        
        return contains(node.left, n) || contains(node.right, n);
    }

    public Integer getMax() { return getMax(root); }

    private Integer getMax(BSTNode node) {
        if (node == null) return Integer.MIN_VALUE;

        return Math.max(node.val, Math.max(getMax(node.left), getMax(node.right))); // if wrong, it's because of the null (change it to 0)
    }

    public Integer getMin() { return getMin(root); }

    private Integer getMin(BSTNode node) {
        if (node == null) return Integer.MAX_VALUE;

        return Math.min(node.val, Math.min(getMin(node.left), getMin(node.right))); // if wrong, it's because of the null (change it to 0)
    }

    public void delete(Integer n) { root = delete(n, root); }

    private BSTNode delete(Integer n, BSTNode node) {
        if (node == null || (node.left == null && node.right == null)) return null;

        if (n < node.val) {
            node.left = delete(n, node.left);
            return node;
        }
        if (n > node.val) {
            node.right = delete(n, node.right);
            return node;
        }

        if (node.left != null && node.right == null) return node.left;
        if (node.left == null && node.right != null) return node.right;

        // node has two children
        node.val = getMin(node.right);
        node.right = delete(node.val, node.right);
        return node;
    }

    public void inOrder() { 
        inOrder(root);
        System.out.println();
     }

    private void inOrder(BSTNode node) {
        if (node == null) return;

        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    public void print() { print(root, 0, new ArrayList<Boolean>(), false); }

    private void print(BSTNode node, int level, List<Boolean> hasLine, boolean isRight) {
        if (node == null) return;

        level++;
        final List<Boolean> rightHasLine = new ArrayList<>(hasLine);
        final List<Boolean> leftHasLine = new ArrayList<>(hasLine);
        if (level > 1) {
            rightHasLine.add(!isRight);
            leftHasLine.add(isRight);
        }

        print(node.right, level, rightHasLine, true);

        for (int i = 0; i < hasLine.size(); i++) System.out.print(hasLine.get(i) ? " │      " : "        ");
        if (level > 1) System.out.print(" " + (isRight ? "/" : "\\") + "----- ");
        System.out.println(node.val);

        print(node.left, level, leftHasLine, false);
    }

    /**
     * Converts a tree to a circular queue. use printList to see. (WILL RENDER ALL NORMAL FUNCTIONS USELESS.)
     * 
     * @return BSTNode the root of the circular queue
     */
    public BSTNode treeToList() { 
        List<BSTNode> list = new ArrayList<BSTNode>();
        treeToList(root, list);

        final int size = list.size();
        for (int i = 0; i < size; i++) {
            BSTNode current = list.get(i), next = list.get((i + 1) % size);
            current.right = next;
            next.left = current;
        }
        return list.get(0);
    }

    private void treeToList(BSTNode node, List<BSTNode> list) {
        if (node == null) return;

        treeToList(node.left, list);
        list.add(node);
        treeToList(node.right, list);
    }

    /**
     * Prints circular queue
     */
    public static void printList(BSTNode listRoot) {
        BSTNode current = listRoot;
        do {
            System.out.print(current.val + " <==> ");
            current = current.right;
        } while (current != listRoot);
        System.out.println();
    }
}