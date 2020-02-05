package com.actv8.k2annex.actv8mediaplayer.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;


import com.actv8.k2annex.actv8mediaplayer.Model.DeviceObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;


/**
 * Created by neoforce-01 on 9/7/2018.
 */

public class SdkUtil
{

    public static DeviceObject.Placemark getPlacemarkFromLatLng(Context context, double latitude, double longitude)
    {
        DeviceObject.Placemark placemark = null;
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try
        {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if(addresses!=null && addresses.size()>=1)
            {/*
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL*/

                placemark = new DeviceObject().new Placemark();

                placemark.setZIP(addresses.get(0).getPostalCode());
                placemark.setCity(addresses.get(0).getLocality());
                placemark.setName(addresses.get(0).getFeatureName());
                placemark.setState(addresses.get(0).getAdminArea());
                placemark.setCountry(addresses.get(0).getCountryName());
                placemark.setCountryCode(addresses.get(0).getCountryCode());
                placemark.setSubLocality(addresses.get(0).getSubLocality());
                placemark.setThoroughfare(addresses.get(0).getThoroughfare());
                placemark.setSubThoroughfare(addresses.get(0).getSubThoroughfare());

                if(addresses.get(0).getAddressLine(0)!=null)
                {
                    String[] strFormattedAddress = addresses.get(0).getAddressLine(0).split(",");
                    placemark.setFormattedAddressLines(strFormattedAddress);
                }
                placemark.setSubAdministrativeArea(addresses.get(0).getSubAdminArea());

            }
        }
        catch(Exception ex)
        {
            Log.e("getPlacemarkFromLatLng", ex.getMessage());
        }

        return placemark;
    }


    public static void parseAppSettingData(Context mContext, ServerResponseObject serverResponse)
    {
        if(mContext!=null)
        {
            try
            {
                if(serverResponse.getResponseBody()!=null && !serverResponse.getResponseBody().isEmpty())
                {
                    JSONObject respJson = new JSONObject(serverResponse.getResponseBody());

                    if(!respJson.isNull("application"))
                    {
                        JSONObject appJson = respJson.getJSONObject("application");

                        if(!appJson.isNull("settings"))
                        {
                            JSONObject settingsJson = appJson.getJSONObject("settings");

                            if(!settingsJson.isNull("triggers"))
                            {
                                JSONObject triggersJson = settingsJson.getJSONObject("triggers");

                                // Parse Audio Trigger Setting
                                if(!triggersJson.isNull("audio"))
                                {
                                    JSONObject audioJson = triggersJson.getJSONObject("audio");

                                    if(!audioJson.isNull("time_delay_until_next_request"))
                                    {
                                        //new ApplicationSettings(mContext).setAudioTimeDelayUntilNextRequest(audioJson.getInt("time_delay_until_next_request"));
                                    }
                                }

                                // Parse Beacon Trigger Setting
                                if(!triggersJson.isNull("beacons"))
                                {
                                    JSONObject beaconJson = triggersJson.getJSONObject("beacons");

                                    if(!beaconJson.isNull("time_delay_until_next_request"))
                                    {
                                        //new ApplicationSettings(mContext).setBeaconTimeDelayUntilNextRequest(beaconJson.getInt("time_delay_until_next_request"));
                                    }
                                }

                                // Parse Geo-Fence Trigger Setting
                                if(!triggersJson.isNull("geofences"))
                                {
                                    JSONObject geoFenceJson = triggersJson.getJSONObject("geofences");

                                    if(!geoFenceJson.isNull("time_delay_until_next_request"))
                                    {
                                       // new ApplicationSettings(mContext).setGeofenceTimeDelayUntilNextRequest(geoFenceJson.getInt("time_delay_until_next_request"));
                                    }

                                    if(!geoFenceJson.isNull("max_distance_from_closest_geofence"))
                                    {
                                       // new ApplicationSettings(mContext).setMaxDistanceFromClosestGeofence(geoFenceJson.getInt("max_distance_from_closest_geofence"));
                                    }

                                    if(!geoFenceJson.isNull("distance_threshold_until_next_request"))
                                    {
                                        //new ApplicationSettings(mContext).setDistanceThresholdUntilNextRequest(geoFenceJson.getInt("distance_threshold_until_next_request"));
                                    }
                                }
                            }

                            // Parse User-Data page Url

                            /*if(!settingsJson.isNull("user_data"))
                            {
                                JSONObject userDataJson = settingsJson.getJSONObject("user_data");

                                if(!userDataJson.isNull("url"))
                                {
                                    new ApplicationSettings(mContext).setUserDataUrl(userDataJson.getString("url"));
                                }
                            }*/
                            if(!settingsJson.isNull("user_data_url"))
                            {
                                //new ApplicationSettings(mContext).setUserDataUrl(settingsJson.getString("user_data_url"));
                            }

                        }
                    }
                }
            }
            catch(Exception ex)
            {
                Log.e("SdkUtil", ""+ex.toString());
            }
        }
    }

}
