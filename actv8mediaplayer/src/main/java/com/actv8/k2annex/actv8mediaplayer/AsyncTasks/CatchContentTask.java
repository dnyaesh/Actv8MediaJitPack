package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ContentObject;
import com.actv8.k2annex.actv8mediaplayer.Model.DeviceObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by mgriego on 5/11/17.
 */

public class CatchContentTask extends AsyncTask<String, Void, Void>
{

    ServerResponseObject response;
    String type;

    @Override
    protected Void doInBackground(String... params)
    {
        if (params.length > 1)
        {
            String externalId = params[0];
            //String uuid = "77314694"; //"24468329";
            type = params[1];
            DeviceObject deviceObject = Actv8MediaCoreLibrary.getInstance().getDeviceObject();
           // deviceObject.setUuid("b488d9d9-c7c0-4e6f-b7c0-30c80e62e0bd");
            Gson gson = new Gson();
            String json = gson.toJson(deviceObject);
            Log.e("CATCH CONTENT TASK", "STARTED, " + externalId + ", " + type);
            try
            {
                String testUrl = Actv8MediaCoreLibrary.getInstance().getBaseUrl() + type + "/" + externalId + "/catch";
                response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + type + "/" + externalId + "/catch", "POST", json);
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

        if (response != null)
        {
            Log.e("CATCH RESP BODY", "~" + response.getResponseBody());
            Log.e("CATCH RESP CODE", "" + response.getResponseCode());
            Log.e("CATCH RESP MESSAGE", ""+response.getResponseMessage());
            if (response.getResponseCode() == 200)
            {
                Gson gson = new Gson();
                String responseJsonString = response.getResponseBody();
                JSONArray responseJson = null;
                ArrayList<ContentObject> contentList = new ArrayList<>();
                try
                {
                    responseJson = new JSONArray(responseJsonString);
                    for (int i = 0; i < responseJson.length(); i++)
                    {
                        JSONObject contentJson = responseJson.getJSONObject(i);
                        ContentObject caughtContent = gson.fromJson(contentJson.toString(), ContentObject.class);
                        contentList.add(caughtContent);
                    }
                }
                catch (JSONException e)
                {
                    //CoreLibrary.getInstance().onCaughtContent(response, null);
                    //Actv8MediaCoreLibrary.getInstance().onOfferDetected(response, null);
                    e.printStackTrace();
                }

                //CoreLibrary.getInstance().onCaughtContent(response, contentList);
                //Actv8MediaCoreLibrary.getInstance().onOfferDetected(response, contentList);
            }
            else
            {
                //CoreLibrary.getInstance().onCaughtContent(response, null);
               // Actv8MediaCoreLibrary.getInstance().onOfferDetected(response, null);
            }
        }
        else
        {
            //CoreLibrary.getInstance().onCaughtContent(null, null);
            //Actv8MediaCoreLibrary.getInstance().onOfferDetected(null, null);
        }
    }
}
