package SearchTwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Stefan Anders Mellem on 4/9/14.
 */
public class TweetBinner {

    private static final long MS_IN_DAY = 1000*60*60*24;
    private static SimpleDateFormat dateNoTime = new SimpleDateFormat("yyyy-MM-dd");

    /*
     date in form "yyyy-MM-dd"
     */
    private static long getMarketOpen(String date){
        //append the hour, minute, and timezone
        TimeZone et = TimeZone.getTimeZone("America/New_York");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd::HH-mm z");

        //System.out.println("Timezone: " + et.getDisplayName());

        date+="::09-30 "+et.getDisplayName();
        Date dt = new Date();
        try {
            dt = formatter.parse(date);
            if (et.inDaylightTime(dt))
                return dt.getTime()-et.getDSTSavings();
            else
                return dt.getTime();
        }
        catch (ParseException pe){
            pe.printStackTrace();
            return -1;
        }
    }

    /*
     date in form "yyyy-MM-dd"
     */
    private static long getMarketClose(String date){
        //append the hour, minute, and timezone
        TimeZone et = TimeZone.getTimeZone("America/New_York");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd::HH-mm z");

        //System.out.print("Timezone: ");
        //System.out.println(et.getDisplayName());

        date+="::16-00 "+et.getDisplayName();
        Date dt;
        try {
            dt = formatter.parse(date);
            return dt.getTime();
        }
        catch (ParseException pe){
            pe.printStackTrace();
            return -1;
        }
    }

    public static int getTweetBinFlag(long timestamp){
        String day = dateNoTime.format(new Date(timestamp));
        if (timestamp < getMarketOpen(day))
            return -1;
        else if (timestamp < getMarketClose(day))
            return 0;
        else
            return 1;
    }

    public static String getDateBin(long timestamp){
        String day = dateNoTime.format(new Date(timestamp));
        if (timestamp < getMarketOpen(day))
            return dateNoTime.format(new Date(timestamp))+"::0";
        else if (timestamp < getMarketClose(day))
            return dateNoTime.format(new Date(timestamp))+"::1";
        else
            return dateNoTime.format(new Date(timestamp+MS_IN_DAY))+"::0";
    }
}
