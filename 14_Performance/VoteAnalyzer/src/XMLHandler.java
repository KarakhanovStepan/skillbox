import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler
{
    // When the limit is reached, the insert query added into list
    private int limit = 100000;
    private int count = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if(qName.equals("voter"))
        {
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");

            try {
                DBConnection.countVoter(name, birthDay);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count++;

            if(count == limit)
            {
                count = 0;
                DBConnection.addQueryIntoList();
            }
        }
    }
}
