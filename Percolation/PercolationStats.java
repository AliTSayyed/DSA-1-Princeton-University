
/* Purpose of this class is to perform a series of computational experiments on an n * n grid.
We want to determine the percolation threshold for an n*n grid. */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials; // int value to store trials amount
    private double[] probabilities; // array to store individual probability thresholds
    private double confidence95 = 1.96; // constant at 95% confidence

    // perform independent trials on an n*n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid grid or trials");
        this.trials = trials; // store the amount of trials
        probabilities = new double[trials];

        // repeat the amount of trials and determine the probability threshold of each trial
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(n); // create new percolation object
            int openedSites = 0; // store the amount of open sites per percolation object

            // repeat opening random sites until system percolates
            while (!system.percolates()) {
                int j = StdRandom.uniformInt(1, n + 1); // create random int as row or col values (last value is exclusive so must add 1)
                int k = StdRandom.uniformInt(1, n + 1);
                if (!system.isOpen(j, k)) system.open(j, k);
            }
            openedSites = system.numberOfOpenSites(); // store the number of open sites
            double probability = 1.0 * openedSites / (n * n); // probability threshold
            probabilities[i] = probability; // add each probability threshold to the probabilities array for mean and stddev calculation
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(probabilities);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(probabilities);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidence95 * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidence95 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {

        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(size, trials);

        StdOut.println("mean= " + stats.mean());
        StdOut.println("stddev= " + stats.stddev());
        StdOut.println("95% confidence interval = " + "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
