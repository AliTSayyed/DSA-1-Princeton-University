# 8 Puzzle A* search Algorithim

(Assignment instructions)
1. The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8, plus a blank square. 
The goal is to rearrange the tiles so that they are in row-major order, using as few moves as possible. 
You are permitted to slide tiles either horizontally or vertically into the blank square. 

2. We describe a solution to the 8-puzzle problem that illustrates a general artificial intelligence methodology known as the A* search algorithm.
We define a search node of the game to be a board, the number of moves made to reach the board, and the previous search node.
First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node).
Repeat this procedure until the search node dequeued corresponds to the goal board.
