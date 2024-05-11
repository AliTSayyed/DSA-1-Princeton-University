/*
Dutch national flag. Given an array of 𝑛 buckets, each containing a red, white, or blue pebble, sort them by color.
The allowed operations are:
swap(i,j):  swap the pebble in bucket 𝑖 with the pebble in bucket 𝑗.
color(i): determine the color of the pebble in bucket 𝑖.

The performance requirements are as follows:
At most 𝑛 calls to color().
At most 𝑛 calls to swap().
Constant extra space.
*/

import java.util.Arrays;

public class ColorSort {

    // create a color array from the numbers
    public String[] colorArray(int[] bucket) {
        String[] colorArray = new String[bucket.length];
        for (int i = 0; i < bucket.length; i++) {
            colorArray[i] = color(bucket[i]);
        }
        return colorArray;
    }

    // use 3 sort method, only works with 3 unique values in ascending order
    public void sort(int[] bucket) {
        int lo = 0; // index of lowest
        int mid = 0; // index of mid (used as the "condition checker")
        int hi = bucket.length - 1; //index of highest
        while (mid <= hi) { // mid pointed can never pass the hi pointer
            if (bucket[mid] == 0) { // if the condition is 0, make the lo index 0
                swap(bucket, lo, mid);
                mid++; // move both pointers since that value does not need to be touched again
                lo++;
            } else if (bucket[mid] == 1) { // if the condition is 1, it is in the middle, move on
                mid++;
            } else if (bucket[mid] == 2) { // if the condition is 2, that is the highest, swap and increment
                swap(bucket, mid, hi);
                hi--; // reduce the hi pointer since the left item the hi pointer must now be the highest value, 2
            }
        }
    }

    // swap method
    public void swap(int[] bucket, int i, int j) {
        int swap = bucket[i];
        bucket[i] = bucket[j];
        bucket[j] = swap;
    }

    // map int to colors
    public String color(int i) {
        if (i == 0) {
            return "red";
        }
        if (i == 1) {
            return "white";
        }
        if (i == 2) {
            return "blue";
        } else return null;
    }

    public static void main(String[] args) {
        ColorSort cs = new ColorSort();
        int[] array1 = {1, 1, 1, 2, 2, 0, 0, 1, 1};

        // Sort the array
        cs.sort(array1);

        // Print the sorted array in number version
        System.out.print("Sorted array (number version): ");
        System.out.println(Arrays.toString(array1));

        // Print the sorted array in color version
        String[] colorArray = cs.colorArray(array1);
        System.out.print("Sorted array (color version): ");
        System.out.println(Arrays.toString(colorArray));
    }
}

