package EightPuzzle;
// Implement A* search to solve n-by-n slider puzzles and create an immutable data type.

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node solvedBoard; // store solution board
    private boolean solvable; // store if board is solvable (default value is false)

    // a search node comprises an initial board, how many moves the board has made, and its previous node
    private class Node implements Comparable<Node> {
        private Board board; // store the input board
        private int moves; // keep track of how many moves have been made
        private Node previous; // store the previous node (null if first node)
        private int priority; // priority is used to compare nodes, it's the moves + manhattan amount.

        public Node(Board initial, int moves, Node previous) {
            this.board = initial;
            this.previous = previous;
            this.moves = moves;
            this.priority = this.moves + board.manhattan();
        }

        // returns -1 if less, 0 if equal, and +1 if greater than
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // first check if board is non null
        if (initial == null) throw new IllegalArgumentException("No board entered!");
        // next check if board is even solvable
        if (!isSolvable()) throw new IllegalArgumentException("Board is not solvable");

        // if board is solvable, proceed with the A*search method
        Node node = new Node(initial, 0, null);
        Node twinNode = new Node(initial.twin(), 0, null);
        // create two PQ, one for the original board and one for the twin board. If a twin board is solvable, the original board is not.
        MinPQ<Node> aSearch = new MinPQ<>();
        MinPQ<Node> twinASearch = new MinPQ<>();
        aSearch.insert(node);
        twinASearch.insert(twinNode);

        while (true) {
            // add remove lowest priority node from PQ
            Node currentNode = aSearch.delMin();
            Node currentTwinNode = twinASearch.delMin();

            // if the board of the node is complete break out of loop
            if (currentNode.board.isGoal()) {
                this.solvedBoard = currentNode;
                break;
            }

            // if the board of the twin node is solvable, then the original board is unsolvable
            if (currentTwinNode.board.isGoal()) {
                this.solvable = true;
                break;
            }

            // add each neighbor board to the PQ making sure not to add any potential duplicates (grandparent node board)
            neighborSearch(currentNode, aSearch);
            neighborSearch(currentTwinNode, twinASearch);
        }
    }

    // method to create neighbor boards (children of the node) and add them back to the PQ
    private void neighborSearch(Node currentNode, MinPQ<Node> queue) {
        for (Board b : currentNode.board.neighbors()) {
            Node neighborNode = new Node(b, currentNode.moves + 1, currentNode);
            if (currentNode.previous == null || !neighborNode.board.equals(currentNode.previous.board)) {
                queue.insert(neighborNode);
            }
        }
    }

    // return if the board is solvable
    public boolean isSolvable() {
        return !solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return solvedBoard.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable. Store them in a stack for a LIFO output.
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> solution = new Stack<>();
        Node currentNode = solvedBoard;
        while (currentNode != null) {
            solution.push(currentNode.board);
            currentNode = currentNode.previous;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}
