package com.actv8.k2annex.actv8mediaplayer.Model;

/**
 * Created by neoforce-01 on 2/28/2019.
 */

public class Actv8User
{
    UserDetails topDetails;
    UserPii pii;
    String password;



    public UserDetails getTopDetails()
    {
        return topDetails;
    }

    public void setTopDetails(UserDetails topDetails)
    {
        this.topDetails = topDetails;
    }

    public UserPii getPii() {
        return pii;
    }

    public void setPii(UserPii pii) {
        this.pii = pii;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
