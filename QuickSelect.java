import java.util.*;

public class Select {

    private static void swap(int[] inputArray, int a, int b) {
        int tmp = inputArray[a];
        inputArray[a] = inputArray[b];
        inputArray[b] = tmp;
    }

    private static void randomPivot(int[] inputArray, int low, int high) {
        Random rand = new Random();
        int pivotIndex = low + rand.nextInt(high - low);
        swap(inputArray, pivotIndex, high);
    }

    public static int partition(int[] inputArray, int low, int high) {
        randomPivot(inputArray, low, high);
        int pivot = inputArray[high];
        int swapTo = low;
        for (int j = low; j <= high; j++) {
            if (inputArray[j] < pivot) {
                swap(inputArray, swapTo, j);
                swapTo++;
            }
        }
        swap(inputArray, swapTo, high);
        return swapTo;
    }

    public static int quickSelect(int[] unsorted, int low, int high, int k) {
        if (low == high) {
            return unsorted[low];
        }
        int partition = partition(unsorted, low, high);

        if (partition == k) {
            return unsorted[partition];
        } else if (partition < k) {
            return quickSelect(unsorted, partition + 1, high, k);
        } else {
            return quickSelect(unsorted, low, partition - 1, k);
        }
    }

    public static void main(String[] args) {
        Select test = new Select();
        int[] input = new int[]{10, 4, 5, 8, 6, 11, 26};
        int kthPosition = 7;
        System.out.println(test.quickSelect(input, 0, input.length - 1, kthPosition-1));
    }
}