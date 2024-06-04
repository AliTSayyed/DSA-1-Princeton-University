package EightPuzzle;
// Create a data type that models an n-by-n board with sliding tiles.

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int[][] tiles; // copy the input array into a new data type (to prevent changing the original array)

    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length; // obtain dimension of the n * n board
        this.tiles = new int[n][n];

        // create a defensive (immutable) copy of the initial board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    @Override
    // string representation of this board, use a string builder to display values in a grid format
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : this.tiles) {
            for (int tile : row) {
                sb.append(tile).append(" ");
            }
            sb.append("\n");
        }
        return dimension() + "\n" + sb;
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int n = this.tiles.length;
        int count = 0;
        int index = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Ignore the last tile (which is supposed to be the blank space or 0)
                if (i == n - 1 && j == n - 1) {
                    continue;
                }
                // Check if the current tile is not in the correct position
                if (this.tiles[i][j] != index) {
                    count++;
                }
                index++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        int n = this.tiles.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = this.tiles[i][j];
                if (value != 0) { // Skip the blank space (0)
                    int goalRow = (value - 1) / n; // Calculate the goal row index
                    int goalCol = (value - 1) % n; // Calculate the goal column index
                    count += Math.abs(i - goalRow) + Math.abs(j - goalCol); // manhattan distance formula
                }
            }
        }

        return count;
    }

    // check if this is the solved board
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // create an equals method to compare boards
    public boolean equals(Object y) {
        // Check if obj is the same object as this
        if (this == y) {
            return true;
        }

        // check if y is even an object of Board or null
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        // cast Object to Board
        Board compare = (Board) y;

        // check if the lengths are the same
        if (this.tiles.length != compare.tiles.length || this.tiles[0].length != compare.tiles[0].length) {
            return false;
        }

        // check if the individual tiles values are the same
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if (this.tiles[i][j] != compare.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    // create all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>(); // create iterable item to store neighbor boards
        int n = this.tiles.length;

        // create double for loop to find element value 0
        outerLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) { // if value 0 is found then move to creating neighbor boards
                    // check if there is a tile under the value 0
                    if (i + 1 < n) {
                        int[][] copy = copyTiles();
                        swap(copy, i, j, i + 1, j);
                        Board board = new Board(copy); // use constructor that does not shuffle the board
                        neighbors.enqueue(board);
                    }
                    // check if there is a tile above the value 0
                    if (i - 1 >= 0) {
                        int[][] copy = copyTiles();
                        swap(copy, i, j, i - 1, j);
                        Board board = new Board(copy); // use constructor that does not shuffle the board
                        neighbors.enqueue(board);
                    }
                    // check if there is a tile to the right of the value 0
                    if (j + 1 < n) {
                        int[][] copy = copyTiles();
                        swap(copy, i, j, i, j + 1);
                        Board board = new Board(copy); // use constructor that does not shuffle the board
                        neighbors.enqueue(board);
                    }
                    // check if there is a tile to the left of the value 0
                    if (j - 1 >= 0) {
                        int[][] copy = copyTiles();
                        swap(copy, i, j, i, j - 1);
                        Board board = new Board(copy); // use constructor that does not shuffle the board
                        neighbors.enqueue(board);
                    }
                    // break out of loop since there can only be one 0 element on the board
                    break outerLoop;
                }
            }
        }
        return neighbors;
    }

    // method to create a copy of the original tiles for manipulation
    private int[][] copyTiles() {
        int n = tiles.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // method to swap 2 tiles
    private void swap(int[][] array, int i, int j, int k, int h) {
        int temp = array[i][j];
        array[i][j] = array[k][h];
        array[k][h] = temp;
    }

    // a board that is obtained by exchanging the first 2 non 0 tiles. Should give the same twin every time for the same original board.
    public Board twin() {
        int n = tiles.length;
        int[][] twinTiles = copyTiles(); // Create a copy of the tiles

        // Find the positions of the first two non-zero tiles
        int[] pos1 = null;
        int[] pos2 = null;

        outerLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    if (pos1 == null) {
                        pos1 = new int[]{i, j};
                    } else {
                        pos2 = new int[]{i, j};
                        break outerLoop;
                    }
                }
            }
        }

        // Ensure both pos1 and pos2 are initialized before calling swap to prevent null pointer exceptions.
        if (pos1 != null && pos2 != null) {
            swap(twinTiles, pos1[0], pos1[1], pos2[0], pos2[1]);
        }

        return new Board(twinTiles); // Use constructor that does not shuffle the board
    }


    // test if the class methods work
    public static void main(String[] args) {
        int[][] tiles = {{1, 2, 3}, {4, 6, 5}, {7, 8, 0}};
        Board board = new Board(tiles);

        StdOut.println(board.toString()); // test to string method
        StdOut.println(board.dimension()); // test dimension method
        StdOut.println(board.hamming()); // test hamming method
        StdOut.println(board.manhattan()); // test manhattan method
        StdOut.println(board.isGoal()); // test is goal method
        StdOut.println();
        Board twin = board.twin();
        StdOut.println(twin); // test twin method
        StdOut.println(board.equals(twin)); // test equals method
        StdOut.println();

        Iterable<Board> neighbors = board.neighbors();

        for (Board b : neighbors) {
            StdOut.println(b.toString());
        }

    }
}
