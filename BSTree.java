// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }


    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree newNode = new BSTree(address,size,key);
        BSTree temp = this.getRoot();

        // If tree is empty then this will be pointing to sentinal node.
        if(temp == null){
            this.right = newNode;
            newNode.parent = this;
            return newNode; 
        } 

        insert_helper(temp,newNode);
        return newNode;
    }


    public boolean Delete(Dictionary e)
    { 
        // if e is null then nothing is to be deleted.
        if(e == null){
            return false;
        }

        BSTree temp = this.getRoot();

        if(temp == null){
            return false;
        }

        return this.delete_helper(temp,temp.parent,e);
    }

        
    public BSTree Find(int key, boolean exact)
    { 
        // in case of exact == true, returning smallest element with key = k.
        if(exact == true){
            BSTree res = null;
            BSTree temp = this.getRoot();
            while(temp != null){
                if(temp.key == key){
                    res = temp;
                }

                if(key > temp.key){
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
            BSTree res = null;
            BSTree temp = this.getRoot();
            while(temp != null){
                if(temp.key >= key){
                    res = temp;
                    temp = temp.left;
                }
                else{
                    temp = temp.right;
                }
            }
            return res;
        }
    }


    public BSTree getFirst()
    { 
        BSTree temp = this.getRoot();
        if(temp == null){
            return null;
        }

        while(temp.left != null){
            temp = temp.left;
        }
        return temp;
    }


    public BSTree getNext()
    { 
        // empty tree;
        if(this.parent == null){
            return null;
        }

        BSTree temp = this;
        if(temp.right == null){
            BSTree parent = temp.parent;
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

    //O(n) time and O(n) space(recursion call stack space).
    public boolean sanity()
    { 
        // check if cycle exists containing the sentinal node, in case there is any other 
        // cycle or some other error then the function may return true or false, but in case of 
        // existance of cycle containing sentinal node it will surely return true.
        if(this.cycleExists() == true){
            return false;
        }

        BSTree head = this;
        while(head.parent != null){
            head = head.parent;
        }

        //head is sentinal node, so check its properties;
        if(head.address != -1 || head.size != -1 || head.key != -1){
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
        // and left and right child should not be same
        // done using dfs.

        if(ErrorinDfs(head) == true){
            return false;
        }

        // after reaching here in code , it can be said that the BSTree is structurally valid.
        // So it would return correct result of getFirst and getNext function according to the structure.
        // Now traversing the tree in inorder traversal using getFirst and getNext should be a sorted order
        // for binary search property to be valid.

        BSTree curr = head.getFirst();
        BSTree prev = null;
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



    /***** Helper Functions *****/

    private BSTree getRoot(){
        BSTree temp = this;
        while(temp.parent != null){
            temp = temp.parent;
        }
        return temp.right;
    }



    private static BSTree insert_helper(BSTree root,BSTree node){
        if(root == null){
            return node;
        }

        if(compare(root,node)){
            root.right = insert_helper(root.right,node);
            root.right.parent = root;
        }
        else{
            root.left = insert_helper(root.left,node);
            root.left.parent = root;
        }

        return root;
    }



    private boolean delete_helper(BSTree root, BSTree parent, Dictionary e){
        if(root == null){
            return false;
        }

        if(e.key == root.key && e.address == root.address && e.size == root.size){
            //if root is leaf node
            if(root.left == null && root.right == null){

                if(parent.left == root){
                    parent.left = null;
                }
                else{
                    parent.right = null;
                }
                root.parent = null;

                if(this == root){
                    replace(root,parent);
                }
            }

            //if root have only right child
            else if(root.left == null){
                if(parent.left == root){
                    parent.left = root.right;
                    parent.left.parent = parent;
                }
                else{
                    parent.right = root.right;
                    parent.right.parent = parent;
                }
                root.parent = null;
                root.right = null;

                if(this == root){
                    replace(root,parent);
                }
            }

            //if root have only left child
            else if(root.right == null){
                if(parent.left == root){
                    parent.left = root.left;
                    parent.left.parent = parent;
                }
                else{
                    parent.right = root.left;
                    parent.right.parent = parent;
                }
                root.parent = null;
                root.left = null;

                if(this == root){
                    replace(root,parent);
                }
            }

            // if root have both child
            else{
                BSTree replacement = root.getNext();
                pointerReplacement(root,replacement);

                Dictionary dictOfReplacement = root;
                this.delete_helper(root,root.parent,dictOfReplacement);
            }

            return true;
        }
        else if(compare(root,e)){
            return this.delete_helper(root.right,root,e);
        }
        else{
            return this.delete_helper(root.left,root,e);
        }
    }

    private static void replace(BSTree temp,BSTree node){

        temp.key = node.key;
        temp.address = node.address;
        temp.size = node.size;

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


    private void pointerReplacement(BSTree temp, BSTree node){

        if(node.parent == temp){
            BSTree parentTemp = temp.parent;
            BSTree leftTemp = temp.left;
            BSTree rightTemp = temp.right;

            BSTree parentNode = node.parent;
            BSTree leftNode = node.left;
            BSTree rightNode = node.right;

            node.parent = parentTemp;
            node.left = leftTemp;
            node.right = temp;

            if(node.parent != null){
                if(node.parent.left == temp){
                    node.parent.left = node;
                }
                else{
                    node.parent.right = node;
                }
            }
            if(node.left != null){
                node.left.parent = node;
            }

            temp.parent = node;
            temp.left = leftNode;
            temp.right = rightNode;

            if(temp.left != null){
                temp.left.parent = temp;
            }
            if(temp.right != null){
                temp.right.parent = temp;
            }

            return ;
        }

        BSTree parentTemp = temp.parent;
        BSTree leftTemp = temp.left;
        BSTree rightTemp = temp.right;

        temp.parent = node.parent;
        temp.left = node.left;
        temp.right = node.right;

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

        node.parent = parentTemp;
        node.left = leftTemp;
        node.right = rightTemp;

        if(node.parent != null){
            if(node.parent.left == temp){
                node.parent.left = node;
            }
            else{
                node.parent.right = node;
            }
        }

        if(node.left != null){
            node.left.parent = node;
        }
        if(node.right != null){
            node.right.parent = node;
        }
    }



    private boolean cycleExists(){
        BSTree slow = this;
        BSTree fast = slow;

        while(fast != null && fast.parent != null){
            fast=fast.parent.parent;
            slow=slow.parent;
            if(slow==fast){
                return true;
            }
        }

        return false;
    }



    private boolean ErrorinDfs(BSTree root){
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

        if(ErrorinDfs(root.left)){
            return true;
        }
        return ErrorinDfs(root.right);

    }



    // return true if p1<p2.
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


}


 


