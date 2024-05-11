/*
Given two integer arrays of size n, design a subquadratic algorithm to determine whether one is a permutation of the other.
 That is, do they contain exactly the same entries but, possibly, in a different order.
 */

import java.util.Arrays;

public class PermutationQuestion {
    // first check if array lengths are the same
    // then sort the arrays
    // then traverse the array and compare the values, if there is one different value, it is not a permutation.
    public boolean isPermutaiton(int[] a, int[] b) {
        if (a.length == b.length) {
            Arrays.sort(a);
            Arrays.sort(b);
            for (int i = 0; i < a.length; ++i) {
                if (a[i] != b[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // example arrays
        int[] array1 = {1, 2, 3, 4};
        int[] array2 = {4, 3, 2, 1};
        PermutationQuestion pq = new PermutationQuestion();
        if (pq.isPermutaiton(array1, array2)) {
            System.out.println("Permutation!");
        } else {
            System.out.println("Not a permutation.");
        }
    }
}
