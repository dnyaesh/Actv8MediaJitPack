package com.actv8.k2annex.actv8mediaplayer.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dnyaneshwar on 2/1/2017.
 */

public class MyNetworkManager
{
    private Context _context;

    public MyNetworkManager(Context context){
        this._context = context;
    }


    public boolean isNetworkAvailable()
    {
        boolean isNetworkAvailable = false;
        if(_context!=null)
        {
            ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null)
            { // connected to the internet
                isNetworkAvailable = true;
            }
        }
        return isNetworkAvailable;
    }
}
