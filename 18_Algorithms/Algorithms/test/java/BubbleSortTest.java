import bubble_sort.BubbleSort;
import junit.framework.TestCase;

public class BubbleSortTest extends TestCase
{
    private int[] actual;

    @Override
    protected void setUp() throws Exception
    {
        actual = new int[]{5,4,3,2,1};
    }

    public void testSort()
    {
        BubbleSort.sort(actual);
        int[] expected = new int[]{1,2,3,4,5};

        for(int i = 0; i < actual.length; i++)
        {
            assertEquals(expected[i], actual[i]);
        }
    }

    @Override
    protected void tearDown() throws Exception
    {

    }
}
