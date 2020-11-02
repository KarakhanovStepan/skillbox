package binary_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BinarySearch
{
    private ArrayList<String> list;

    public BinarySearch(ArrayList<String> list)
    {
        this.list = list;
        Collections.sort(list);
    }

    public int search(String query)
    {
        if(list.size() == 0)
            return -1;

        return search(query, 0, list.size() - 1);
    }

    private int search(String query, int from, int to)
    {
        if(from == to)
        {
            if(query.equals(list.get(from)))
                return from;
            else
                return -1;
        }

        int middle = (from + to) / 2;
        int comparison = query.compareTo(list.get(middle));

        if(comparison == 0) {

            return check(query, middle);
        }

        if(comparison > 0)
            return search(query, middle + 1, to);
        else
            return search(query, from, middle);
    }


    /**
     *  Метод check() нужен для поиска именно первого по порядку элемента в массиве
     *
     *  Если в массиве есть два идентичных элемента, то бинарный поиск укажет на правый элемент,
     *  Что не принципиально если нужно просто совпадение,
     *  Но в массиве с одинаковыми элементами бинарный поиск всегда будет указывать на элемент в середине массива,
     *  Что, наверное, не совсем корректно.
     *
     *  Поэтому бинарный поиск логично применять к массивам с уникальными элементами.
     */
    public int check(String query,int firstMetItem)
    {
        try
        {
            if(query.equals(list.get(firstMetItem - 1)))
                return check(query, firstMetItem -1);
        }
        catch (IndexOutOfBoundsException ex)
        {
        }

        return firstMetItem;
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(
                "Aax",
                "Bax",
                "Cax",
                "Dax",
                "Eax",
                "Fax",
                "Gax",
                "Hax",
                "Iax",
                "Jax",
                "Lax",
                "Max"

//  Без метода check() вернет номер среднего элемента

//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax",
//                "Aax"
        ));

        BinarySearch binarySearch = new BinarySearch(list);

        System.out.println(binarySearch.search("Aax"));
    }
}
