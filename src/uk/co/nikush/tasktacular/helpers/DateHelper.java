package uk.co.nikush.tasktacular.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DateHelper
{
    public static long now()
    {
        return (Calendar.getInstance().getTimeInMillis() / 1000L);
    }
    
    public static long makeTimestamp(int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return (c.getTimeInMillis() / 1000L);
    }
    
    public static String format(long timestamp)
    {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        return format.format(timestamp * 1000);
    }
    
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
