/*
Describe how to add the methods `sample()` and `delRandom()` to our binary heap implementation.
The two methods return a key that is chosen uniformly at random among the remaining keys,
with the latter method also removing that key. The `sample()` method should take constant time; the `delRandom()`
method should take logarithmic time. Do not worry about resizing the underlying array.
 */

// create a sample method that will generate a random number in between 0 and the array length. Return the element at that index
// create a delRandom method that will generate a random index. Swap the index with the last item on the list and remove/return it
// Sink or swim the swapped item until the binary tree rules are satisfied.

import java.util.Random;

public class RandomizedPQ {

    private int[] heap;
    private int size;
    private Random random;

    public RandomizedPQ(int capacity) {
        heap = new int[capacity]; // Index 0 is used
        size = 0;
        random = new Random();
    }

    public void insert(int value) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full");
        }
        heap[size] = value;
        swim(size);
        size++;
    }

    public int sample() {
        if (size == 0) throw new IndexOutOfBoundsException("Heap is empty");
        int randomIndex = random.nextInt(size);
        return heap[randomIndex];
    }

    public int delRandom() {
        if (size == 0) throw new IndexOutOfBoundsException("Heap is empty");
        int randomIndex = random.nextInt(size);
        int valueToRemove = heap[randomIndex];

        // Swap with the last element and remove the last element
        exch(randomIndex, size - 1);
        heap[size - 1] = 0; // Optional: clear the last element
        size--;

        // Restore the heap property
        if (randomIndex < size) {
            sink(randomIndex);
            if (randomIndex > 0 && heap[randomIndex] > heap[(randomIndex - 1) / 2]) {
                swim(randomIndex);
            }
        }
        return valueToRemove;
    }

    private void swim(int k) {
        while (k > 0 && heap[(k - 1) / 2] < heap[k]) {
            exch(k, (k - 1) / 2);
            k = (k - 1) / 2;
        }
    }

    private void sink(int k) {
        while (2 * k + 1 < size) {
            int j = 2 * k + 1;
            if (j + 1 < size && heap[j] < heap[j + 1]) j++;
            if (heap[k] >= heap[j]) break;
            exch(k, j);
            k = j;
        }
    }

    private void exch(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        RandomizedPQ pq = new RandomizedPQ(10);
        pq.insert(5);
        pq.insert(10);
        pq.insert(3);
        pq.insert(7);

        System.out.println("Sample: " + pq.sample());
        System.out.println("Delete Random: " + pq.delRandom());
    }
}
