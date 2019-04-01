# how to run

the project use `sbt` to build and assemble an fat jar to run the program.

with assembled jar:
```
java -jar treeflip-assembly-0.1.jar sample
```

`sample` is the input to the program:

```
[[0,0,1,0,0]]
[[0,2,0,3,0]]
[[4,0,5,0,0]]
[[0,6,0,7,0]]
```

here is result:

```
guixin@Guixins-MacBook-Pro ~/scala $ java -jar treeflip-assembly-0.1.jar treeflip/sample
Here is what we have loaded:
[[0,0,1,0,0]]
[[0,2,0,3,0]]
[[4,0,5,0,0]]
[[0,6,0,7,0]]

Please choose what to do next
 1. print the tree
 2. flip tree
 3. exit
 your input: 1
    1  
   / \ 
  2   3
 / \   
4   5  
 \   \ 
  6   7

Please choose what to do next
 1. print the tree
 2. flip tree
 3. exit
 your input: 2
  1    
 / \   
3   2  
   / \ 
  5   4
 /   / 
7   6  

Please choose what to do next
 1. print the tree
 2. flip tree
 3. exit
 your input: 3
```