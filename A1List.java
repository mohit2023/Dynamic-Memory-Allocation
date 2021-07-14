// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        //Insertion after tail not possible.
        if(this.isTail()){
            return null;
        }

        A1List newNode = new A1List(address,size,key);
        newNode.next = this.next;
        newNode.prev = this;
        this.next.prev = newNode;
        this.next = newNode;
        return newNode;
    }

    public boolean Delete(Dictionary d) 
    {   
        // element send to be deleted is null.
        if(d == null){
            return false;
        }

        A1List temp = this.getFirst();
        while(temp != null){
            if(temp.key == d.key){
                if(temp.address == d.address){
                    if(temp.size == d.size){

                        if(this == temp){
                            this.key = this.prev.key;
                            this.address = this.prev.address;
                            this.size = this.prev.size;
                            temp = this.prev;
                            this.prev = temp.prev;
                            if(this.prev != null){
                                this.prev.next = this;
                            }
                            temp.next = null;
                            temp.prev = null;
                            return true;
                        }
                        temp.prev.next = temp.next;
                        temp.next.prev = temp.prev;
                        temp.prev = null;
                        temp.next = null;
                        return true;
                    }
                }
            }
            temp = temp.getNext();
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    {
        A1List temp = this.getFirst();
        if(exact == true){
            while(temp != null){
                if(temp.key == k){
                    return temp;
                }
                temp = temp.getNext();
            }
        }
        else{
            while(temp != null){
                if(temp.key >= k){
                    return temp;
                }
                temp = temp.getNext();
            }
        }

        return null;
    }

    public A1List getFirst()
    {
        //Empty list.
        if(this.isEmpty() == true){
            return null;
        }
        // getFirst() called from head node.
        if(this.isHead()){
            return this.next;
        }
        A1List temp =this;
        while(temp.prev.isHead() != true){
            temp = temp.prev;
        }
        return temp;
    }
    
    public A1List getNext() 
    {   
        //Empty list.
        if(this.isEmpty() == true){
            return null;
        }
        //Calling getNext() from tail node.
        if(this.isTail()){
            return null;
        }
        //Calling getNext() from node previous to tail node.
        if(this.next.isTail() == true){
            return null;
        }

        return this.next;
    }

    public boolean sanity()
    {
        // this==null is not possible because call can not be made on 
        // sanity function from a null value of A1 list instance.

        //next and prev of the node being null.
        if(this.next == null && this.prev == null){
            return false;
        }

        // the function checks for cycles that are created when the next of tail node(last node)
        // points to head node instead of null and prev of head node(first node) points to tail
        // instead of head.
        // any other cycle if exists would be caught when check for 
        // node.next.prev==node or node.prev.next==node will be done.
        if(this.CycleExists()){
            return false;
        }

        A1List temp = this;

        A1List head;
        A1List tail;

        // check that node.next.prev==node while going in forward direction from this pointer
        // and check for node.next==node or node.prev==node also.
        while(temp.next != null){
            if(temp.next == temp || temp.prev == temp){
                return false;
            }
            if(temp.next.prev != temp){
                return false;
            }
            temp = temp.next;
        }
        tail = temp;

        temp = this;

        // check that node.prev.next==node while going in backward direction from this pointer
        // and check for node.next==node or node.prev==node also.
        while(temp.prev != null){
            if(temp.next == temp || temp.prev == temp){
                return false;
            }
            if(temp.prev.next != temp){
                return false;
            }
            temp = temp.prev;
        }
        head = temp;

        // checks if head or tail sentinal node are not having values -1,-1,-1 
        // for key,address and size.
        if(head.key != -1 || head.address != -1 || head.size != -1){
            return false;
        }
        if(tail.key != -1 || tail.address != -1 || tail.size != -1){
            return false;
        }

        return true;
    }


    /**** Helper Functions ****/


    private boolean isHead(){
        if(this.prev == null){
            return true;
        }
        return false;
    }

    private boolean isTail(){
        if(this.next == null){
            return true;
        }
        return false;
    }

    private boolean checkSentinal(){
        if(this.isHead() || this.isTail()){
            return true;
        }
        return false;
    }

    private boolean isEmpty(){
        //If node is not sentinal node than list cant be empty.
        if(this.checkSentinal() == false){
            return false;
        }
        //Current node is head node.
        if(this.isHead() && this.next.checkSentinal() == false){
            return false;
        }
        //Current node is tail node.
        if(this.isTail() && this.prev.checkSentinal() == false){
            return false;
        }

        return true;
    }



    private boolean CycleExists(){
        A1List slow = this;
        A1List fast = slow;

        while(fast != null && fast.next != null){
            fast=fast.next.next;
            slow=slow.next;
            if(slow==fast){
                return true;
            }
        }

        return false;
    }

}


