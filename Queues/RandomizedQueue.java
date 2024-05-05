/*
Purpose of this program is to create a Queue that returns randomized values that were enqueued.
Need to use a resizing array since accessing random values is much faster using arrays than
linked lists.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q; // create array to store items
    private int n; // create int to keep track of array size

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1]; // instantiate new object array with item type cast (only way).
        n = 0; // set size to 0
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (n == q.length) resizeQueueArray(q.length * 2); // resize the array to twice its size if array is full
        q[n++] = item; // store item at index n and then increment n by one, the new q[n] will now be null.
    }

    // remove and return a random item
    public Item dequeue() {
        int r = StdRandom.uniformInt(n); // create a random int from [0,n)
        Item i = q[r]; // store value at that index
        q[r] = q[n - 1]; // sets value of q[r] to the last value in the array. This removes the original q[r] value.
        q[n - 1] = null; // Then set the last item to null.
        if (n == q.length / 4) resizeQueueArray(q.length / 2); // resize array to half if the array is 1/4 full
        n--; // decrease n by 1 since we removed an item
        return i;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return q[StdRandom.uniformInt(n)]; // returns a random value from [0,n)
    }

    // create method to resize array
    private void resizeQueueArray(int capactiy) {
        Item[] copy = (Item[]) new Object[capactiy];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // return an independent iterator over items in random order, needs a custom class
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current; // int variable to keep track of indexes
        private Item[] items; // new array to store original values

        public RandomizedQueueIterator() {
            current = 0; // set current to 0, items[0] = first value in q[].
            items = (Item[]) new Object[n]; // create new items array with type cast
            // populate array
            for (int i = 0; i < n; i++) {
                items[i] = q[i];
            }
            // once populated, randomize the order of items in the array
            StdRandom.shuffle(items);
        }

        public boolean hasNext() {
            return n > current; // n will always be incremented before current as long as there are items in the array. If not, then there are no items left.
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove method not supported");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No items in the dequeue");
            return items[current++]; // return element and then increment current by one to get the next element.
        }
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(2);
        rq.enqueue(1);
        for (int number : rq) {
            StdOut.print(number);
        }

    }
}
