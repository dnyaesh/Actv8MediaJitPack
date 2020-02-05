package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.actv8.k2annex.actv8mediaplayer.Utils.ApiConstants;

import java.io.IOException;

public class GetTimeMarksTask extends AsyncTask<Void, Void, Void>
{
    ServerResponseObject response;

    @Override
    protected Void doInBackground(Void... params)
    {
        Log.e("GetAndroidPassById TASK", "STARTED");

        try
        {
            String testUrl = Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "timemarks?vast_url="+ ApiConstants.VAST_TAG;
            response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "timemarks?vast_url="+ ApiConstants.VAST_TAG, "GET", null);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        Actv8MediaCoreLibrary.getInstance().onTimemarksResponse(response);
    }
}