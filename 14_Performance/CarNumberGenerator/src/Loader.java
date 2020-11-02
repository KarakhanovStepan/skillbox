import java.io.FileWriter;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        /**
         *  1 поток выполняет работу ~44 сек
         *  4 потока выполняют работу ~23 сек
         *  8 потоков выполняют работу ~18 сек
         *
         *  4 потока в 4 файла ~13 сек
         *  4 потока в 1 файл  ~19 сек
         */

        FileWriter writer = new FileWriter("res/numbers1.txt");

        /**
         * 1 поток пишет в один файл
         */

//        ThreadWriter threadWriter1 = new ThreadWriter("res/numbers1.txt", 1, 100);
//        new Thread(threadWriter1).start();

        /**
         * 4 синхронизированных потока пишут в один файл
         */

        ThreadWriter threadWriter1 = new ThreadWriter(writer, 1, 25);
        ThreadWriter threadWriter2 = new ThreadWriter(writer, 26, 50);
        ThreadWriter threadWriter3 = new ThreadWriter(writer, 51, 75);
        ThreadWriter threadWriter4 = new ThreadWriter(writer, 76, 100);

        new Thread(threadWriter1).start();
        new Thread(threadWriter2).start();
        new Thread(threadWriter3).start();
        new Thread(threadWriter4).start();

        /**
         * 4 потока пишут в 4 файла
         */

//        ThreadWriter threadWriter1 = new ThreadWriter("res/numbers1.txt", 1, 25);
//        ThreadWriter threadWriter2 = new ThreadWriter("res/numbers2.txt", 26, 50);
//        ThreadWriter threadWriter3 = new ThreadWriter("res/numbers3.txt", 51, 75);
//        ThreadWriter threadWriter4 = new ThreadWriter("res/numbers4.txt", 76, 100);
//
//        new Thread(threadWriter1).start();
//        new Thread(threadWriter2).start();
//        new Thread(threadWriter3).start();
//        new Thread(threadWriter4).start();

        /**
         * 8 потоков пишут в 8 файлов
         */

//        ThreadWriter threadWriter1 = new ThreadWriter("res/numbers1.txt", 1, 12);
//        ThreadWriter threadWriter2 = new ThreadWriter("res/numbers2.txt", 13, 25);
//        ThreadWriter threadWriter3 = new ThreadWriter("res/numbers3.txt", 26, 38);
//        ThreadWriter threadWriter4 = new ThreadWriter("res/numbers4.txt", 39, 50);
//        ThreadWriter threadWriter5 = new ThreadWriter("res/numbers5.txt", 51, 63);
//        ThreadWriter threadWriter6 = new ThreadWriter("res/numbers6.txt", 64, 75);
//        ThreadWriter threadWriter7 = new ThreadWriter("res/numbers7.txt", 76, 88);
//        ThreadWriter threadWriter8 = new ThreadWriter("res/numbers8.txt", 89, 100);
//
//        new Thread(threadWriter1).start();
//        new Thread(threadWriter2).start();
//        new Thread(threadWriter3).start();
//        new Thread(threadWriter4).start();
//        new Thread(threadWriter5).start();
//        new Thread(threadWriter6).start();
//        new Thread(threadWriter7).start();
//        new Thread(threadWriter8).start();


        // Проверка оптимизации
        long start = System.nanoTime();

        String str = padNumber(5,3);

        System.out.println("Check the pad method: " + (System.nanoTime() - start) + " nanos ");
        System.out.println(str);

    }

    // Оптимизация метода

    private static String padNumber(int number, int numberLength)
    {
        char[] chars = Integer.toString(number).toCharArray();

        if(chars.length >= numberLength)
            return String.copyValueOf(chars);
        else {
            // Вставляем меньший массив в больший начиная с конца

            char[] arr = new char[numberLength];

            int j = chars.length;

            for (int i = numberLength; i > 0; i--) {
                if (j == 0)
                    arr[i-1] = '0';
                else {
                    arr[i - 1] = chars[j - 1];
                    j--;
                }
            }

            return String.copyValueOf(arr);
        }
    }
}