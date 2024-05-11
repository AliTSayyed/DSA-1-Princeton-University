import edu.princeton.cs.algs4.StdOut;

/*
An inversion in an array `a[]` is a pair of entries `a[i]` and `a[j]` such that `i < j` but `a[i] > a[j]`.
Given an array, design a linearithmic algorithm to count the number of inversions.
 */
public class InversionCount {

    private static int inversion; // variable to store value of inversions.

    private static void mergeSort(int[] inputArray) {
        int inputLength = inputArray.length;

        if (inputLength < 2) {
            return;
        }

        int midIndex = inputLength / 2;
        int[] leftHalf = new int[midIndex];
        int[] rightHalf = new int[inputLength - midIndex];

        for (int i = 0; i < midIndex; i++) {
            leftHalf[i] = inputArray[i];
        }
        for (int i = midIndex; i < inputLength; i++) {
            rightHalf[i - midIndex] = inputArray[i];
        }
        mergeSort(leftHalf);
        mergeSort(rightHalf);

        merge(inputArray, leftHalf, rightHalf);
    }

    private static void merge(int[] inputArray, int[] leftHalf, int[] rightHalf) {
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        int i = 0, j = 0, k = 0;

        while (i < leftSize && j < rightSize) {
            if (leftHalf[i] <= rightHalf[j]) {
                inputArray[k] = leftHalf[i];
                i++;

            } else {
                inputArray[k] = rightHalf[j];
                j++;
                // if an item is smaller in the right half, it had a larger original index, but had a smaller element value.
                // This means it was an inversion of the number it was compared to.
                inversion += (leftSize - i);
                // Since both leftHalf and rightHalf are sorted arrays, if rightHalf[j] is smaller than leftHalf[i],
                // then all elements from leftHalf[i] to leftHalf[leftSize - 1] will form inversions with rightHalf[j].
                // This count is exactly equal to leftSize - i.
            }
            k++;
        }

        while (i < leftSize) {
            inputArray[k] = leftHalf[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            inputArray[k] = rightHalf[j];
            j++;
            k++;
        }
    }

    public static void printInversion() {
        StdOut.print(inversion);
    }

    public static void main(String[] args) {

        int[] numbers = {9, 6, 8, 4, 3};

        mergeSort(numbers);
        printInversion();
    }
}
