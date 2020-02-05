package com.actv8.k2annex.onescreenjwapp.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import bz.tsung.android.objectify.ObjectPreferenceLoader;
import me.actv8.core.Actv8User;

public class MyPreferences
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Mobii preferences";
    private static final String LOGGED_IN_USER = "LOGGED_IN_USER";
    private static final String JWT_TOKEN = "JWT_TOKEN";


    public MyPreferences(Context context)
    {
        try
        {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }
        catch (Exception ex)
        {
            Log.e("PreferenceManager", ""+ex.toString());
        }
    }

    public void setLoggedInUser(Actv8User actv8User)
    {
        new ObjectPreferenceLoader(_context, LOGGED_IN_USER, Actv8User.class).set(actv8User);
    }

    public Actv8User getLoggedInUser()
    {
        Actv8User loggedInUser = new ObjectPreferenceLoader(_context, LOGGED_IN_USER, Actv8User.class).get();
        return loggedInUser;
    }


    public void setJwtToken(String jwtToken)
    {
        if(editor!=null)
        {
            editor.putString(JWT_TOKEN, jwtToken);
            editor.commit();
        }
    }

    public String getJwtToken()
    {
        if(pref!=null)
        {
            return pref.getString(JWT_TOKEN, null);
        }
        else
        {
            return null;
        }
    }
}
