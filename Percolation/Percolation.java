

/* goal is to create a program that can create an n * n size grid
and determine if the grid percolates. Percolation means that there is a connected
link of full sites in the grid. The gird is initially set to all sites being closed.
We select a certain coordinate (aka tile) in the n * n grid to become open.
A site becomes full if it is connected to the top only.
We use a weighted union-find algorithm to complete this problem.
If we create a top node and a bottom node (relative to the grid) and then check if the two nodes are connected to each other
(ie, the root of the bottom node is the top node) then we know the grid percolates. */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP = 0; // TOP node's index is always 0
    private int size; // this is the n value user input for the n * n grid
    private int bottom; // bottom node
    private boolean[][] grid; // create n * n grid
    private int openSites; // keep track of open sites
    private WeightedQuickUnionUF qf; // create a weighted union-find object

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Size of grid must be greater than 0");
        this.size = n;
        this.grid = new boolean[n][n]; // the default value of each cell is false (which means blocked)
        this.bottom = size * size + 1; // bottom node's index will always be one more than the final grid's index
        qf = new WeightedQuickUnionUF(n * n + 2); // create qf object with n*n+2 index values (since we added a TOP and bottom node)
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already. True means open.
    public void open(int row, int col) {
        checkException(row, col);
        if (grid[row - 1][col - 1]) {
            return;
        }
        grid[row - 1][col - 1] = true; // Update tile to true. Must subtract 1 from the input values since the array index is n-1
        openSites++; // update the amount of open sites

        // Top row case, if any of these coordinates are open then they will connect with the TOP node
        if (row == 1) {
            qf.union(getQuickFindIndex(row, col), TOP);
        }

        // Bottom row case, if any of these coordinates are open then they will connect with the bottom node
        if (row == size) {
            qf.union(getQuickFindIndex(row, col), bottom);
        }

        // Not TOP or bottom row case, need to check if the surrounding tiles are open. If they are, union them.
        // conditionals make sure that the tiles being compared are open and not out of bounds before making a union
        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row - 1, col)); // check tile above
        }
        if (row < size && isOpen(row + 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row + 1, col)); // check tile below
        }
        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col - 1)); // check tile to the left
        }
        if (col < size && isOpen(row, col + 1)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col + 1)); // check tile to the right
        }
    }

    // get qf index value of the tile (since each block in the grid has an index value in the qf)
    // note there is no need to subtract 1 from row or col since we are working the qf index
    private int getQuickFindIndex(int row, int col) {
        checkException(row, col);
        return size * (row - 1) + col;
    }

    // check if row and col value inputs are valid
    private void checkException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Not a valid row or column!");
        }
    }

    // returns if the site (row, col) is open
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return grid[row - 1][col - 1];
    }

    // returns if the site (row, col) is full. Only full if there is a connection to a full site.
    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= size) && (col > 0 && col <= size)) {
            return isOpen(row, col) && qf.find(getQuickFindIndex(row, col)) == qf.find(TOP);
        } else throw new IllegalArgumentException("Invalid inputs for row or column");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // return if system percolates.This is when TOP and bottom are connected (i.e. share the same root).
    public boolean percolates() {
        return qf.find(TOP) == qf.find(bottom);
    }

    public static void main(String[] args) {

    }
}
