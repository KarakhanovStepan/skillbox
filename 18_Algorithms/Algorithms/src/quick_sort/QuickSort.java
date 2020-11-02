package quick_sort;

import java.util.Arrays;

public class QuickSort
{
    public static void main(String[] args)
    {
        int[] arr = new int[]{38,5,8,3,45,69,0,23,4,654,3,2};

        sort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] array)
    {
        if(array.length <= 1) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int from, int to)
    {
        if(from <= to)
        {
            int pivot = partition(array, from, to);
            sort(array, from, pivot - 1);
            sort(array, pivot + 1, to);
        }
    }

    private static int partition(int[] array, int from, int to)
    {
        int i = from, j = to + 1;

        while(true)
        {
            while(array[++i] < array[from])
            {
                if ( i == to ) break;
            }

            while (array[--j] > array[from])
            {
                if ( j == from ) break;
            }

            if (i >= j) break;

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        int temp = array[from];
        array[from] = array[j];
        array[j] = temp;

        return j;
    }

}
