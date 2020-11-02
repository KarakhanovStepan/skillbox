import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection
{
    private static Connection connection;

//    private static String dbName = "voters?serverTimezone=UTC";
//    private static String dbUser = "root";
//    private static String dbPass = "rootroot";

    private static String url = "jdbc:mysql://localhost:3306/voters?serverTimezone=UTC";
    private static String name = "root";
    private static String pass = "rootroot";

    private static boolean goodForLoad = true;

    private static List<StringBuilder> queryList = new ArrayList<>();
    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
//                connection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/" + dbName +
//                    "?user=" + dbUser + "&password=" + dbPass);

                connection = DriverManager.getConnection(url, name, pass);

                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert() throws SQLException {
        if (queryList.size() > 0) {
            String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
                    "VALUES " + queryList.get(0).toString() +
                    "ON DUPLICATE KEY UPDATE `count`=`count` + 1";

            DBConnection.getConnection().createStatement().execute(sql);

            queryList.remove(0);
        }
    }

    public static void countVoter(String name, String birthDay) throws SQLException
    {
        birthDay = birthDay.replace('.', '-');

        insertQuery.append((insertQuery.length() == 0 ? "": ",") +
                "('" + name + "', '" + birthDay + "', 1)");
//        String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
//                "VALUES('" + name + "', '" + birthDay + "', 1) " +
//                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";
//
//        DBConnection.getConnection().createStatement().execute(sql);

//        String sql = "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
//        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
//        if(!rs.next())
//        {
//            DBConnection.getConnection().createStatement()
//                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
//                            name + "', '" + birthDay + "', 1)");
//        }
//        else {
//            Integer id = rs.getInt("id");
//            DBConnection.getConnection().createStatement()
//                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
//        }
//        rs.close();
    }

    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }

    public static void closeConnection() throws SQLException {
        goodForLoad = false;
        connection.close();
    }

    public static boolean isGoodForLoad() {
        return goodForLoad;
    }

    public static void setGoodForLoad(boolean goodForLoad) {
        DBConnection.goodForLoad = goodForLoad;
    }

    public static void addQueryIntoList()
    {
        queryList.add(insertQuery);
        insertQuery = new StringBuilder();
    }

    public static int getQueryListSize()
    {
        return queryList.size();
    }
}
