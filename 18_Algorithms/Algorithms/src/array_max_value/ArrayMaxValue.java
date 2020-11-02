package array_max_value;

public class ArrayMaxValue
{
    public static int getMaxValue(int[] values)
    {
        if(values.length == 0) {
            System.out.println("Empty array.");
            throw new IllegalArgumentException("Empty array");
        }

        int maxValue = Integer.MIN_VALUE;
        for(int value : values)
        {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
        System.out.println(getMaxValue(new int[]{}));
    }
}