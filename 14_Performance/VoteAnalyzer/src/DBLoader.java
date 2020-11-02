import java.sql.SQLException;

public class DBLoader implements Runnable
{
    @Override
    public void run()
    {
        long start = System.currentTimeMillis();
        System.out.println("Start thread!");

        try {
            while (DBConnection.isGoodForLoad() || DBConnection.getQueryListSize() > 0) {
                System.out.println(DBConnection.getQueryListSize() + " check");
//                Thread.sleep(1);
                if(DBConnection.getQueryListSize() > 0) {
                    System.out.println("Loading...");
                    DBConnection.executeMultiInsert();
                }
            }

            long endSec = (System.currentTimeMillis() - start) / 1000;
            System.out.printf("Loading time: %d min %d sec\n", endSec / 60, endSec - ((endSec / 60) * 60) );

            DBConnection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
