/*
Design a data type that supports insert in logarithmic time,
find-the-median in constant time, and remove-the-median in logarithmic time.
If the number of keys in the data type is even, find/remove the lower median.
 */

// Maintain two binary heaps, one that is max-oriented and one that is min-oriented.
// the min oriented heap has all numbers larger than or equal to the numbers in the max oriented heap
// the median is calculated by comparing the min of the large heap and max of the small heap, return the smaller of the two
// if there is an odd number of values in one heap, return its min or max as the median
// no heap should have more than 1 extra element than another heap. A difference of 1 is allowed.

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

public class DynamicMedian {
    private MaxPQ<Integer> lowerHalf; // Max-Heap
    private MinPQ<Integer> upperHalf; // Min-Heap

    public DynamicMedian() {
        lowerHalf = new MaxPQ<>();
        upperHalf = new MinPQ<>();
    }

    public void insert(int value) {
        // Insert into max-heap if value is less than or equal to its max, else into min-heap
        if (lowerHalf.isEmpty() || value <= lowerHalf.max()) {
            lowerHalf.insert(value);
        } else {
            upperHalf.insert(value);
        }

        // Balance the heaps if their sizes differ by more than one
        balanceHeaps();
    }

    public int findMedian() {
        if (lowerHalf.isEmpty() && upperHalf.isEmpty()) {
            throw new IllegalStateException("No elements in the data structure.");
        }
        if (lowerHalf.size() >= upperHalf.size()) {
            return lowerHalf.max();
        } else {
            return upperHalf.min();
        }
    }

    public int removeMedian() {
        if (lowerHalf.isEmpty() && upperHalf.isEmpty()) {
            throw new IllegalStateException("No elements in the data structure.");
        }

        int median;
        if (lowerHalf.size() >= upperHalf.size()) {
            median = lowerHalf.delMax();
        } else {
            median = upperHalf.delMin();
        }

        // Balance the heaps after removal
        balanceHeaps();

        return median;
    }

    private void balanceHeaps() {
        if (lowerHalf.size() > upperHalf.size() + 1) {
            upperHalf.insert(lowerHalf.delMax());
        } else if (upperHalf.size() > lowerHalf.size() + 1) {
            lowerHalf.insert(upperHalf.delMin());
        }
    }

    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian();
        dm.insert(1);
        dm.insert(5);
        dm.insert(3);
        System.out.println(dm.findMedian()); // Output: 3
        dm.insert(2);
        System.out.println(dm.findMedian()); // Output: 3
        System.out.println(dm.removeMedian()); // Output: 3
        System.out.println(dm.findMedian()); // Output: 2
    }
}
