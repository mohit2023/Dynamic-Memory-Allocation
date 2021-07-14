# Dynamic-Memory-Allocation
(COL106 Assignment)

Application of Linked Lists, Binary Search Tree and AVL Trees to create a **Dynamic Memory Allocation System**\
assignment.pdf describes the problem statement.

## Execution
To compile your .java files:

```make all```

To remove the generated .class files:

```make clean```

To run code:

```run.sh {input_file} {output_file}```

Example:
```run.sh input.in output.out```

In the case any argument is missing, console is used for input or output.


## Format of test file

number of test cases

size of first test case

number of commands in first test case

command1

command2

...

size for another test case

number of commands for another test case

command1

command2

...


Following is the format for commands required (Defragment will always be called with 0 as the argument):

```Allocate Size```

```Free Address```

```Defragment 0```

One sample test file test.in is attached alongside
