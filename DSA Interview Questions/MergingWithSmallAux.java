/*
Merging with smaller auxiliary array. Suppose that the subarray 𝑎[0] to 𝑎[𝑛−1] is sorted and the subarray
𝑎[𝑛] to 𝑎[2*𝑛−1] is sorted. How can you merge the two subarrays so that 𝑎[0] to 𝑎[2*𝑛−1] is sorted using an auxiliary
array of length 𝑛 (instead of 2𝑛)? (original array has size of 2n but aux array must have size of n)
 */

public class MergingWithSmallAux {
    // Function to merge two sorted subarrays within the original array. Subarrays are of equal size. n is half the size of the original array.
    public static void merge(int[] arr, int n) {
        int[] aux = new int[n]; // Auxiliary array to merge the subarrays

        // Copy the first subarray (left side) into the auxiliary array
        System.arraycopy(arr, 0, aux, 0, n);

        int i = 0, j = n, k = 0;

        // Merge the two sorted subarrays into the original array
        while (i < n && j < 2 * n) {
            if (aux[i] <= arr[j]) {
                arr[k++] = aux[i++];
            } else {
                arr[k++] = arr[j++];
            }
        }

        // Copy remaining elements from the auxiliary array (if any) into the original array
        while (i < n) {
            arr[k++] = aux[i++];
        }
    }

    // Function to print an array
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int[] arr = {1, 3, 5, 7, 2, 4, 6, 8};
        int n = arr.length / 2;

        System.out.println("Original array:");
        printArray(arr);

        merge(arr, n);

        System.out.println("Array after merging sorted subarrays:");
        printArray(arr);
    }
}
