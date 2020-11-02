package rabin_karp;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RabinKarpExtended
{
    private String text;
    private TreeMap<Integer, Integer> number2position;

    public static void main(String[] args) {
        RabinKarpExtended rabinKarpExtended = new RabinKarpExtended("0123456789 qwerty qwerty qwertry 0123456789");
        List<Integer> list = rabinKarpExtended.search("qwerty");

        list.forEach(System.out::println);
    }

    public RabinKarpExtended(String text)
    {
        this.text = text;
        createIndex();
    }

    public List<Integer> search(String query)
    {
        ArrayList<Integer> indices = new ArrayList<>();

        char[] textLetters = text.toCharArray();
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < (textLetters.length - query.length() + 1); i++)
        {
            // Получаем строку равную по длинне искомой строке
            for(int j = 0; j < query.length(); j++){
                builder.append(textLetters[i + j]);
            }

            // Сравниваем хеши
            if(builder.toString().hashCode() == query.hashCode())
            {
                if(builder.toString().equals(query))
                    indices.add(i);
            }

            builder = new StringBuilder();
        }

        return indices;
    }

    private void createIndex()
    {
        /** Не понял, что должен делать этот метод
         */
    }
}