// Don't touch my spaghet ) _\ _ /_ )
public class BST <K extends Comparable<K>,V> {
    class Node {
        K key;
        V value;
        int height;
        Node left, right;
        Node (K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.left = this.right = null;
        }
    }
    Node root;
    private Node temp;
    private boolean fnd;
    public BST() { this.root = null; }
    private int height (Node n) {
        if (n==null) return 0;
        int left = (n.left!=null) ? n.left.height : 0;
        int right = (n.right!=null) ? n.right.height : 0;
        return Math.max(left,right) + 1;
    }
    private Node rot_left (Node parent) {
        Node new_parent = parent.right;
        parent.right = new_parent.left;
        new_parent.left = parent;
        parent.height = height(parent);
        new_parent.height = height(new_parent);
        return new_parent;
    }
    private Node rot_right (Node parent) {
        Node new_parent = parent.left;
        parent.left = new_parent.right;
        new_parent.right = parent;
        parent.height = height(parent);
        new_parent.height = height(new_parent);
        return new_parent;
    }
    private Node re_balance (Node current) {
        current.height = height(current);
        int b_factor = bl_factor(current);
        if (b_factor < -1) {
            if (bl_factor(current.right) == 1)
                current.right = rot_right(current.right);
            current = rot_left(current);
        } else if (b_factor > 1) {
            if (bl_factor(current.left) == -1)
                current.left = rot_left(current.left);
            current = rot_right(current);
        } return current;
    }
    private int bl_factor (Node n) {
        if (n==null) return 0;
        int left = (n.left!=null) ? n.left.height : 0;
        int right = (n.right!=null) ? n.right.height : 0;
        return left-right;
    }
    private Node DFS (K key,V value, Node current) {
        if (current == null) return new Node(key,value);
        if (key.compareTo(current.key) == 0) {
            this.fnd = true;
            current.value = value;
        }
        else if (key.compareTo(current.key) < 0) current.left = DFS(key,value, current.left);
        else current.right = DFS(key,value, current.right);
        return (!fnd) ? re_balance(current) : current;
    }
    private Node DFS2 (K key, Node current) {
        if (!fnd) {
            if (current!=null) {
                if (key.compareTo(current.key) == 0) {
                    fnd = true;
                    if (current.right != null && current.left!=null) {
                        current.left = DFS2(key, current.left);
                        temp.right = current.right;
                        if (current.left != null) temp.left = current.left;
                    }
                    else  temp = (current.left!=null) ? current.left :
                            (current.right !=null) ? current.right : null;
                    current = temp;
                }
                else if (key.compareTo(current.key) < 0) current.left = DFS2(key, current.left);
                else current.right = DFS2(key, current.right);
            }
        } else {
            if (current.right == null) {
                temp = current;
                return null;
            } current.right = DFS2(key, current.right);
        }
        return (fnd && current!=null) ? re_balance(current) : current;
    }
    public void insert (K key, V value) {
        this.fnd = false;
        root = DFS(key, value, root);
    }
    public void delete(K key) {
        this.temp = null; this.fnd = false;
        root = DFS2(key, root);
    }
    public V find (K key) {
        Node temp = root;
        while (temp != null) {
            if (key.compareTo(temp.key) == 0) return temp.value;
            temp = (key.compareTo(temp.key) < 0) ? temp.left : temp.right;
        } return null;
    }
    public void printRange (Node temp, K u, K v) {
        if (temp != null) {
            if (u.compareTo(temp.key) > 0) printRange(temp.right, u, v);
            else if (v.compareTo(temp.key) < 0) printRange(temp.left, u,v);
            else {
                printRange(temp.left, u, v);
                System.out.println(temp.key+" : "+temp.value);
                printRange(temp.right, u, v);
            }
        }
    }
    public void printTree (Node temp, int level) {
        if (temp != null) {
            for (int i = 0; i < level; i++)
                System.out.print('\t');
            System.out.println(level+" ----- ("+temp.key+", "+temp.value+")");
            printTree(temp.left, level+1);
            printTree(temp.right, level+1);
        }
    }
}
