package com.actv8.k2annex.actv8mediaplayer.Interfaces;


import com.actv8.k2annex.actv8mediaplayer.Model.ContentObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mgriego on 5/8/17.
 */

public interface Actv8CallbackInterface
{
    interface OnUpdateContentStatusListener {   // OnInsertContentListener
        void onUpdateContentStatus(ServerResponseObject response);
    }

    interface OnGetContentTermsListener {
        void onGetContentTerms(ServerResponseObject response, String contentTerms);
    }

    interface CreateAndroidPassListener
    {
        void onCreateAndroidPassResponse(ServerResponseObject response);
    }

    interface AndroidPassByIdListener
    {
        void onAndroidPassByIdResponse(ServerResponseObject response);
    }

    interface OnCaughtContentListener {
        void onCaughtContent(ServerResponseObject response, ArrayList<ContentObject> content);
    }

    interface CatchOffersResponseListener
    {
        void onCatchOffersResponse(ServerResponseObject response);
    }

    interface UpdateDeviceListener
    {
        void onUpdateDeviceResponse(ServerResponseObject response);
    }

    interface TimemarksResponseListener
    {
        void onTimemarksResponse(ServerResponseObject response);
    }
}
