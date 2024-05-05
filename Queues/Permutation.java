/*
Purpose of this program is to create a permutation that takes in an int argument and
returns that amount of random items from the RandomizedQueue.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>(); // create a new randomized queue object
        int k = Integer.parseInt(args[0]); // store the value put in the commandline as an int
        // read in values and put them in the randomized queue
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            rq.enqueue(input);
        }
        // return items in random order.
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
