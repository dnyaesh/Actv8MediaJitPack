package com.actv8.k2annex.actv8mediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.CatchContentTask;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.CatchOffersTask;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.CreateAndroidPass;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.GetAndroidPassById;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.GetContentTermsTask;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.GetTimeMarksTask;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.UpdateContentStatusTask;
import com.actv8.k2annex.actv8mediaplayer.AsyncTasks.UpdateDeviceTask;
import com.actv8.k2annex.actv8mediaplayer.Interfaces.Actv8CallbackInterface;
import com.actv8.k2annex.actv8mediaplayer.Model.Actv8User;
import com.actv8.k2annex.actv8mediaplayer.Model.ContentObject;
import com.actv8.k2annex.actv8mediaplayer.Model.DeviceObject;
import com.actv8.k2annex.actv8mediaplayer.Model.Server;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.actv8.k2annex.actv8mediaplayer.Utils.ApiConstants;
import com.actv8.k2annex.actv8mediaplayer.Utils.GPSTracker;
import com.actv8.k2annex.actv8mediaplayer.Utils.SdkContants;
import com.actv8.k2annex.actv8mediaplayer.Utils.SdkUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import bz.tsung.android.objectify.ObjectPreferenceLoader;

import static android.content.Context.MODE_PRIVATE;

public class Actv8MediaCoreLibrary implements Actv8CallbackInterface.OnUpdateContentStatusListener, Actv8CallbackInterface.OnGetContentTermsListener,
        Actv8CallbackInterface.AndroidPassByIdListener, Actv8CallbackInterface.CatchOffersResponseListener, Actv8CallbackInterface.OnCaughtContentListener,
        Actv8CallbackInterface.UpdateDeviceListener, Actv8CallbackInterface.TimemarksResponseListener, Actv8CallbackInterface.CreateAndroidPassListener
{

    private static Server selectedServer = null;
    private static Actv8MediaCoreLibrary instance;

    private static DeviceObject deviceObject;

    private Context mContext;

    //private static String sdkVersion = "4.3";
    private static String sdkVersion = "4.4";

    private static String userJWT;
    private static Actv8User currentUser;

    private static HashSet<Actv8CallbackInterface.OnUpdateContentStatusListener> onUpdateContentStatusListener = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.OnGetContentTermsListener> onGetContentTermsListeners = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.AndroidPassByIdListener> androidPassByIdListeners = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.CatchOffersResponseListener> catchOffersResponseListeners = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.OnCaughtContentListener> onCaughtContentListeners = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.UpdateDeviceListener> updateDeviceListeners = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.TimemarksResponseListener> timemarksResponseListeners = new HashSet<>();
    private static HashSet<Actv8CallbackInterface.CreateAndroidPassListener> createAndroidPassListeners = new HashSet<>();

    private static String baseUrl;
    private static String apiKey;

    private Actv8MediaCoreLibrary() {

    }

    /**
     * This method returns an instance of the WalletCoreLibrary
     *
     * @return An instance of WalletCoreLibrary
     */
    public static Actv8MediaCoreLibrary getInstance() {
        if (instance == null) {
            instance = new Actv8MediaCoreLibrary();
        }
        return instance;
    }

    public static String getSdkVersion() {
        return sdkVersion;
    }


    public Server getSelectedServer()
    {
        return selectedServer;
    }

    public void setSelectedServer(Server selectedServer)
    {
        this.selectedServer = selectedServer;
    }


    public void shutdown()
    {

        Log.e("***************", "******************************");
        Log.e("***************", "******************************");
        Log.e("***************", "******************************");
        Log.e("WalletCoreLibrary", "shutdown() Called");
        Log.e("WalletCoreLibrary", "shutdown() Called");
        Log.e("WalletCoreLibrary", "shutdown() Called");
        Log.e("WalletCoreLibrary", "shutdown() Called");
        Log.e("***************", "******************************");
        Log.e("***************", "******************************");
        Log.e("***************", "******************************");

        //refreshDeviceLocation();
        /*UpdateDeviceTask updateDeviceTask = new UpdateDeviceTask();
        updateDeviceTask.execute(mContext);*/
        Gson gson = new Gson();

        SharedPreferences sharedPref = mContext.getSharedPreferences(SdkContants.ACTV8_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();

        if (currentUser != null)
        {
            edit.putString(SdkContants.ACTV8_USER, gson.toJson(currentUser));
        }


        edit.putString("Actv8DeviceObject", gson.toJson(deviceObject));
        edit.putString("Actv8UserJWT", userJWT);
        edit.apply();

        Log.e("***************", "******************************");
        Log.e("***************", "******************************");
        Log.e("***************", "******************************");
        Log.e("WalletCoreLibrary", "shutdown() Device Object saved successfully");
        Log.e("WalletCoreLibrary", "shutdown() Device Object saved successfully");
        Log.e("WalletCoreLibrary", "shutdown() Device Object saved successfully");
        Log.e("WalletCoreLibrary", "shutdown() Device Object saved successfully");
        Log.e("***************", "******************************");
        Log.e("***************", "******************************");
        Log.e("***************", "******************************");

    }

    /**
     * The first method that should be called to initialize the WalletCoreLibrary. Only required to be
     * called once per application start.
     *
     * @param context Application context the library will use for permission checking and storing
     *                data in shared prefs.
     */
    public void init(Context context)
    {
        mContext = context;

        //Get selected server from preference
        selectedServer = new ObjectPreferenceLoader(context, SdkContants.SELECTED_SERVER, new TypeToken<Server>(){}.getType()).get();

        if(selectedServer == null)
        {
            selectedServer = new Server();
            selectedServer.setBaseUrl(context.getResources().getString(R.string.actv8_base_url));
            selectedServer.setApiKey(context.getResources().getString(R.string.actv8_api_key));
            selectedServer.setUserDataUrl(context.getResources().getString(R.string.actv8_user_data_base_url));
            selectedServer.setSelected(true);
            if(selectedServer.getBaseUrl().equals(ApiConstants.PRODUCTION_BASE_URL))
            {
                selectedServer.setId(1);
                //selectedServer.setName(context.getResources().getString(R.string.p));
            }
            else if(selectedServer.getBaseUrl().equals(ApiConstants.STAGING_BASE_URL))
            {
                selectedServer.setId(2);
                //selectedServer.setName(context.getResources().getString(R.string.staging));
            }
            else if(selectedServer.getBaseUrl().equals(ApiConstants.DEVELOPMENT_BASE_URL))
            {
                selectedServer.setId(3);
                //selectedServer.setName(getResources().getString(R.string.development));
            }
            else if(selectedServer.getBaseUrl().equals(ApiConstants.DEV_PARTNER_BASE_URL))
            {
                selectedServer.setId(4);
                //selectedServer.setName(getResources().getString(R.string.devpartners));
            }
            else if(selectedServer.getBaseUrl().equals(ApiConstants.VYNAMIC_BASE_URL))
            {
                selectedServer.setId(5);
                //selectedServer.setName(getResources().getString(R.string.vynamic));
            }
            else if(selectedServer.getBaseUrl().equals(ApiConstants.PRE_PROD_BASE_URL))
            {
                selectedServer.setId(6);
                //selectedServer.setName(getResources().getString(R.string.vynamic));
            }

            // Save selected server object in to the preferences
            new ObjectPreferenceLoader(context, SdkContants.SELECTED_SERVER, Server.class).set(selectedServer);

            //Server testSelectedServer = new ObjectPreferenceLoader(context, SdkContants.SELECTED_SERVER, new TypeToken<Server>(){}.getType()).get();

            Log.e("Core-SDK init", "Selected server object created");
            Log.e("Core-SDK init", "Selected server object created");
            Log.e("Core-SDK init", "Selected server object created");
        }

        if(selectedServer!=null)
        {
            baseUrl = selectedServer.getBaseUrl();
            apiKey = selectedServer.getApiKey();

            if(baseUrl!=null && (baseUrl.contains("production") || baseUrl.contains("preprod")))
            {
                selectedServer.setAudioTriggerUrl("http://sonar.actv8technologies.com/fdb/");
            }
            else
            {
                selectedServer.setAudioTriggerUrl("http://aws.lb.tele.fm/fdb/");
            }
        }
        else
        {
            baseUrl = context.getResources().getString(R.string.actv8_base_url);
            apiKey = context.getResources().getString(R.string.actv8_api_key);
        }


       /* baseUrl = context.getResources().getString(R.string.actv8_base_url);
        apiKey = context.getResources().getString(R.string.actv8_api_key);*/


        SharedPreferences sharedPref = mContext.getSharedPreferences(SdkContants.ACTV8_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String user = sharedPref.getString(SdkContants.ACTV8_USER, null);

        if (user != null)
        {
            setCurrentUser(gson.fromJson(user, Actv8User.class));
        }
        String jwt = sharedPref.getString(SdkContants.ACTV8_JWT, null);

        if (jwt != null)
        {
            userJWT = jwt;
        }
        /*String device = sharedPref.getString(SdkContants.ACTV8_DEVICE, null);
        if (device != null)
        {
            deviceObject = gson.fromJson(device, DeviceObject.class);
            refreshDeviceLocation();
            UpdateDeviceTask updateDeviceTask = new UpdateDeviceTask();
            updateDeviceTask.execute(mContext);
        }
        else
        {
            deviceObject = generateDeviceObject();
            CreateDeviceTask createDeviceTask = new CreateDeviceTask();
            createDeviceTask.execute(mContext);
        }*/
    }

    public String getBaseUrl()
    {
        if (selectedServer!=null)
        {
            return selectedServer.getBaseUrl();
        }
        else
        {
            return baseUrl;
        }
    }

    public String getApiKey()
    {
        if (selectedServer!=null)
        {
            return selectedServer.getApiKey();
        }
        else
        {
            return apiKey;
        }
    }

    public DeviceObject getDeviceObject()
    {

        if (deviceObject == null)
        {
            deviceObject = generateDeviceObject();
        }
        else
        {
            refreshDeviceLocation();
            deviceObject.setSdk_version(getSdkVersion());
        }
        return deviceObject;
    }

    private void refreshDeviceLocation()
    {
        try
        {
            if (deviceObject != null)
            {
                deviceObject.setSdk_version(getSdkVersion());

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        deviceObject.setLatitude("" + location.getLatitude());
                        deviceObject.setLongitude("" + location.getLongitude());

                        DeviceObject.Placemark placemark = SdkUtil.getPlacemarkFromLatLng(mContext, location.getLatitude(), location.getLongitude());
                        deviceObject.setPlacemark(placemark);
                    } else {
                        GPSTracker gpsTracker = new GPSTracker(mContext);

                        if (gpsTracker.getIsGPSTrackingEnabled()) {

                            deviceObject.setLatitude("" + gpsTracker.latitude);
                            deviceObject.setLongitude("" + gpsTracker.longitude);

                            DeviceObject.Placemark placemark = SdkUtil.getPlacemarkFromLatLng(mContext, gpsTracker.latitude, gpsTracker.longitude);
                            deviceObject.setPlacemark(placemark);
                        }
                    }

                }
            }
        }
        catch (Exception ex)
        {

        }

    }

    private DeviceObject generateDeviceObject()
    {
        DeviceObject appObject = new DeviceObject();
        try
        {
            SharedPreferences prefs = mContext.getSharedPreferences(SdkContants.DEVICE_UUID_PREF, MODE_PRIVATE);
            String existingDeviceUuid = prefs.getString(SdkContants.DEVICE_UUID, null);
            //String existingDeviceUuid = CoreSdkUtil.getProperty(SdkContants.DeviceUUID, mContext);

            // String existingDeviceUuid = MyFileHelper.readUuid(SdkContants.DEVICE_UUID_FILE, mContext);

            Log.e("$$$$$$$$$$$$$$$$$", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Log.e("$$$$$$$$$$$$$$$$$", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Log.e("$$$$$$$$$$$$$$$$$", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Log.e("WalletCoreLibrary", "Existing Device-UUID = "+existingDeviceUuid);
            Log.e("WalletCoreLibrary", "Existing Device-UUID = "+existingDeviceUuid);
            Log.e("WalletCoreLibrary", "Existing Device-UUID = "+existingDeviceUuid);
            Log.e("$$$$$$$$$$$$$$$$$", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Log.e("$$$$$$$$$$$$$$$$$", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Log.e("$$$$$$$$$$$$$$$$$", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");


            //if (existingDeviceUuid == null || existingDeviceUuid.equals(SdkContants.TEST))
            if (existingDeviceUuid == null || existingDeviceUuid.isEmpty())
            {
                // Generate new Device UUID
                String strNewDeviceUuid = UUID.randomUUID().toString();
                appObject.setUuid(strNewDeviceUuid);

                // Save the newly created device_uuid in to the preferences
                //CoreSdkUtil.setProperty(SdkContants.DeviceUUID, strNewDeviceUuid, mContext);

                //MyFileHelper.writeUuid(SdkContants.DEVICE_UUID_FILE, strNewDeviceUuid, mContext);



                SharedPreferences.Editor edit = prefs.edit();
                edit.putString(SdkContants.DEVICE_UUID, strNewDeviceUuid);
                edit.apply();

                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");
                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");
                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");
                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");

                Log.e("generateDeviceObject()", "existingDeviceUuid = "+strNewDeviceUuid);
                Log.e("generateDeviceObject()", "existingDeviceUuid = "+strNewDeviceUuid);

                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");
                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");
                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");
                Log.e("generateDeviceObject()", "=================NEW UUID CREATED=============================");

            }
            else
            {
                appObject.setUuid(existingDeviceUuid);
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");

                Log.e("generateDeviceObject()", "existingDeviceUuid = "+existingDeviceUuid);
                Log.e("generateDeviceObject()", "existingDeviceUuid = "+existingDeviceUuid);

                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
                Log.e("generateDeviceObject()", "=================CONTINUED WITH EXISTING UUID=============================");
            }


            appObject.setSdk_version(getSdkVersion());
            appObject.setOs_version("" + android.os.Build.VERSION.SDK_INT);
            appObject.setDevice_model(android.os.Build.MODEL);
            appObject.setDevice_manufacturer(Build.MANUFACTURER);

            String serial = "";
            try
            {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ril.serialnumber");
                if (serial == null || serial.equals(""))
                {
                    serial = android.os.Build.SERIAL;
                }
            }
            catch (Exception ignored)
            {
                serial = android.os.Build.SERIAL;
            }
            if (!serial.isEmpty())
            {
                appObject.setDevice_identifier(serial);
            }

            if (mContext != null)
            {
                Locale locale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                {
                    locale = mContext.getResources().getConfiguration().getLocales().get(0);
                }
                else
                {
                    locale = mContext.getResources().getConfiguration().locale;
                }
                appObject.setLocale(locale.toString());

                String carrierName = "";
                try
                {
                    TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    carrierName = manager.getNetworkOperatorName();
                }
                catch (Exception e)
                {

                }
                if (!carrierName.isEmpty())
                {
                    appObject.setCarrier_name(carrierName);
                }
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null)
                    {
                        appObject.setLatitude("" + location.getLatitude());
                        appObject.setLongitude("" + location.getLongitude());

                        DeviceObject.Placemark placemark = SdkUtil.getPlacemarkFromLatLng(mContext, location.getLatitude(), location.getLongitude());
                        appObject.setPlacemark(placemark);
                    }
                    else
                    {
                        GPSTracker gpsTracker = new GPSTracker(mContext);

                        if (gpsTracker.getIsGPSTrackingEnabled())
                        {
                            appObject.setLatitude("" + gpsTracker.latitude);
                            appObject.setLongitude("" + gpsTracker.longitude);

                            DeviceObject.Placemark placemark = SdkUtil.getPlacemarkFromLatLng(mContext, gpsTracker.latitude, gpsTracker.longitude);
                            appObject.setPlacemark(placemark);
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {

        }

        return appObject;
    }

    /**
     * If there is a user logged in or stored, returns the JWT token used to API calls for this user.
     *
     * @return A string representation of the current users JWT token for API authorization.
     */
    public String getUserJWT() {
        return userJWT;
    }

    /**
     * Method to update the current User JWT.
     *
     * @param userJWT The updated JWT to set for the current user.
     */
    public void setUserJWT(String userJWT) {
        this.userJWT = userJWT;
    }

    /**
     * Gives you the {@link Actv8User User} that is currently logged in.  Will return
     * null if no user is logged in.
     *
     * @return {@link Actv8User UserObject} representing the current user. Null if no
     * user is logged in.
     */
    public Actv8User getCurrentUser()
    {
        return currentUser;
    }


    public void setCurrentUser(Actv8User currentUser)
    {
        this.currentUser = currentUser;
    }


    /**
     * Method to update the status of a caught ContentObject
     *
     * @param content The content to update
     */
    public void updateContentStatus(ContentObject content) {
       /* if (currentUser == null) {
            Log.e("InsertContent", "No user currently signed in");
            return;
        }*/
        UpdateContentStatusTask insertContentTask = new UpdateContentStatusTask();
        insertContentTask.execute(content);
    }

    /**
     * Method that is called upon completion of a content status update call
     *
     * @param response The server response from the updateContentStatus call
     */
    @Override
    public void onUpdateContentStatus(ServerResponseObject response)
    {
        for (Actv8CallbackInterface.OnUpdateContentStatusListener insertListener : onUpdateContentStatusListener)
        {
            insertListener.onUpdateContentStatus(response);
        }
    }

    /**
     * Method to add a listener for when updateContentStatus is complete
     *
     * @param insertListener The listener to be added
     */
    public void addOnUpdateContentStatusListener(Actv8CallbackInterface.OnUpdateContentStatusListener insertListener) {
        onUpdateContentStatusListener.add(insertListener);
    }

    /**
     * Method to remove a listener from being alerted on task completion
     *
     * @param insertListener The listener to be removed
     */
    public void removeOnUpdateContentStatusListener(Actv8CallbackInterface.OnUpdateContentStatusListener insertListener) {
        onUpdateContentStatusListener.remove(insertListener);
    }



    public void getContentTerms(int contentID)
    {
        GetContentTermsTask getContentTermsTask = new GetContentTermsTask();
        getContentTermsTask.execute(contentID);
    }

    @Override
    public void onGetContentTerms(ServerResponseObject response, String contentTerms)
    {
        for (Actv8CallbackInterface.OnGetContentTermsListener getContentTermsListener : onGetContentTermsListeners)
        {
            getContentTermsListener.onGetContentTerms(response, contentTerms);
        }
    }

    public void addOnGetContentTermsListner(Actv8CallbackInterface.OnGetContentTermsListener getUserContentListener) {
        onGetContentTermsListeners.add(getUserContentListener);
    }

    public void removeOnGetContentTermsListener(Actv8CallbackInterface.OnGetContentTermsListener getUserContentListener) {
        onGetContentTermsListeners.remove(getUserContentListener);
    }

    /**
     *
     * Method to get app Android Pass By contentId
     *
     */
    public void getAndroidPassById(String contentId, int userId)
    {
        if(contentId!=null && !contentId.isEmpty() && userId>0)
        {
            GetAndroidPassById androidPassById = new GetAndroidPassById();
            androidPassById.execute(contentId, ""+userId);
        }
    }

    /**
     *
     * Method to add AndroidPassByIdListener
     *
     * @param androidPassByIdListener The listener to be added
     */
    public void addAndroidPassByIdListener(Actv8CallbackInterface.AndroidPassByIdListener androidPassByIdListener)
    {
        androidPassByIdListeners.add(androidPassByIdListener);
    }

    /**
     * Method to remove AndroidPassByIdListener
     *
     * @param androidPassByIdListener The listener to be removed
     */
    public void removeAndroidPassByIdListener(Actv8CallbackInterface.AndroidPassByIdListener androidPassByIdListener)
    {
        androidPassByIdListeners.remove(androidPassByIdListener);
    }

    @Override
    public void onAndroidPassByIdResponse(ServerResponseObject response)
    {
        for (Actv8CallbackInterface.AndroidPassByIdListener androidPassByIdListener : androidPassByIdListeners)
        {
            androidPassByIdListener.onAndroidPassByIdResponse(response);
        }
    }


    @Override
    public void onCatchOffersResponse(ServerResponseObject response)
    {
        for (Actv8CallbackInterface.CatchOffersResponseListener listener : catchOffersResponseListeners)
        {
            listener.onCatchOffersResponse(response);
        }
    }

    /**
     * Method to add a listener for when content is caught from the {CatchContentTask CatchContentTask}
     *
     * @param catchOffersResponseListener Listener to be added.
     */
    public void addCatchOfferListener(Actv8CallbackInterface.CatchOffersResponseListener catchOffersResponseListener) {
        catchOffersResponseListeners.add(catchOffersResponseListener);
    }


    public void removeCatchOfferdListener(Actv8CallbackInterface.CatchOffersResponseListener catchOffersResponseListener) {
        catchOffersResponseListeners.remove(catchOffersResponseListener);
    }

    public void catchOffers(String strAdId, String strStartAt) {
        CatchOffersTask catchContentTask = new CatchOffersTask();
        catchContentTask.execute(strAdId, strStartAt);
    }


    @Override
    public void onCaughtContent(ServerResponseObject response, ArrayList<ContentObject> content) {
        for (Actv8CallbackInterface.OnCaughtContentListener listener : onCaughtContentListeners) {
            listener.onCaughtContent(response, content);
        }
    }

    /**

    public void addOnCaughtContentListener(Actv8CallbackInterface.OnCaughtContentListener caughtContentListener) {
        onCaughtContentListeners.add(caughtContentListener);
    }

    /**
     * Method to remove a listerner to prevent further alerts for caught content.
     *
     * @param caughtContentListener Listener to be removed.
     */
    public void removeOnCaughtContentListener(Actv8CallbackInterface.OnCaughtContentListener caughtContentListener)
    {
        onCaughtContentListeners.remove(caughtContentListener);
    }

    public void updateDeviceInfo()
    {
        /*if (currentUser == null)
        {
            Log.e("updateDeviceInfo()", "No user currently signed in");
            return;
        }*/

        refreshDeviceLocation();
        UpdateDeviceTask updateDeviceTask = new UpdateDeviceTask();
        updateDeviceTask.execute(mContext);
    }


    @Override
    public void onUpdateDeviceResponse(ServerResponseObject response)
    {
        for (Actv8CallbackInterface.UpdateDeviceListener updateDeviceListener : updateDeviceListeners)
        {
            updateDeviceListener.onUpdateDeviceResponse(response);
        }
    }

    /**
     * Method to add a Update Device listener
     *
     * @param updateDeviceListener The listener to be added
     */
    public void addUpdateDeviceListner(Actv8CallbackInterface.UpdateDeviceListener updateDeviceListener)
    {
        updateDeviceListeners.add(updateDeviceListener);
    }

    /**
     * Method to remove Update Device listener
     *
     * @param updateDeviceListener The listener to be removed
     */
    public void removeUpdateDeviceListner(Actv8CallbackInterface.UpdateDeviceListener updateDeviceListener)
    {
        updateDeviceListeners.remove(updateDeviceListener);
    }


    // Get Timemarks
    public void getTimemarks()
    {
        GetTimeMarksTask getTimeMarksTask = new GetTimeMarksTask();
        getTimeMarksTask.execute();
    }


    @Override
    public void onTimemarksResponse(ServerResponseObject response)
    {
        for (Actv8CallbackInterface.TimemarksResponseListener timemarksResponseListener : timemarksResponseListeners)
        {
            timemarksResponseListener.onTimemarksResponse(response);
        }
    }

    /**
     * Method to add a Timemarks response listener
     *
     * @param timemarksResponseListener The listener to be added
     */
    public void addTimemarkResponseListener(Actv8CallbackInterface.TimemarksResponseListener timemarksResponseListener)
    {
        timemarksResponseListeners.add(timemarksResponseListener);
    }

    /**
     * Method to remove Timemark response listener
     *
     * @param timemarksResponseListener The listener to be removed
     */
    public void removeTimemarkResponseListener(Actv8CallbackInterface.TimemarksResponseListener timemarksResponseListener)
    {
        timemarksResponseListeners.remove(timemarksResponseListener);
    }

    // Create Android Pass
    public void createAndroidPass(String contentId, int userId)
    {
        if(contentId!=null && !contentId.isEmpty() && userId>0)
        {
            CreateAndroidPass createAndroidPass = new CreateAndroidPass();
            createAndroidPass.execute(contentId, ""+userId);
        }
    }

    /**
     * Method to add a CreateAndroidPassListener
     *
     * @param createAndroidPassListener The listener to be added
     */
    public void addCreateAndroidPassListener(Actv8CallbackInterface.CreateAndroidPassListener createAndroidPassListener)
    {
        createAndroidPassListeners.add(createAndroidPassListener);
    }

    /**
     * Method to remove CreateAndroidPassListener
     *
     * @param createAndroidPassListener The listener to be removed
     */
    public void removeCreateAndroidPassListener(Actv8CallbackInterface.CreateAndroidPassListener createAndroidPassListener)
    {
        createAndroidPassListeners.remove(createAndroidPassListener);
    }

    @Override
    public void onCreateAndroidPassResponse(ServerResponseObject response)
    {
        for (Actv8CallbackInterface.CreateAndroidPassListener createAndroidPassListener : createAndroidPassListeners)
        {
            createAndroidPassListener.onCreateAndroidPassResponse(response);
        }
    }



}
