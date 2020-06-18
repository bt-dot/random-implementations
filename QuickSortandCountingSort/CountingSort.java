import java.util.*;

public class Sorts {

    private static int findMax(int[] inputArray){
            int i = 0;
            int max = 0;
            while (i < inputArray.length) {
                if (max < inputArray[i]) {
                    max = inputArray[i];
                }
                i++;
            }
            return max;
        }

    /**
     * Sorting the given array by counting the number of occurrences of each unique element.
     * @param array unsorted, a list of numbers that need to be sorted
     * @return array, a sorted array list
     */
    public static int[] countingSort(int[] unsorted) {
        int max = findMax(unsorted);
        int[] sorted = new int[unsorted.length];
        int[] count = new int[max+1];

        for (int i = 0; i < unsorted.length; i++) {
            sorted[i] = 0;
        }
        for (int i = 0; i <= max; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < unsorted.length; i++) {
            count[unsorted[i]]++;
        }
        for (int i = 1; i <= max; i++) {
            count[i] = count[i] + count[i-1];
        }
        for (int i = unsorted.length - 1; i >= 0; i--) {
            count[unsorted[i]]--;
            sorted[count[unsorted[i]]] = unsorted[i];
        }

        return sorted;
    }

    private static void randomPivot(int[] inputArray, int low, int high) {
        Random rand = new Random();
        int pivotIndex = low + rand.nextInt(high-low);
        swap(inputArray, pivotIndex, high);
    }

    private static void swap(int[] inputArray, int a, int b) {
        int tmp = inputArray[a];
        inputArray[a] = inputArray[b];
        inputArray[b] = tmp;
    }

    /**
     * This method calls QuickSort on an input integer array and recursively sorts the array
     * using random partitioning.
     *
     * lowerBound and upperBound parameters allow us to sort different pieces of the array
     * independently.
     *
     * @param unsorted The array we want to sort.
     * @param low The minimum index value of the array to be sorted.
     * @param high The maximum index value of the array to be sorted.
     */
    public static void quickSort(int[] unsorted, int low, int high) {
        if (low < high) {
            int pivot = partition(unsorted, low, high);
            quickSort(unsorted, low, pivot - 1);
            quickSort(unsorted, pivot + 1, high);
        }
    }

    /**
     * This method divides the input array around a randomly selected pivot value.
     * Values less than or equal to the pivot will be moved to the left of the pivot
     * while values greater than the pivot go to the right.
     *
     * @param inputArray The array being sorted.
     * @param low The first index of the array being sorted.
     * @param high The last index of the array being sorted.
     * @return The pivot
     */
    public static int partition(int[] inputArray, int low, int high) {
        randomPivot(inputArray, low, high);
        int pivot = inputArray[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (inputArray[j] <= pivot) {
                i++;
                swap(inputArray, i, j);
            }
        }
        i++;
        swap(inputArray, i, high);
        return i;
    }

    private static boolean isSorted(int[] inputArray) {
        for (int i = 0; i < inputArray.length - 1; i ++) {
            if (inputArray[i] > inputArray[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //change the values here to test
        int size = 1000;
        int range = 1000; 
        int[] countingSortArray = new int[size];
        int[] quickSortArray = new int[size];

        for (int i = 0; i < size; i++) {
            Random random = new Random();
            int rand = random.nextInt(range);
            countingSortArray[i] = rand;
            quickSortArray[i] = rand;
        }


        long start = System.nanoTime();
        int[] sorted = countingSort(countingSortArray);
        long end = System.nanoTime();
        if (isSorted(sorted))
            System.out.print("Counting sort successful!" + "\n");
        else
            System.out.print("Counting sort failed!" + "\n");
        System.out.print("Counting Sort with " + size + " numbers and range of " + range + " took (sec): " + (end - start) / 1000000000.0 + "\n");


        long start2 = System.nanoTime();
        quickSort(quickSortArray, 0, quickSortArray.length - 1);
        long end2 = System.nanoTime();
        if (isSorted(quickSortArray))
            System.out.print("Quick sort successful!" + "\n");
        else
            System.out.print("Quick sort failed!" + "\n");
        System.out.print("Quick Sort with " + size + " numbers and range of " + range + " took (sec): " + (end2 - start2) / 1000000000.0 + "\n");
    }
}