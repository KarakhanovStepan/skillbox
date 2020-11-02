package merge_sort;

import java.util.Arrays;

public class MergeSort
{
    public static void main(String[] args)
    {
        int[] arr = new int[]{38,5,8,3,45,69,0,23,4,654,3,2};

        mergeSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] array)
    {
        int n = array.length;
        if(n < 2) {
            return;
        }
        int middle = n / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[n - middle];

        for (int i = 0; i < middle; i++) {
            leftArray[i] = array[i];
        }
        for (int i = middle; i < n; i++) {
            rightArray[i - middle] = array[i];
        }
        mergeSort(leftArray);
        mergeSort(rightArray);

        merge(array, leftArray, rightArray);
    }

    private static void merge(int[] array, int[] left, int[] right)
    {
        int leftIndex = 0, rightIndex = 0;

        for(int i = 0; i < array.length; i++)
        {
            if(leftIndex < left.length && rightIndex < right.length)
            {
                if(left[leftIndex] < right[rightIndex])
                {
                    array[i] = left[leftIndex];
                    leftIndex++;
                }
                else
                {
                    array[i] = right[rightIndex];
                    rightIndex++;
                }
            }
            else if(leftIndex == left.length)
            {
                array[i] = right[rightIndex];
                rightIndex++;
            }
            else
            {
                array[i] = left[leftIndex];
                leftIndex++;
            }
        }
    }
}
