/* Purpose of this program is to create generic data type for a deque (pronounced "deck").
A deque is a data structure that allows you to add or remove items from the front and back.
In this program, I used a double linked list as the structure to create the deque and made an iterator
to go through items in the deque.
*/

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // Node to keep track of the head item on the list
    private Node last; // Node to keep track of the tail item on the list
    private int size; // variable to keep track of deque size

    // Must create a private node class (can be named anything but convention is node) to implement a linked list structure
    // Think of a node like a "unit" that can hold a (Item) value in memory.
    // each "element" in the deque is its own double linked node (except for first and last item that are single linked)
    private class Node {
        Item item; // Generic item to add to the Node
        Node next; // Node called next (used to link to next node)
        Node prev; // Node called prev (used to link to prev node)
    }

    // construct an empty deque
    public Deque() {
        first = null; // set first Node value to null (no memory taken but instantiated)
        last = null; // set last Node value to null (no memory taken but instantiated)
        size = 0; // set the size of the deque to 0 since no items are in the deque to start
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null; // if the first node ("unit") is null there are no items in the deque
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Not a valid input");
        Node oldFirst = first; // create a new node (unit that stores a value) and set it equal to the value of first (will become the older item in the deque)
        first = new Node(); // make first into a new Node with all null values.
        first.item = item; // set the value of the new first node to the item.
        first.next = oldFirst; // set the .next property of the first node equal to the oldFirst node (if this was the first item added to the deque, first.next points to null).
        if (oldFirst != null)
            oldFirst.prev = first; // Case for item added not being the first item to be in the deque, point its prev node to first. If it was the first, don't need to point the prev node.
        if (last == null)
            last = first; // if this was the first item added to the deque, it is both the first and last item in the deque. Set last node equal to first.
        size++; // Increment the size to keep track of the amount of added items.
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Not a valid input");
        Node oldLast = last; // create a new node called oldLast to store the value of the current last node.
        last = new Node(); // make last into a new Node with all null values.
        last.item = item; // set the value of last to item
        last.prev = oldLast; // point last item in the deque to the previous last item in the deque
        if (oldLast != null)
            oldLast.next = last; // if oldLast was not the first item added in the deque, point its node to the new item
        if (first == null) first = last; // set first to last if this is the first item added to the deque
        size++; // Increment the size to keep track of the amount of added items.
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("No items in the deque");
        Item item = first.item; // store the value that was associated in the first item
        first = first.next; // set the first node equal to the next node (essentially deletes the first node)
        if (first != null) {
            first.prev = null; // set the first node (the "next node") previous to null as it no more exists
        } else {
            last = null; // if it was the last item in the deque, set last to null as well.
        }
        size--; // decrease the size of deque
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("No items in the deque");
        Item item = last.item; // store value of the last item
        last = last.prev; // set the last equal to the next to last item
        if (last != null) {
            last.next = null; // make sure that the last no more points to the node deleted
        } else {
            first = null; // if it was the last item on the list, set first to null.
        }
        size--; // decrease the size to by one
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(); // Iterator method uses a custom iterator, need to create a custom class for it.
    }

    // private class to create custom iterator
    private class DequeIterator implements Iterator<Item> {

        private Node current = first; // create a new node that has the value of first

        public boolean hasNext() {
            return current != null; // current node must have a value
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove method not supported");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No items in the dequeue");
            Item item = current.item; // store item of current node
            current = current.next; // set current to the next node of the deque
            return item;
        }
    }

    // test the deque data structure
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        StdOut.println(deque.isEmpty());

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());

        for (int number : deque) {
            StdOut.print(number);
        }

        deque.removeFirst();
        deque.removeLast();
        StdOut.println();
        for (int number : deque) {
            StdOut.print(number);
        }
    }
}
