package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.DeviceObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.google.gson.Gson;

/**
 * Created by mgriego on 9/7/17.
 */

public class UpdateDeviceTask extends AsyncTask<Context, Void, Void>
{
    ServerResponseObject response;
    Context mContext;

    @Override
    protected Void doInBackground(Context... params) {

        DeviceObject deviceObject = Actv8MediaCoreLibrary.getInstance().getDeviceObject();

        if(deviceObject!=null && deviceObject.getUuid()!=null && (!deviceObject.getUuid().isEmpty()))
        {
            Gson gson = new Gson();
            String json = gson.toJson(deviceObject);
            Log.e("UPDATE DEVICE TASK", "STARTED");
            Log.e("UPDATE DEVICE TASK", "STARTED");
            Log.e("================", "========================");
            Log.e("================", "========================");
            Log.e("================", "========================");
            Log.e("REQUEST JSON", ""+json);
            Log.e("================", "========================");
            Log.e("================", "========================");
            Log.e("================", "========================");
            Log.e("UPDATE DEVICE TASK", "STARTED");
            Log.e("UPDATE DEVICE TASK", "STARTED");
            Log.e("UPDATE DEVICE TASK", "STARTED");


            try
            {
                mContext = params[0];

                response = Actv8AsyncTask.actv8ServerRequest(Actv8MediaCoreLibrary.getInstance().getBaseUrl() + "device/" + deviceObject.getUuid(), "PUT", json);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        if (response != null && (response.getResponseCode()==200 || response.getResponseCode()==201))
        {
            Actv8MediaCoreLibrary.getInstance().onUpdateDeviceResponse(response);

           // SdkUtil.parseAppSettingData(mContext, response);

        }
    }



}
