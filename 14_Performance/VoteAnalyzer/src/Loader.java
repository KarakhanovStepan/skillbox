import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 *  Without UNIQUE KEY name_date(name(50), birthDate):
 *
 *  limit 100 000 - 5 min 3 sec  - 12,349,146 voters in table
 *  limit 300 000 - 4 min 58 sec - 12,349,146 voters in table
 *
 *  With UNIQUE KEY name_date(name(50), birthDate):
 *
 *  limit 300 000 - ~30 min an it's just 7,199,943 voters
 */

public class Loader
{
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-1572M.xml";

//        DBConnection.getConnection();

        long start = System.currentTimeMillis();
        new Thread(new DBLoader()).start();
        parseFile(fileName);
        DBConnection.addQueryIntoList();
        DBConnection.setGoodForLoad(false);
//        DBConnection.executeMultiInsert();
//        DBConnection.closeConnection();
        long endSec = (System.currentTimeMillis() - start) / 1000;
        System.out.printf("Parsing time: %d min %d sec\n", endSec / 60, endSec - ((endSec / 60) * 60) );
        System.out.println(DBConnection.getQueryListSize() + " builders in query list");

    }

    private static void parseFile(String fileName) throws Exception
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File(fileName), handler);
    }

//    private static void findEqualVoters(Document doc) throws Exception
//    {
//        NodeList voters = doc.getElementsByTagName("voter");
//        int votersCount = voters.getLength();
//        for(int i = 0; i < votersCount; i++)
//        {
//            Node node = voters.item(i);
//            NamedNodeMap attributes = node.getAttributes();
//
//            String name = attributes.getNamedItem("name").getNodeValue();
//            String birthDay = attributes.getNamedItem("birthDay").getNodeValue();
////            Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());
//
//            DBConnection.countVoter(name, birthDay);
//
////            Voter voter = new Voter(name, birthDay);
////            Integer count = voterCounts.get(voter);
////            voterCounts.put(voter, count == null ? 1 : count + 1);
//        }
//        DBConnection.executeMultiInsert();
//    }
//
//    private static void fixWorkTimes(Document doc) throws Exception
//    {
//        NodeList visits = doc.getElementsByTagName("visit");
//        int visitCount = visits.getLength();
//        for(int i = 0; i < visitCount; i++)
//        {
//            Node node = visits.item(i);
//            NamedNodeMap attributes = node.getAttributes();
//
//            Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
//            Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
//            WorkTime workTime = voteStationWorkTimes.get(station);
//            if(workTime == null)
//            {
//                workTime = new WorkTime();
//                voteStationWorkTimes.put(station, workTime);
//            }
//            workTime.addVisitTime(time.getTime());
//        }
//    }
}