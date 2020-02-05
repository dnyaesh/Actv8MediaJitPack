package com.actv8.k2annex.actv8mediaplayer.WebViewActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actv8.k2annex.actv8mediaplayer.R;

import org.apache.http.util.EncodingUtils;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener
{

    WebView mainWebView;
    private String title;
    private String url;
    private String offer_code;
    private String postParameters;
    private boolean post = false;
    TextView toolbarTitle;
    //boolean isForUserData;



    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final Intent mainIntent = getIntent();

        title = mainIntent.getStringExtra("title");
        url = mainIntent.getParcelableExtra("url").toString();

        if(mainIntent.hasExtra("offer_code"))
        {
            offer_code = mainIntent.getStringExtra("offer_code");
        }
       /* final Button btnDeleteAccount = (Button) findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(this); */

        /*if(mainIntent.hasExtra("is_for_user_dada"))
        {
            isForUserData = true;
            CoreLibrary.getInstance().addGetUserDetailsListener(this);
        }
        else
        {
            isForUserData = false;
        }*/


        String postMethod = mainIntent.getStringExtra("post");
        if(postMethod != null && !postMethod.isEmpty())
        {
            url = mainIntent.getStringExtra("post");
            postParameters = mainIntent.getStringExtra("postParameters");
            post = true;
        }

        /*Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        //TextView mTitle = (TextView) toolBar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        toolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();

                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });*/

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);*/

        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlBack.setOnClickListener(this);

        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(title);
        toolbarTitle.setOnClickListener(this);

       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        mainWebView = (WebView) findViewById(R.id.my_webview);
        mainWebView.setVisibility(View.GONE);

        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.getSettings().setAppCacheEnabled(true);
        mainWebView.getSettings().setDomStorageEnabled(true);


        mainWebView.getSettings().setUseWideViewPort(true);
        mainWebView.getSettings().setLoadWithOverviewMode(true);
        mainWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, final int errorCode, final String description, final String failingUrl) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        View empty_list_refresh_progress = findViewById(R.id.empty_list_refresh_progress);
                        if (empty_list_refresh_progress != null)
                            empty_list_refresh_progress.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                runOnUiThread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        View empty_list_refresh_progress = findViewById(R.id.empty_list_refresh_progress);
                        if (empty_list_refresh_progress != null)
                            empty_list_refresh_progress.setVisibility(View.GONE);


                        Animation animFadein = new AlphaAnimation(0, 1);
                        animFadein.setInterpolator(new DecelerateInterpolator()); //add this
                        animFadein.setDuration(1000);



                        /*if(isForUserData)
                        {
                            btnDeleteAccount.setVisibility(View.VISIBLE);
                           // btnDeleteAccount.startAnimation(animFadein1);
                        }
                        else
                        {
                            btnDeleteAccount.setVisibility(View.GONE);
                        }*/

                        mainWebView.setVisibility(View.VISIBLE);
                        mainWebView.startAnimation(animFadein);
                    }
                });
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        View empty_list_refresh_progress = findViewById(R.id.empty_list_refresh_progress);
                        if (empty_list_refresh_progress != null)
                            empty_list_refresh_progress.setVisibility(View.VISIBLE);

                    }
                });
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("actv8://")) {
//                    CLog.i("WebView", "Intercept Url : " + url);
                    view.stopLoading();
                    Intent returnIntent = new Intent();

                    returnIntent.putExtra("url", Uri.parse(url));
                    returnIntent.setData(Uri.parse(url));

                    setResult(Activity.RESULT_OK, returnIntent);
//                    CLog.w("WebView", returnIntent.toString());

                    finish();
                    return false;
                }
                return false;
            }
        });

        if(post){
            byte[] post = EncodingUtils.getBytes(postParameters, "UTF-8");
            mainWebView.postUrl(url, post);
        }
        else {
            mainWebView.loadUrl(url);
        }
    }


  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_open_in_browser:
                openNativeBrowser();
                break;
           *//* case R.id.menu_web_back_btn:
                nav_back();
                break;
            case R.id.menu_web_next_btn:
                nav_forward();
                break;*//*
            case R.id.menu_web_refresh_btn:
                refresh();
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webmenu, menu);
        return true;
    }*/

    @Override
    public void onBackPressed()
    {

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        super.onResume();
        // MobiiApplication.getInstance().trackScreenView(title + " webView");
    }


    public boolean nav_back() {
        if (!mainWebView.canGoBack()) return false;
        mainWebView.goBack();
        return true;
    }

    public boolean nav_forward() {
        if (!mainWebView.canGoForward()) return false;
        mainWebView.goForward();
        return true;
    }

    public void refresh() {
        mainWebView.reload();
    }

    private void openNativeBrowser() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

        overridePendingTransition(0, android.R.anim.fade_out);

        Intent returnData = new Intent();
        setResult(RESULT_OK, returnData);
        finish();
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        if (id == R.id.rlBack)
        {
            onBackPressed();
        }
        else if (id == R.id.toolbarTitle)
        {
            if (offer_code != null && !offer_code.isEmpty())
            {
                Toast.makeText(WebViewActivity.this, "" + getResources().getString(R.string.coupon_code) + ": " + offer_code, Toast.LENGTH_LONG).show();
            }
        }
    }
}