# K Dimesnional Trees

Write a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1) 
using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) 
and nearest-neighbor search (find a closest point to a query point). 

Brute-force implementation. 
Write a mutable data type using a red-black BST PointSET.java that represents a set of points in the unit square.
   
2d-tree implementation. 
Write a mutable data type KdTree.java that uses a 2d-tree to implement the same API (but replace PointSET with KdTree).
A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes,
using the x- and y-coordinates of the points as keys in strictly alternating sequence.
