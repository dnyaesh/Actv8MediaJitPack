package com.actv8.k2annex.actv8mediaplayer.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by neoforce-01 on 3/7/2018.
 */

public class DateTimeHelper
{

    //
    // Method to return Date difference Current-Date and End-Date(i.e. Number of days between 2 dates)
    //
    public static String getRemainingDays(String strEndDate)   //"end_date":"2018-04-23 00:00:00",
    {

       // strEndDate = "2019-05-08 11:59:59";

        boolean isHrs = false;
        long remainingDays = -1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try
        {
            Date startDate = Calendar.getInstance().getTime();
            String strStartDate = simpleDateFormat.format(startDate);

            startDate = simpleDateFormat.parse(strStartDate);
            Date endDate = simpleDateFormat.parse(strEndDate);

           /* String startDtArr[] = strStartDate.split(" ");
            String endDtArr[] = strEndDate.split(" ");

            if(startDtArr!=null && startDtArr.length==2 && endDtArr!=null && endDtArr.length==2)
            {
                if(startDtArr[0].equals(endDtArr[0]))
                {
                    return 1;
                }
            }*/


            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : "+ endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            remainingDays = different / daysInMilli;
            Log.e("Rem Days: ", ""+remainingDays);

            if(remainingDays==0)
            {
                isHrs = true;
                remainingDays = different/hoursInMilli;
            }

            /*if(remainingDays==0 && different>0)
            {
                remainingDays = 1;
            }*/

            /*different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(isHrs)
        {
            return remainingDays+" h";
        }
        else if (remainingDays<14)
        {
            return remainingDays+" d";
        }
        else
        {
            long remainingMonths = remainingDays/30;
            if (remainingMonths==0)
            {
                remainingMonths = 1;
            }
            return remainingMonths+" m";
        }
    }


    //
    // Method to return Date difference between From-Date to Current-Date(i.e. Number of days between 2 dates)
    //
    public static long getDateDifference(String strFromDate)   //"strFromDate":"2018-04-23",
    {
        long remainingDays = -1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date currentDate = Calendar.getInstance().getTime();
            String strStartDate = simpleDateFormat.format(currentDate);
            currentDate = simpleDateFormat.parse(strStartDate);

            Date fromDate = simpleDateFormat.parse(strFromDate);

            //milliseconds
            long different =  currentDate.getTime() - fromDate.getTime();

            System.out.println("startDate : " + currentDate);
            System.out.println("endDate : "+ fromDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            remainingDays = different / daysInMilli;
            Log.e("Rem Days: ", ""+remainingDays);

            /*different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return remainingDays;
    }


    //
    // Method to return Date difference Current-Date and End-Date(i.e. Number of days between 2 dates)
    //
    public static long getRemainingHours(String strEndDate)   //"end_date":"2018-04-23 00:00:00",
    {
        //strEndDate = "2018-03-16 00:00:00";
        long elapsedHours = -1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try
        {
            Date startDate = Calendar.getInstance().getTime();
            String strStartDate = simpleDateFormat.format(startDate);

            startDate = simpleDateFormat.parse(strStartDate);
            Date endDate = simpleDateFormat.parse(strEndDate);

            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            elapsedHours = TimeUnit.MILLISECONDS.toHours(different);
            Log.e("Hrs= ", ""+elapsedHours);

          /*  System.out.println("startDate : " + startDate);
            System.out.println("endDate : "+ endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long remainingDays = different / daysInMilli;
            Log.e("Rem Days: ", ""+remainingDays);

            different = different % daysInMilli;

            elapsedHours = (remainingDays * 24) + (different / hoursInMilli);*/


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return elapsedHours;
    }

    public static String getCurrentDate(String strDateFormat)
    {
        String strCurrentDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);
        try
        {
            Date startDate = Calendar.getInstance().getTime();
            strCurrentDate = simpleDateFormat.format(startDate);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return strCurrentDate;
    }

    public static String changeDateFormat(String strDate, String strFromFormat, String strToFormat)
    {
        String strOutputDate = strDate;

        try
        {
            SimpleDateFormat fromFormat = new SimpleDateFormat(strFromFormat);
            SimpleDateFormat toFormat = new SimpleDateFormat(strToFormat);
            try
            {
                Date date = fromFormat.parse(strDate);
                strOutputDate = toFormat.format(date);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception ex)
        {
           Log.e("changeDateFormat", ex.toString());
        }

        if(strOutputDate==null)
        {
            strOutputDate= "";
        }

        return strOutputDate;
    }


    public static int getAge(String strDOB) // strDOB formste
    {
        int age = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try
        {
            Calendar dob = Calendar.getInstance();
            dob.setTime(sdf.parse(strDOB));// all done

            Calendar today = Calendar.getInstance();

            age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

           /* if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
            {
                age--;
            }*/

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return age;
    }

}
