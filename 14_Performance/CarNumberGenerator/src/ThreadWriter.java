import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadWriter implements Runnable
{
    private int startRegion;
    private int endRegion;
    private FileWriter writer;

    private char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

    public ThreadWriter(String filePath, int startRegion, int endRegion) {
        this.startRegion = startRegion;
        this.endRegion = endRegion;
        try {
            this.writer = new FileWriter(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ThreadWriter(FileWriter writer, int startRegion, int endRegion)
    {
        this.writer = writer;
        this.startRegion = startRegion;
        this.endRegion = endRegion;
    }

    @Override
    public void run(){

        long start = System.currentTimeMillis();
        System.out.println("Thread start!");

        try {
            for (int regionCode = startRegion; regionCode < endRegion + 1; regionCode++) {
                StringBuffer buffer = new StringBuffer();
                for (int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                buffer.append(firstLetter);
                                buffer.append(padNumber(number, 3));
                                buffer.append(secondLetter);
                                buffer.append(thirdLetter);
                                buffer.append(padNumber(regionCode, 2));
                                buffer.append("\n");
                            }
                        }
                    }
                }
                synchronized (writer) {
                    writer.write(buffer.toString());
                }
            }


            writer.flush();
//            writer.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("Thread done! " + (System.currentTimeMillis() - start) + " ms");
    }

    private static String padNumber(int number, int numberLength)
    {
        StringBuilder numberStr = new StringBuilder();

        numberStr.append(number);
        int padSize = numberLength - numberStr.length();
        for(int i = 0; i < padSize; i++) {
            numberStr.insert(0, '0');
        }

        return numberStr.toString();
    }
}