package com.actv8.k2annex.actv8mediaplayer.AsyncTasks;

import android.util.Log;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.in;

/**
 * Created by mgriego on 5/3/17.
 */

public class Actv8AsyncTask
{

    public static ServerResponseObject actv8ServerRequest(String url, String requestType, String data)
            throws JsonSyntaxException,
            JsonIOException,
            ClassNotFoundException,
            IOException
    {

        HttpURLConnection conn = null;
        ServerResponseObject responseObject = new ServerResponseObject();


        try
        {
            URL urls = new URL(url);

            conn = (HttpURLConnection) urls.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod(requestType);
            conn.setDoInput(true);
            if(!requestType.equals("GET") && !requestType.equals("DELETE"))
            {
                conn.setDoOutput(true);
            }

            conn.setRequestProperty("Content-Type", "application/json");
            String strAPIKey = Actv8MediaCoreLibrary.getInstance().getApiKey();
            conn.setRequestProperty("X-API-Key", Actv8MediaCoreLibrary.getInstance().getApiKey());
            String sdkVersion = Actv8MediaCoreLibrary.getSdkVersion();
            conn.setRequestProperty("X-API-Version", Actv8MediaCoreLibrary.getSdkVersion());
            String strDeviceUuid = Actv8MediaCoreLibrary.getInstance().getDeviceObject().getUuid();
            conn.setRequestProperty("X-Device-uuid", Actv8MediaCoreLibrary.getInstance().getDeviceObject().getUuid());
            //conn.setRequestProperty("X-Device-uuid", "b488d9d9-c7c0-4e6f-b7c0-30c80e62e0bd");
            if(Actv8MediaCoreLibrary.getInstance().getUserJWT() != null)
            {
                String strToken = Actv8MediaCoreLibrary.getInstance().getUserJWT();
                Log.e("***TOKEN SENT***", ""+strToken);
                conn.setRequestProperty("Authorization", "Bearer " + Actv8MediaCoreLibrary.getInstance().getUserJWT());
            }
            conn.connect();

            if(data != null)
            {
                Log.e("data", data);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                os.close();
            }

            try
            {
                responseObject.setResponseCode(conn.getResponseCode());
                responseObject.setResponseMessage(conn.getResponseMessage());
                if (responseObject.getResponseCode() == HttpURLConnection.HTTP_OK || responseObject.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED || responseObject.getResponseCode() == HttpURLConnection.HTTP_CREATED)
                {
                    // Handle success response from server.

                    BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    in.close();
                    responseObject.setResponseBody(sBuilder.toString());
                }
                else
                {
                    // Handle actual error response from server.

                    BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    in.close();
                    responseObject.setErrorBody(sBuilder.toString());


                    /*InputStream error = conn.getErrorStream();
                    InputStreamReader isw = new InputStreamReader(error);
                    int dta = isw.read();

                    String response ="";
                    while (dta != -1)
                    {
                        char current = (char) dta;
                        dta = isw.read();
                        System.out.print(current);
                        response = response+current;

                    }

                    System.out.print(response);*/
                }

            }
            catch (Exception e)
            {
                Log.e("Actv8AsyncTask", Log.getStackTraceString(e));
            }
            conn.disconnect();
            if (responseObject != null)
            {
                return responseObject;
            }
            else
            {
                throw (new IOException("request object is null"));
            }
        }
        catch (ConnectTimeoutException e)
        {
            throw new ConnectTimeoutException("Connection timed out");
        }
        catch (IOException e)
        {
            try
            {
                responseObject.setResponseCode(conn.getResponseCode());
                responseObject.setResponseMessage(conn.getResponseMessage());
                BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null)
                {
                    sBuilder.append(line + "\n");
                }

                in.close();
                responseObject.setResponseBody(sBuilder.toString());
            }
            catch (IOException ex)
            {
                throw ex;
            }
            catch (Exception en)
            {
                try
                {
                    throw (en);
                }
                catch (Exception e1)
                {
                    Log.e("Actv8AsyncTask", Log.getStackTraceString(e));
                }
            }

        }
        catch (Exception e)
        {
            try
            {
                throw (e);
            }
            catch (Exception e1)
            {
                Log.e("Actv8AsyncTask", Log.getStackTraceString(e));
            }
        }
        finally
        {
            if (conn != null)
            {
                conn.disconnect();
            }
        }
        if (responseObject != null)
        {
            return responseObject;
        }
        else
        {
            throw (new IOException("request object is null"));
        }
    }
}
