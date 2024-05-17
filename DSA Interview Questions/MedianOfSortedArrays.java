/*
Question 2
Selection in two sorted arrays. Given two sorted arrays `a[]` and `b[]`, of lengths `n1` and `n2` respectively, and an integer `0 ≤ k < n1 + n2`, design an algorithm to find a key of rank `k`.
The order of growth of the worst-case running time of your algorithm should be log(n), where n = n1 + n2.
For arrays of equal length (n1 = n2) and k = n/2 (median).
 */

import java.util.Arrays;

public class MedianOfSortedArrays {

    // recursive method to determine the median value if two sorted arrays are combined into one array (O(log(m*n)) time.
    public static double findSortedMedianArray(int[] a, int[] b, int n) {
        // if the arrays are size 1, the median is the lesser of the two
        if (n == 1) {
            return Math.min(a[0], b[0]);
        }
        // calculate the median of both arrays separately
        double m1 = median(a, n);
        double m2 = median(b, n);

        // if both arrays have the same median, then return any one of the medians
        if (m1 == m2) {
            return m1;
        }

        // if the median of the first array is less than the median of the second array then search for the median between the original median and the first array and the beginning of the second array and its median.
        if (m1 < m2) {
            if (n % 2 == 0) { // method if the array is even
                return findSortedMedianArray(Arrays.copyOfRange(a, n / 2, a.length), Arrays.copyOfRange(b, 0, n / 2), n / 2);
            } else { // method if the array is odd
                return findSortedMedianArray(Arrays.copyOfRange(a, n / 2, a.length), Arrays.copyOfRange(b, 0, n / 2 + 1), n / 2 + 1);
            }
        } else { // if the median of the first array is greater than the median of the second array then search for the median between the beginning and the median of the first array and median of the second array and its end.
            if (n % 2 == 0) { // method if the array is even
                return findSortedMedianArray(Arrays.copyOfRange(a, 0, n / 2), Arrays.copyOfRange(b, n / 2, b.length), n / 2);
            } else { // method if the array is odd
                return findSortedMedianArray(Arrays.copyOfRange(a, 0, n / 2 + 1), Arrays.copyOfRange(b, n / 2, b.length), n / 2 + 1);
            }
        }
    }

    // create method to calculate the median value in an array
    public static double median(int[] array, int n) {
        if (n % 2 == 0) { // if the array length is even, then the median value is in the average of the two middle elements
            return (array[n / 2 - 1] + array[n / 2]) / 2.0;
        } else { // if the array is odd then a basic integer divide will round up to the median element
            return array[n / 2];
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {6, 7, 8, 9, 10};
        int n = a.length;
        System.out.println("Median of combined arrays: " + findSortedMedianArray(a, b, n));
    }
}
