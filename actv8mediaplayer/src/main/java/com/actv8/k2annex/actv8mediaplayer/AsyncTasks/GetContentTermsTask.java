package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ContentTermsObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.google.gson.Gson;

import java.io.IOException;


/**
 * Created by mgriego on 5/15/17.
 */

public class GetContentTermsTask extends AsyncTask<Integer, Void, Void>
{

    ServerResponseObject response;

    @Override
    protected Void doInBackground(Integer... params)
    {
        if (params.length > 0)
        {
            int id = params[0];
            Log.e("GET CONTENT TERMS TASK", "STARTED");
            try
            {
                response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "content-terms/" + id, "GET", null);
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
        else
        {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        if (response != null && response.getResponseCode() == 200)
        {
            Gson gson = new Gson();
            String responseJsonString = response.getResponseBody();
            ContentTermsObject contentTermsObject = gson.fromJson(responseJsonString, ContentTermsObject.class);
            Actv8MediaCoreLibrary.getInstance().onGetContentTerms(response, contentTermsObject.getBody());

            Log.e("CONTENT TERMS RESP BODY", "~" + response.getResponseBody());
            Log.e("CONTENT TERMS RESP CODE", "" + response.getResponseCode());
            Log.e("CONTENT TERMS RESP MSG",""+response.getResponseMessage());
        }
        else
        {
            Actv8MediaCoreLibrary.getInstance().onGetContentTerms(response, null);
        }
    }
}
