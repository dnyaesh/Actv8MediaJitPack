package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;

import java.io.IOException;

public class CreateAndroidPass extends AsyncTask<String, String, Void>
{
    ServerResponseObject response;

    @Override
    protected Void doInBackground(String... params)
    {
        Log.e("GetAndroidPassById TASK", "STARTED");

        try
        {
            String contentId = params[0];
            String userId = params[1];

            String testUrl = Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "user/"+userId+"/content/"+contentId+"/androidpass/";

            response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "user/"+userId+"/content/"+contentId+"/androidpass/", "POST", null);
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

        Actv8MediaCoreLibrary.getInstance().onCreateAndroidPassResponse(response);
    }
}