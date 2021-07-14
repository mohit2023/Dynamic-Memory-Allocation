// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {

        Dictionary AddressBasedFMB;
        if(this.type == 1){
            return ;
        }
        else if(this.type == 2){
            AddressBasedFMB = new BSTree();
        }
        else{
            AddressBasedFMB = new AVLTree();
        }

        Dictionary iterator;
        iterator = this.freeBlk.getFirst();
        while(iterator != null){
            AddressBasedFMB.Insert(iterator.address,iterator.size,iterator.address);
            iterator = iterator.getNext();
        }

        iterator = AddressBasedFMB.getFirst();
        int prevSize = 0;
        int prevAddress = -1;
        Dictionary prevIterator = null;
        boolean IsInContinuity = false;

        while(iterator != null){
            if(iterator.address == prevAddress){
                this.freeBlk.Delete(prevIterator);
                prevSize = prevSize + iterator.size;
                prevAddress = iterator.address + iterator.size;
                prevIterator = iterator;
                IsInContinuity = true;
                iterator = iterator.getNext();
            }
            else{
                if(IsInContinuity == true){
                    this.freeBlk.Delete(prevIterator);
                    this.freeBlk.Insert(prevAddress - prevSize,prevSize,prevSize);
                }
                prevSize = iterator.size;
                prevAddress = iterator.address + iterator.size;
                prevIterator = iterator;
                IsInContinuity = false;
                iterator = iterator.getNext();
            }
            prevIterator.key = prevIterator.size;
        }
        if(IsInContinuity == true){
            this.freeBlk.Delete(prevIterator);
            this.freeBlk.Insert(prevAddress - prevSize,prevSize,prevSize);
        }

        //printInorder(this.freeBlk);
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