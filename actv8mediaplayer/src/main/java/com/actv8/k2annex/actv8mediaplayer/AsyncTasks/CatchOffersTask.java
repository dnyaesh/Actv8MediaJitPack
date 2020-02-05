package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.actv8.k2annex.actv8mediaplayer.Utils.ApiConstants;

import java.io.IOException;

public class CatchOffersTask extends AsyncTask<String , String, Void>
{
    ServerResponseObject response;

    @Override
    protected Void doInBackground(String... params)
    {
        Log.e("GetAndroidPassById TASK", "STARTED");

       // media/{adId}/catch?timemark_start_at={start_at_from_last_call}

        try
        {
            String strAdId = params[0];
            String strStartAt = params[1];

            String testUrl = Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "media/"+strAdId+"/catch?timemark_start_at="+strStartAt;
            response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "media/"+strAdId+"/catch?timemark_start_at="+strStartAt, "POST", null);
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

        Actv8MediaCoreLibrary.getInstance().onCatchOffersResponse(response);
    }
}