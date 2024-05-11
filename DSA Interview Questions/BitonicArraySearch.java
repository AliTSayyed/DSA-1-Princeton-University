/*
An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a
decreasing sequence of integers. Write a program that, given a bitonic array of n distinct integer values,
determines whether a given integer is in the array.
 */

// First find the bitonic point, then go a normal binary search on the first half,
// then do a reverse binary search on the second half, if the key is not in the array return -1

public class BitonicArraySearch {
    // finding bitonic point first, this will determine where to split the array for the 2 types of binary searches.
    public int findBitonicPoint(int[] arr, int n, int l, int r) {
        int mid;
        int bitonicPoint = 0;
        mid = (r + l) / 2; // midpoint of array using index 0 and N -1
        if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) { // check if previous number and next number is less than the point (means it is bitonic)
            return mid;
        } else {
            if (arr[mid] > arr[mid - 1] && arr[mid] < arr[mid + 1]) { // if the point is to the left of the bitonic point
                bitonicPoint = findBitonicPoint(arr, n, mid, r); // make a recursive call with mid replacing the new left side
            } else { // of the point is to the right of the bitonic point
                if (arr[mid] < arr[mid - 1] && arr[mid] > arr[mid + 1]) {  // if the point is to the right of the biotonic point
                    bitonicPoint = findBitonicPoint(arr, n, l, mid); //  make a recursive call with mid replacing the new right side
                }
            }
        }
        return bitonicPoint; // return peak point
    }

    // Function for binary search in ascending part. Int low is index 0, and int high it the bitonic point.
    public int ascendingBinarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == key) {
                return mid;
            }
            if (arr[mid] > key) { // normal binary search, if the mid pointer is to the right of the key, make high mid + 1
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    // Function for binary search in descending part of array. Int low is the end of the array and int high is bitonic point
    static int descendingBinarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == key) {
                return mid;
            }
            if (arr[mid] > key) { // reverse binary search, if the mid pointer is to the right of the key, update low to be mid + 1
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    // Function to search key in bitonic array
    public int searchBitonic(int[] arr, int n, int key, int index) {
        if (key > arr[index]) {
            return -1;
        } else if (key == arr[index]) {
            return index;
        } else {
            int temp = ascendingBinarySearch(arr, 0, index - 1, key);
            if (temp != -1) {
                return temp;
            }

    // Search in right of k
            return descendingBinarySearch(arr, index + 1, n - 1, key);
        }
    }

    // Driver code
    public static void main(String[] args) {
        BitonicArraySearch bas = new BitonicArraySearch();
        int[] arr = { -8, 1, 2, 3, 4, 5, -2, -3 };
        int key = 5;
        int n, l, r;
        n = arr.length;
        l = 0;
        r = n - 1;
        int index;
        index = bas.findBitonicPoint(arr, n, l, r);

        int x = bas.searchBitonic(arr, n, key, index);

        if (x == -1) {
            System.out.println("Element Not Found");
        } else {
            System.out.println("Element Found at index " + x);
        }
    }
}

