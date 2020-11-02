import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Voter
{
    private char[] name;
    private Date birthDay;

    public Voter(String name, Date birthDay)
    {
        this.name = name.toCharArray();
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object obj)
    {
        Voter voter = (Voter) obj;
        return Arrays.equals(name, voter.getName()) && birthDay.equals(voter.birthDay);
    }

    @Override
//    public int hashCode()
//    {
//        long code = name.hashCode() + birthDay.hashCode();
//        while(code > Integer.MAX_VALUE) {
//            code = code/10;
//        }
//        return (int) code;
//    }

    public String toString()
    {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        String sName = "";
        for (char ch: name) {
            sName += ch;
        }
        return sName + " (" + dayFormat.format(birthDay) + ")";
    }

    public char[] getName()
    {
        return name;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }
}
