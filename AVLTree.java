// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree newNode = new AVLTree(address,size,key);
        AVLTree temp = this.getRoot();

        // If tree is empty then this will be pointing to sentinal node.
        if(temp == null){
            this.right = newNode;
            newNode.parent = this;
            return newNode; 
        } 
        AVLTree sentinalNode = temp.parent;
        temp = insert_helper(temp,newNode);
        sentinalNode.right = temp;
        sentinalNode.right.parent = sentinalNode;
        return newNode;
    }

    public boolean Delete(Dictionary e)
    {
        // if e is null then nothing is to be deleted.
        if(e == null){
            return false;
        }

        AVLTree temp = this.getRoot();

        if(temp == null){
            return false;
        }

        MutableBoolean result = new MutableBoolean();
        MutableBoolean deletedNodeIsthis = new MutableBoolean();
        AVLTree sentinalNode = temp.parent;
        temp = this.delete_helper(temp,e,result,deletedNodeIsthis);
        sentinalNode.right = temp;
        if(sentinalNode.right != null){
            sentinalNode.right.parent = sentinalNode;
        }

        if(deletedNodeIsthis.getValue() == true){
            replace(this,sentinalNode);
        }

        return result.getValue();
    }
        
    public AVLTree Find(int k, boolean exact)
    { 
        // in case of exact == true, returning smallest element with key = k.
        if(exact == true){
            AVLTree res = null;
            AVLTree temp = this.getRoot();
            while(temp != null){
                if(temp.key == k){
                    res = temp;
                }

                if(k > temp.key){
                    temp = temp.right;
                }
                else{
                    temp = temp.left;
                }
            }
            return res; 
        }
        // in case of exact == false, returning smallest element with key>=k. 
        else{
            AVLTree res = null;
            AVLTree temp = this.getRoot();
            while(temp != null){
                if(temp.key >= k){
                    res = temp;
                    temp = temp.left;
                }
                else{
                    temp = temp.right;
                }
            }
            if(res == null){
                return null;
            }
            AVLTree result = new AVLTree(res.address,res.size,res.key);
            return result;
        }
    }

    public AVLTree getFirst()
    { 
        AVLTree temp = this.getRoot();
        if(temp == null){
            return null;
        }

        while(temp.left != null){
            temp = temp.left;
        }
        return temp;
    }

    public AVLTree getNext()
    {
        // empty tree;
        if(this.parent == null){
            return null;
        }

        AVLTree temp = this;
        if(temp.right == null){
            AVLTree parent = temp.parent;
            while(parent.parent != null){
                if(parent.left == temp){
                    return parent;
                }
                temp = parent;
                parent = temp.parent;
            }
            return null;
        }
        else{
            temp = temp.right;
            while(temp.left != null){
                temp = temp.left;
            }
            return temp;
        }
    }


    public boolean sanity()
    { 
        // check if cycle exists containing the sentinal node, in case there is any other 
        // cycle or some other error then the function may return true or false, but in case of 
        // existance of cycle containing sentinal node it will surely return true.
        if(this.cycleExists() == true){
            return false;
        }

        AVLTree head = this;
        while(head.parent != null){
            head = head.parent;
        }

        //head is sentinal node, so check its properties;
        if(head.address != -1 || head.size != -1 || head.key != -1 || head.height != 0){
            return false;
        }
        if(head.left != null){
            return false;
        }
        if(head.right != null && head.right.parent != head){
            return false;
        }

        // check for the pointers of a node should point to a node which in turn should point to the node
        // using correct pointer. Also check if node does not point to itself 
        // and left and right child should not be same.
        // Also checks for the height balancing property at each node.
        // done using dfs.

        if(ErrorinDfs(head) == true){
            return false;
        }

        // after reaching here in code , it can be said that the AVLTree is structurally valid.
        // So it would return correct result of getFirst and getNext function according to the structure.
        // Now traversing the tree in inorder traversal using getFirst and getNext should be a sorted order
        // for binary search property to be valid.

        AVLTree curr = head.getFirst();
        AVLTree prev = null;
        while(curr != null){

            if(prev != null){
                if(compare(curr,prev)){
                    return false;
                }
            }

            prev = curr;
            curr = curr.getNext();
        }  

        return true;
    }






    /**** Helper Function ****/

    private AVLTree getRoot(){
        AVLTree temp = this;
        while(temp.parent != null){
            temp = temp.parent;
        }
        return temp.right;
    }


    private static AVLTree insert_helper(AVLTree root,AVLTree node){
        if(root == null){
            return node;
        }

        if(compare(root,node)){
            root.right = insert_helper(root.right,node);
            if(root.right != null){
                root.right.parent = root;    
            }
        }
        else{
            root.left = insert_helper(root.left,node);
            if(root.left != null){
                root.left.parent = root;    
            }
        }

        root.height = 1 + max(height(root.left),height(root.right));

        int diff = root.heightDiff();
        if(diff > 1){
            // left right case
            if(compare(root.left,node)){
                root.left = leftRotate(root.left);
                if(root.left != null){
                    root.left.parent = root;    
                }
                return rightRotate(root);
            }
            // left left case
            else{
                return rightRotate(root);
            }
        }
        else if(diff < -1){
            // right right case
            if(compare(root.right,node)){
                return leftRotate(root);
            }
            // right left case
            else{
                root.right = rightRotate(root.right);
                if(root.right != null){
                    root.right.parent = root;    
                }
                return leftRotate(root);
            }
        }

        return root;
    }



    private AVLTree delete_helper(AVLTree root, Dictionary e,MutableBoolean result, MutableBoolean deletedNodeIsthis){
        if(root == null){
            return null;
        }

        if(e.key == root.key && e.address == root.address && e.size == root.size){
            //if root is leaf node
            if(root.left == null && root.right == null){
                root.parent = null;

                if(this == root){
                    deletedNodeIsthis.setValueTrue();
                }

                result.setValueTrue();
                return null;
            }

            //if root have only right child
            else if(root.left == null){
                root.parent = null;
                AVLTree temp = root.right;
                root.right = null;

                if(this == root){
                    deletedNodeIsthis.setValueTrue();
                }

                result.setValueTrue();
                return temp;
            }

            //if root have only left child
            else if(root.right == null){
                root.parent = null;
                AVLTree temp = root.left;
                root.left = null;

                if(this == root){
                    deletedNodeIsthis.setValueTrue();
                }

                result.setValueTrue();
                return temp;
            }

            // if root have both child
            else{
                AVLTree replacement = root.getNext();
                root.address = replacement.address;
                root.size = replacement.size;
                root.key = replacement.key;
                Dictionary dictOfReplacement = replacement;
                root.right = this.delete_helper(root.right,dictOfReplacement,result,deletedNodeIsthis);
                if(root.right != null){
                    root.right.parent = root;
                }
            }

            result.setValueTrue();
        }
        else if(compare(root,e)){
            root.right = this.delete_helper(root.right,e,result,deletedNodeIsthis);
            if(root.right != null){
                root.right.parent = root;
            }
        }
        else{
            root.left = this.delete_helper(root.left,e,result,deletedNodeIsthis);
            if(root.left != null){
                root.left.parent = root;
            }
        }


        root.height = 1 + max(height(root.left),height(root.right));
        int diff = root.heightDiff();

        if(diff > 1){
            // left left case
            if(root.left.heightDiff() >= 0){
                return rightRotate(root);
            }
            // left right case
            else{
                root.left = leftRotate(root.left);
                if(root.left != null){
                    root.left.parent = root;
                }
                return rightRotate(root);
            }
        }
        else if(diff < -1){
            // right right case
            if(root.right.heightDiff() <= 0){
                return leftRotate(root);
            }
            // right left case
            else{
                root.right = rightRotate(root.right);
                if(root.right != null){
                    root.right.parent = root;    
                }
                return leftRotate(root);
            }
        }

        return root;

    }

    private static void replace(AVLTree temp,AVLTree node){

        temp.key = node.key;
        temp.address = node.address;
        temp.size = node.size;
        temp.height = node.height;

        temp.parent = node.parent;
        temp.left = node.left;
        temp.right = node.right;

        node.parent = null;
        node.left = null;
        node.right = null;

        if(temp.parent != null){
            if(temp.parent.left == node){
                temp.parent.left = temp;
            }
            else{
                temp.parent.right = temp;
            }
        }

        if(temp.left != null){
            temp.left.parent = temp;
        }
        if(temp.right != null){
            temp.right.parent = temp;
        }

    }




    private boolean cycleExists(){
        AVLTree slow = this;
        AVLTree fast = slow;

        while(fast != null && fast.parent != null){
            fast=fast.parent.parent;
            slow=slow.parent;
            if(slow==fast){
                return true;
            }
        }

        return false;
    }



    private boolean ErrorinDfs(AVLTree root){
        if(root == null){
            return false;
        }

        // parent pointer will already be check in the previous call in this recursive function.

        if(root.left != null && root.left.parent != root){
            return true;
        }
        if(root.right != null && root.right.parent != root){
            return true;
        }

        if(root.left != null && root.right != null){
            if(root.left == root.right){
                return true;
            }
        }

        int diff = root.heightDiff();
        if(diff > 1 || diff < -1){
            return true;
        }

        if(root.height != 1+max(height(root.left),height(root.right))){
            return true;
        }

        if(ErrorinDfs(root.left)){
            return true;
        }
        return ErrorinDfs(root.right);

    }




    private int heightDiff(){
        return height(this.left) - height(this.right);
    }


    private static int height(AVLTree node){
        if(node == null){
            return -1;
        }
        return node.height;
    }


    private static AVLTree leftRotate(AVLTree root){
        AVLTree r = root.right;
        AVLTree rl = r.left;

        r.left = root;
        if(r.left != null){
            r.left.parent = r;
        }
        root.right = rl;
        if(root.right != null){
            root.right.parent = root;
        }
        root.height = 1 + max(height(root.left),height(root.right));
        r.height = 1 + max(height(r.left),height(r.right));

        return r;
    }

    private static AVLTree rightRotate(AVLTree root){
        AVLTree l = root.left;
        AVLTree lr = l.right;

        l.right = root;
        if(l.right != null){
            l.right.parent = l;
        }
        root.left = lr;
        if(root.left != null){
            root.left.parent = root;
        }

        root.height = 1 + max(height(root.left),height(root.right));
        l.height = 1 + max(height(l.left),height(l.right));

        return l;
    }



    private static int max(int a,int b){
        if(a>=b){
            return a;
        }
        else{
            return b;
        }
    }


    // return true if p1<p2
    private static boolean compare(Dictionary p1, Dictionary p2){
        if(p1.key < p2.key){
            return true;
        }
        else if(p1.key == p2.key){
            if(p1.address < p2.address){
                return true;
            }
        }
        return false;
    }


    private class MutableBoolean {
        boolean b;
        MutableBoolean(){
            b=false;
        }

        private void setValueTrue(){
            b = true;
        }

        private boolean getValue(){
            return b;
        }
    }

}


