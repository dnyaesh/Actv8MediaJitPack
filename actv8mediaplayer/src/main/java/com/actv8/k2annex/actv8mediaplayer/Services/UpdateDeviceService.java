package com.actv8.k2annex.actv8mediaplayer.Services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Interfaces.Actv8CallbackInterface;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by neoforce-01 on 6/14/2018.
 */

public class UpdateDeviceService extends Service implements Actv8CallbackInterface.UpdateDeviceListener
{
    public int counter=0;
    Context applicationContext;
    Activity activityContext;

    public UpdateDeviceService(Context applicationContext, Activity activityContext)
    {
        super();
        this.applicationContext = applicationContext;
        this.activityContext = activityContext;
        //Log.i("HERE", "here I am!");
    }

    public UpdateDeviceService()
    {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Actv8MediaCoreLibrary.getInstance().addUpdateDeviceListner(this);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Actv8MediaCoreLibrary.getInstance().removeUpdateDeviceListner(this);
       /* Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);*/
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer()
    {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        /*//schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000);*/

        //schedule the timer, to wake up every 5 Mins
        timer.schedule(timerTask, new Date(), 1000 * 60 * 5);
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask()
    {
        timerTask = new TimerTask()
        {
            public void run()
            {
                Log.e("in timer", "********************* UpdateDeviceService **************************");
                Log.e("in timer", "********************* UpdateDeviceService **************************");
                Log.e("in timer", "********************* UpdateDeviceService **************************");
                Log.e("in timer", "********************* UpdateDeviceService **************************");


               // Log.e("in timer", "in timer ++++  "+ (counter++));

                Actv8MediaCoreLibrary.getInstance().updateDeviceInfo();


                Log.e("in timer", "********************* UpdateDeviceService **************************");
                Log.e("in timer", "********************* UpdateDeviceService **************************");
                Log.e("in timer", "********************* UpdateDeviceService **************************");
                Log.e("in timer", "********************* UpdateDeviceService **************************");
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask()
    {
        //stop the timer, if it's not already null
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onUpdateDeviceResponse(ServerResponseObject response)
    {

        if (response!=null)
        {
            if (response.getResponseCode()==200)
            {
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "\n\n\n\n\n\n\n");

                Log.e("UpdateDeviceService", response.getResponseMessage());

                Log.e("UpdateDeviceService", "\n\n\n\n\n\n\n");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");
                Log.e("UpdateDeviceService", "********************* SUCCESS UpdateDeviceService SUCCESS **************************");

            }
            else if(response.getResponseMessage()!=null && !response.getResponseMessage().isEmpty())
            {
                Log.e("UpdateDeviceService", response.getResponseMessage());
            }
            else
            {
                Log.e("UpdateDeviceService", "Something wen't wrong. Please try again later.");
            }
        }
        else
        {
            Log.e("UpdateDeviceService", "Something wen't wrong. Please try again later.");
        }
    }
}
