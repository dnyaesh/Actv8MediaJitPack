package com.actv8.k2annex.actv8mediaplayer.VideoActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.Interfaces.Actv8CallbackInterface;
import com.actv8.k2annex.actv8mediaplayer.Model.ContentObject;
import com.actv8.k2annex.actv8mediaplayer.Model.ServerResponseObject;
import com.actv8.k2annex.actv8mediaplayer.Model.TimemarkResponse;
import com.actv8.k2annex.actv8mediaplayer.R;
import com.actv8.k2annex.actv8mediaplayer.Utils.ApiConstants;
import com.actv8.k2annex.actv8mediaplayer.Utils.AppConstants;
import com.actv8.k2annex.actv8mediaplayer.Utils.ContentStatusValues;
import com.actv8.k2annex.actv8mediaplayer.Utils.MyDlgManager;
import com.actv8.k2annex.actv8mediaplayer.Utils.MyNetworkManager;
import com.actv8.k2annex.actv8mediaplayer.WebViewActivity.WebViewActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.CompleteEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.ads.AdBreak;
import com.longtailvideo.jwplayer.media.ads.AdSource;
import com.longtailvideo.jwplayer.media.ads.Advertising;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bz.tsung.android.objectify.ObjectPreferenceLoader;
//import me.actv8.sound_lib.Actv8SoundLib;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener, VideoPlayerEvents.OnCompleteListener, Actv8CallbackInterface.OnUpdateContentStatusListener,
        MyDlgManager.OfferDlgButtonClickListener, Actv8CallbackInterface.AndroidPassByIdListener, Actv8CallbackInterface.TimemarksResponseListener,
        Actv8CallbackInterface.CatchOffersResponseListener, Actv8CallbackInterface.CreateAndroidPassListener
        //Actv8CallbackInterface.OfferDetectionListener,
{
    JWPlayerView mPlayerView;
    Handler handler;
    TextView tvSaveAndSkip;
    Animation animFadeIn, animFadeOut;
    ContentObject detectedOffer;
    RelativeLayout rlProgress;
    String strGoogleWalletJwt;
    MyDlgManager myDlgManager;
    TimemarkResponse timemarkResponse;
    ArrayList<ContentObject> detectedOfferList;
    String strVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (getIntent().hasExtra("VIDEO_URL"))
        {
            strVideoUrl = getIntent().getStringExtra("VIDEO_URL");
        }

        if (strVideoUrl==null || strVideoUrl.isEmpty())
        {

            try
            {
                Toast.makeText(this, "Video url unavailable", Toast.LENGTH_SHORT).show();
                Thread.sleep(2000);
            }
            catch (Exception ex)
            {

            }

            finish();
        }

        // Add Audio offer detection listener
        //Actv8MediaCoreLibrary.getInstance().addOfferDetectedListener(this);
        Actv8MediaCoreLibrary.getInstance().addOnUpdateContentStatusListener(this);
        Actv8MediaCoreLibrary.getInstance().addAndroidPassByIdListener(this);
        Actv8MediaCoreLibrary.getInstance().addTimemarkResponseListener(this);
        Actv8MediaCoreLibrary.getInstance().addCatchOfferListener(this);
        Actv8MediaCoreLibrary.getInstance().addCreateAndroidPassListener(this);

        // Start listing Audio Triggers
        //Actv8SoundLib.getInstance().startListening(this);

        myDlgManager = new MyDlgManager();
        myDlgManager.setOfferDlgButtonClickListener(this);

        Typeface tfRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        // Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        animFadeIn = new AlphaAnimation(0, 1);
        animFadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        animFadeIn.setDuration(1000);

        animFadeOut = new AlphaAnimation(1, 0);
        animFadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        //animFadeOut.setStartOffset(500);
        animFadeOut.setDuration(500);

        rlProgress = (RelativeLayout) findViewById(R.id.rlProgress);

        tvSaveAndSkip = (TextView) findViewById(R.id.tvSaveAndSkip);
        tvSaveAndSkip.setTypeface(tfMedium);
        tvSaveAndSkip.setOnClickListener(this);

        mPlayerView = findViewById(R.id.jwplayer);
        mPlayerView.addOnCompleteListener(this);

        Actv8MediaCoreLibrary.getInstance().getTimemarks();

       /* List<AdBreak> adSchedule = new ArrayList<>();

        AdBreak adBreak = new AdBreak.Builder()
                //.tag(ApiConstants.VAST_TAG)
                .offset("5")
                .build();

        adSchedule.add(adBreak);

        Advertising advertising = new Advertising(AdSource.VAST, adSchedule);

        PlaylistItem playlistItem = new PlaylistItem.Builder()
                //.file("https://cdn.jwplayer.com/manifests/kQZvB6sI.m3u8")
                .file(strVideoUrl)
                .build();

        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(playlistItem);

        PlayerConfig config = new PlayerConfig.Builder()
                .playlist(playlist)
                .advertising(advertising)
                .autostart(true)
                .build();


        mPlayerView.setup(config);*/
    }

    private void showSaveToWalletButton()
    {
        double currentPosition = mPlayerView.getPosition();

        Toast.makeText(this, ""+currentPosition, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlayerView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayerView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();

        // Stop listing Audio Triggers
        //Actv8SoundLib.getInstance().stopListening(this);

        //Actv8MediaCoreLibrary.getInstance().removeOfferDetectedListener(this);
        Actv8MediaCoreLibrary.getInstance().removeOnUpdateContentStatusListener(this);
        Actv8MediaCoreLibrary.getInstance().removeAndroidPassByIdListener(this);
        Actv8MediaCoreLibrary.getInstance().removeTimemarkResponseListener(this);
        Actv8MediaCoreLibrary.getInstance().removeCatchOfferdListener(this);
        Actv8MediaCoreLibrary.getInstance().removeCreateAndroidPassListener(this);

        Actv8MediaCoreLibrary.getInstance().shutdown();

        //RESET CURRENTLY DETECTED OFFER LIST
        //new ObjectPreferenceLoader(this, AppConstants.CURRENT_OFFERS_LIST, ContentObject.class).set(null);
    }

    @Override
    public void onComplete(CompleteEvent completeEvent)
    {

        //handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onOfferDlgButtonClicked(int buttonNumber)
    {
        if(Actv8MediaCoreLibrary.getInstance().getCurrentUser()!=null && Actv8MediaCoreLibrary.getInstance().getCurrentUser().getPii()!=null)
        {
            switch (buttonNumber)
            {
                case 1:
                    if(detectedOffer.getRedemption()!=null && (detectedOffer.getRedemption().getUrl()!=null && !detectedOffer.getRedemption().getUrl().isEmpty()))
                    {
                        if(new MyNetworkManager(this).isNetworkAvailable())
                        {
                            // updateStatusFor = 1; // 1 = Redeem Online

                            // Update offer status to "REDEEMED_ONLINE"
                            detectedOffer.setStatus(ContentStatusValues.REDEEMED_ONLINE);
                            Actv8MediaCoreLibrary.getInstance().updateContentStatus(detectedOffer);
                            //AppHelper.updateLocalScratchersList(OfferDetailsBothActivity.this, selectedOffer);

                            // Redirect user to redeem url
                            Intent privacyIntent = new Intent(this, WebViewActivity.class);
                            privacyIntent.putExtra("url", Uri.parse(detectedOffer.getRedemption().getUrl()));

                            if(detectedOffer.getRedemption().getCode()!=null && (detectedOffer.getRedemption().getCode().getValue()!=null && !detectedOffer.getRedemption().getCode().getValue().isEmpty()))
                            {
                                privacyIntent.putExtra("title", ""+detectedOffer.getRedemption().getCode().getValue());
                                privacyIntent.putExtra("offer_code", ""+detectedOffer.getRedemption().getCode().getValue());

                                // Copy Offer Code To The Clip-board
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("OFFER_CODE", ""+detectedOffer.getRedemption().getCode().getValue());
                                clipboard.setPrimaryClip(clip);
                            }
                            else
                            {
                                privacyIntent.putExtra("title", getResources().getString(R.string.offer));
                            }

                            startActivity(privacyIntent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        else
                        {
                            //alertManager.showAlertOk(OfferDetailsBothActivity.this, getResources().getString(R.string.network_error), "Ok");
                            Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        //alertManager.showAlertOk(OfferDetailsBothActivity.this, getResources().getString(R.string.src_url_unavailable), "Ok");
                        Toast.makeText(this, getResources().getString(R.string.src_url_unavailable), Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if(detectedOffer.isAndroid_pass_enabled())
                    {
                        if(new MyNetworkManager(this).isNetworkAvailable())
                        {
                            rlProgress.setVisibility(View.VISIBLE);
                            Actv8MediaCoreLibrary.getInstance().getAndroidPassById(""+detectedOffer.getId(), Actv8MediaCoreLibrary.getInstance().getCurrentUser().getPii().getUser_id());
                        }
                        else
                        {
                            Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
            }
        }
        else
        {
            Toast.makeText(this, getResources().getString(R.string.not_logged_in_msg), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAndroidPassByIdResponse(ServerResponseObject response)
    {
        rlProgress.setVisibility(View.GONE);
        boolean isDone=false;

        String strErrorMessage= "";

        if(response!=null && response.getResponseBody()!=null && !response.getResponseBody().isEmpty())
        {
            try
            {
                JSONObject respJson = new JSONObject(response.getResponseBody());

                if(respJson.has("jwt"))
                {
                    JSONObject jwtJson = respJson.getJSONObject("jwt");

                    if(jwtJson.has("jwt"))
                    {
                        strGoogleWalletJwt = jwtJson.getString("jwt");
                        isDone= true;

                        if(strGoogleWalletJwt!=null && !strGoogleWalletJwt.isEmpty())
                        {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.android.com/payapp/savetoandroidpay/" + strGoogleWalletJwt));
                            startActivity(intent);
                        }
                        else
                        {
                            //alertManager.showAlertOk(OfferDetailsBothActivity.this, getResources().getString(R.string.invalid_jwt), getResources().getString(R.string.ok));
                            Toast.makeText(this, getResources().getString(R.string.invalid_jwt), Toast.LENGTH_SHORT).show();
                        }
                        //rlSaveToPhone.setVisibility(View.VISIBLE);
                        //Toast.makeText(ApiTestActivity.this, strGoogleWalletJwt, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            Log.e("Response", ""+response.getResponseBody());
        }
        else if (response != null && response.getErrorBody() != null && !response.getErrorBody().isEmpty())
        {
            try
            {
                JSONObject errorJson = new JSONObject(response.getErrorBody());

                if(!errorJson.isNull("message"))
                {
                    strErrorMessage = errorJson.getString("message");
                }
                else
                {
                    strErrorMessage = getResources().getString(R.string.unknown_error);
                }
            }
            catch (Exception ex)
            {
                strErrorMessage = getResources().getString(R.string.unknown_error);
            }

            // alertManager.showAlertOk(SignInEmailActivity.this, "" + strErrorMessage, getResources().getString(R.string.ok));
           // Toast.makeText(this, ""+strErrorMessage, Toast.LENGTH_SHORT).show();
        }
        else if(response!=null && response.getResponseMessage()!=null && !(response.getResponseMessage().isEmpty()))
        {
            Toast.makeText(this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }

        if (!isDone)
        {
            if(strErrorMessage!=null && !(strErrorMessage.isEmpty()))
            {
                Toast.makeText(this, strErrorMessage, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
            // finish();
        }

    }

   /* @Override
    public void onOfferDetected(ServerResponseObject response, ArrayList<ContentObject> content)
    {
        if(content!=null && content.size()>0)
        {
            // Display Auto save button
            tvSaveAndSkip.setVisibility(View.VISIBLE);
            tvSaveAndSkip.startAnimation(animFadeIn);

            detectedOffer = content.get(0);

            // Save Detected List in Preferences for later use
            //new ObjectPreferenceLoader(VideoActivity.this, AppConstants.CURRENT_OFFERS_LIST, ContentObject.class).set(content);
        }
        else if(response!=null && response.getResponseMessage()!=null && !(response.getResponseMessage().isEmpty()))
        {
            Toast.makeText(this, response.getResponseMessage(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Something wen't wrong. Please try again later.", Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    public void onUpdateContentStatus(ServerResponseObject serverResponseObject)
    {
        if(serverResponseObject!=null && serverResponseObject.getResponseCode()==200)
        {
            // DO NOTHING
        }
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == R.id.tvSaveAndSkip)
        {
            if(new MyNetworkManager(this).isNetworkAvailable())
            {
                if(Actv8MediaCoreLibrary.getInstance().getCurrentUser()!=null && Actv8MediaCoreLibrary.getInstance().getCurrentUser().getPii()!=null)
                {
                    rlProgress.setVisibility(View.VISIBLE);
                    Actv8MediaCoreLibrary.getInstance().createAndroidPass(""+detectedOffer.getId(), Actv8MediaCoreLibrary.getInstance().getCurrentUser().getPii().getUser_id());
                }
                else
                {
                    Toast.makeText(this, getResources().getString(R.string.not_logged_in_msg), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.rlProgress)
        {
            //DO NOTHING
        }
    }

    @Override
    public void onTimemarksResponse(ServerResponseObject response)
    {
        //TimemarkResponse
        rlProgress.setVisibility(View.GONE);

        if(response!=null && response.getResponseBody()!=null && !response.getResponseBody().isEmpty())
        {
            try
            {
                JSONObject mimemarkJson = new JSONObject(response.getResponseBody());
                Gson gson = new Gson();


                timemarkResponse = gson.fromJson(mimemarkJson.toString(), TimemarkResponse.class);

                if(timemarkResponse!=null && timemarkResponse.getTimemarks()!=null && timemarkResponse.getTimemarks().size()>0)
                {
                    List<AdBreak> adSchedule = new ArrayList<>();

                    for(int i=0; i<timemarkResponse.getTimemarks().size(); i++)
                    {
                        AdBreak adBreak = new AdBreak.Builder()
                                .tag(ApiConstants.VAST_TAG)
                                .offset(""+timemarkResponse.getTimemarks().get(i).getDuration())
                                .build();

                        adSchedule.add(adBreak);
                    }

                    Advertising advertising = new Advertising(AdSource.VAST, adSchedule);

                    PlaylistItem playlistItem = new PlaylistItem.Builder()
                            //.file("https://cdn.jwplayer.com/manifests/kQZvB6sI.m3u8")
                            .file(strVideoUrl)
                            .build();

                    List<PlaylistItem> playlist = new ArrayList<>();
                    playlist.add(playlistItem);

                    PlayerConfig config = new PlayerConfig.Builder()
                            .playlist(playlist)
                            .advertising(advertising)
                            .autostart(true)
                            .build();


                    mPlayerView.setup(config);

                    if(new MyNetworkManager(this).isNetworkAvailable())
                    {
                        Actv8MediaCoreLibrary.getInstance().catchOffers(""+timemarkResponse.getAdId(), ""+timemarkResponse.getTimemarks().get(0).getStart_at());
                    }
                }
                else
                {
                    Toast.makeText(this, "Time marks unavailable", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception ex)
            {
                Log.e("Exception", ""+ex);
            }
        }
    }

    @Override
    public void onCatchOffersResponse(ServerResponseObject response)
    {
        if(response!=null && response.getResponseBody()!=null && (!response.getResponseBody().isEmpty()))
        {
            try
            {
                JSONArray responseArr = new JSONArray(response.getResponseBody());
                if(responseArr!=null)
                {
                    Gson gson = new Gson();
                    detectedOfferList = new Gson().fromJson(responseArr.toString(), new TypeToken<List<ContentObject>>(){}.getType());

                    if (detectedOfferList!=null && detectedOfferList.size()>0)
                    {
                        detectedOffer = detectedOfferList.get(0);
                        // Display Auto save button
                        tvSaveAndSkip.setVisibility(View.VISIBLE);
                        tvSaveAndSkip.startAnimation(animFadeIn);
                    }
                    else
                    {
                        Toast.makeText(this, "No offer(s) found", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "No offer(s) found", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "No offer(s) found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateAndroidPassResponse(ServerResponseObject response)
    {
        if (response!=null && response.getResponseBody()!=null && (response.getResponseCode()==201 || response.getResponseCode()==200))
        {
            tvSaveAndSkip.setVisibility(View.GONE);
            tvSaveAndSkip.startAnimation(animFadeOut);

            myDlgManager.showOfferDlg(this, detectedOffer, "Buy Now");
        }
        else
        {
            Toast.makeText(this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
        }
    }
}