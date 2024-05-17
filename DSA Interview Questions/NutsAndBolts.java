/*
A disorganized carpenter has a mixed pile of n nuts and n bolts.
The goal is to find the corresponding pairs of nuts and bolts.
Each nut fits exactly one bolt and each bolt fits exactly one nut.
By fitting a nut and a bolt together, the carpenter can see which one is bigger
(but the carpenter cannot compare two nuts or two bolts directly).
Design an algorithm for the problem that uses at most proportional to n log n compares (probabilistically).
 */

public class NutsAndBolts {
    // recursive matchSort algorithim that takes in two arrays of the same size with the same items in different orders and sorts them in the same order
    public static void matchSort(char[] nuts, char[] bolts, int low, int high) {
        // condition to make sure that subarray is not size 1
        if (low < high) {
            // partition first array using the last index as the pivot
            int pivot = partition(nuts, low, high, bolts[high]);
            // store the location of the pivot from the first array and use it as the same pivot for the second array (pivot is not necessarily high index again)
            partition(bolts, low, high, nuts[pivot]);
            // recursive call on the left side of the pivot and the right side of the pivot on both (2 sub arrays)
            matchSort(nuts, bolts, low, pivot - 1);
            matchSort(nuts, bolts, pivot + 1, high);
        }

    }

    // method to partition items around a pivot
    private static int partition(char[] array, int low, int high, char pivot) {
        // pointer to keep track of items less than the pivot
        int i = low;
        // temp variables to swap (can also do a swap method)
        char temp1, temp2;
        // loop to check what item is less than the pivot
        for (int j = low; j < high; j++) {
            // if the item is less than the pivot, swap it with the item at i (keeps items to the left of pivot less than the pivot)
            if (array[j] < pivot) {
                temp1 = array[i];
                array[i] = array[j];
                array[j] = temp1;
                i++; // increment the left pointer to keep track of where the pivot should be swapped to at the end
            // special case where the value of the item is the same as the pivot before reaching the end of the array.
            } else if (array[j] == pivot) {
                temp1 = array[j];
                array[j] = array[high];
                array[high] = temp1;
                j--; // decrease j to recheck the swapped element
            }
        }
        // Swap the pivot to its correct position at the end to make sure all items to the left of the pivot are less than the pivot and the right are greater
        temp1 = array[i];
        array[i] = array[high];
        array[high] = temp1;

        return i; // return the index of the pivot so the same pivot can be used in the next array
    }


    public static void printArray(char[] array) {
        for (char ch : array) {
            System.out.print(ch + " ");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        // Nuts and bolts are represented as an array of characters
        char[] nuts = {'@', '#', '$', '%', '^', '&'};
        char[] bolts = {'$', '%', '&', '^', '@', '#'};
        System.out.println("Before:");
        printArray(nuts);
        printArray(bolts);
        matchSort(nuts, bolts, 0, 5);
        System.out.println("After:");
        printArray(nuts);
        printArray(bolts);
    }
}
