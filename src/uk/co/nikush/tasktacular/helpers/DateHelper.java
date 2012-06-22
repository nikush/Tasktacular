package uk.co.nikush.tasktacular.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Helper class to simplify working with dates.
 * 
 * @author  Nikush Patel
 */
public class DateHelper
{
    /**
     * Get the timestamp for the current date and time.
     * 
     * @return  Current timestamp
     */
    public static long now()
    {
        return (Calendar.getInstance().getTimeInMillis() / 1000L);
    }
    
    /**
     * Create a unix timestamp from separate date components.
     * 
     * @param   year
     * @param   month
     * @param   day
     * @return  The constructed timestamp
     */
    public static long makeTimestamp(int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return (c.getTimeInMillis() / 1000L);
    }
    
    /**
     * Format a timestamp into the format: "Day, DD Month YYYY".
     * 
     * @param   timestamp   The timestamp to use
     * @return  The formatted date
     */
    public static String format(long timestamp)
    {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        return format.format(timestamp * 1000);
    }
    
    /**
     * Cut up a timestamp into year, month and day.
     * 
     * @param   timestamp   The timestamp to use
     * @return  The separated time components
     */
    public static HashMap<String, Integer> disectTimestamp(long timestamp)
    {
        int year, month, day;
        
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp * 1000);
        
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("year", year);
        map.put("month", month);
        map.put("day", day);
        
        return map;
    }
}
