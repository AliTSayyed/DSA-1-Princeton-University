/*
Design an algorithm for the 3-SUM problem that takes time proportional to n^2 in the worst case.
You may assume that you can sort the n integers in time proportional to n^2 or better.
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;

class ThreeSum {

    public static void main(String[] args) {

// initialize array and fix the first value of the array. Then compare all possible triplets with that value that equal 0.
// Then move on to the next value and all of its possible triplets.
        ArrayList<Integer> data = new ArrayList<Integer>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextInt()) {
            data.add(scan.nextInt());
        }

// sort array so we can determine when to move each pointer
        Collections.sort(data);

// compute all 3-sum combinations
        for (int i = 0; i < data.size() - 2; ++i) { // fix i to be the target value
            int j = i + 1; // set j to be the left side pointer
            int k = data.size() - 1; // set k to be the right side pointer

            while (j < k) { // make sure the left side pointer does not pass the right side pointer
                int sum = data.get(i) + data.get(j) + data.get(k); // add the triplet
                if (sum == 0) { // if triplet equals 0, print it out
                    System.out.println(i + ":" + data.get(i) + ", " + j + ":" + data.get(j) + ", " + k + ":" + data.get(k));
                }
                if (sum >= 0) { // if the sum is greater than 0, need to reduce the right side pointer to get a smaller sum
                    --k;
                } else { // if the number is less than 0, need to increment the left side pointer to get a larger sum
                    ++j;
                }
            }
        }

    }

}
