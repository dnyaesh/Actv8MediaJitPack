package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ContentObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.google.gson.Gson;

import java.io.IOException;


/**
 * Created by mgriego on 5/11/17.
 */

public class UpdateContentStatusTask extends AsyncTask<ContentObject, Void, Void>
{

    ServerResponseObject response;
    Gson gson;

    @Override
    protected Void doInBackground(ContentObject... params)
    {
        if (params.length > 0)
        {
            ContentObject contentObject = params[0];
            gson = new Gson();
            String json = "{\"status\":" + contentObject.getStatus() + "}";
            Log.e("UPDATE CONTENT TASK", "STARTED");
            try
            {
                if(Actv8MediaCoreLibrary.getInstance().getCurrentUser()!=null)
                {
                    String strTestUrl = Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "user/" + Actv8MediaCoreLibrary.getInstance().getCurrentUser().getTopDetails().getId() + "/content/" + contentObject.getId();

                    response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "user/" + Actv8MediaCoreLibrary.getInstance().getCurrentUser().getTopDetails().getId() + "/content/" + contentObject.getId(), "PATCH", json);
                }
                else
                {
                    String strTestUrl = Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "device/"+ Actv8MediaCoreLibrary.getInstance().getDeviceObject().getUuid()+"/content/"+ contentObject.getId();
                    response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "/device/"+ Actv8MediaCoreLibrary.getInstance().getDeviceObject().getUuid()+"/content/"+ contentObject.getId(), "PUT", json);
                }
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

        Actv8MediaCoreLibrary.getInstance().onUpdateContentStatus(response);
    }
}
