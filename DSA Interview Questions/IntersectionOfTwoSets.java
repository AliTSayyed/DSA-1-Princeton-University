/*
Given two arrays 𝑎 [ ] a[] and 𝑏 [ ] b[], each containing 𝑛 n distinct 2D points in the plane,
design a subquadratic algorithm to count the number of points that are
contained both in array 𝑎 [ ] a[] and array 𝑏 [ ] b[].
 */

public class IntersectionOfTwoSets {
    // one approach is to iterate through one array and compare all its values to the second array
    // same values get stored. However, this would be an O(MN) runtime.
    // instead you can iterate both arrays at once, and find common values. O(M+N) runtime

    public void intersection(int[] array1, int[] array2) {
        // initialize index values
        int i = 0;
        int j = 0;
        // iterate between both arrays at once for efficiency
        while (i < array1.length && j < array2.length) {
            if (array1[i] == array2[j]) { // print out if values are the same.
                System.out.println(array1[i]);
                i++;
                j++;
            } else if (array1[i] > array2[j]) { // increment j if i is greater since the arrays are sorted. The value of array2[j] will never be equal to array[i]
                j++;
            } else { // same logic, must increment i if array2[j] is greater than array1[i] because the arrays are sorted.
                i++;
            }
        }
    }

    public static void main(String[] args) {
        // example arrays
        int[] array1 = {2, 3, 5, 6, 8, 10};
        int[] array2 = {1, 4, 6, 10, 11};
        IntersectionOfTwoSets overlap = new IntersectionOfTwoSets();
        overlap.intersection(array1, array2);
    }
}
