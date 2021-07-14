// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the "dictionary" class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)

    public int Allocate(int blockSize) {

        // no meaning of allocating memory of size <=0.

        if(blockSize <= 0){
            return -1;
        }

        Dictionary freeMemory = this.freeBlk.Find(blockSize,false);
        if(freeMemory == null){
            //System.out.println("Not found");
            //printInorder(this.freeBlk);
            //printInorder(this.allocBlk);
            return -1;
        }
        this.freeBlk.Delete(freeMemory);
        if(freeMemory.size > blockSize){
            int newAddress = freeMemory.address + blockSize;
            int newSize = freeMemory.size - blockSize;
            this.freeBlk.Insert(newAddress,newSize,newSize);
        }

        this.allocBlk.Insert(freeMemory.address,blockSize,freeMemory.address);


        //printInorder(this.freeBlk);
        //printInorder(this.allocBlk);

        return freeMemory.address;
    } 

    // return 0 if successful, -1 otherwise
    public int Free(int startAddr) {

        // no meaning of freeing memory with negative address.
        if(startAddr < 0){
            return -1;
        }

        Dictionary allocatedMemory = this.allocBlk.Find(startAddr,true);
        if(allocatedMemory == null){
            //System.out.println("Not found");
            //printInorder(this.freeBlk);
            //printInorder(this.allocBlk);
            return -1;
        }
        this.freeBlk.Insert(allocatedMemory.address,allocatedMemory.size,allocatedMemory.size);
        this.allocBlk.Delete(allocatedMemory);

        //printInorder(this.freeBlk);
        //printInorder(this.allocBlk);

        return 0;
    }


    // print BSTree inorder;
    public static void printInorder(Dictionary blk){
        Dictionary temp = blk.getFirst();
        System.out.println("   ");
        System.out.println("   ");
        while(temp != null){
            System.out.println(temp.address + "  " + temp.size + "  " + temp.key);
            temp = temp.getNext();
        }
        System.out.println("   ");
        System.out.println("   ");
    }
}