package com.actv8.k2annex.onescreenjwapp.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.TestActivity;
import com.actv8.k2annex.actv8mediaplayer.Utils.MyNetworkManager;
import com.actv8.k2annex.actv8mediaplayer.VideoActivity.VideoActivity;
import com.actv8.k2annex.onescreenjwapp.R;

import me.actv8.core.CoreLibrary;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*CoreLibrary.getInstance().init(this);
        Actv8MediaCoreLibrary.getInstance().init(this);*/

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnTest:

                if(new MyNetworkManager(this).isNetworkAvailable())
                {
                    Intent videoIntent = new Intent(HomeActivity.this, TestActivity.class);
                    videoIntent.putExtra("VIDEO_URL", "http://pmd.cdn.turner.com/cnn/big/us/2019/10/29/jeanne-moos-military-dog-isis-raid-ebof-vpx.cnn_2861620_ios_5500.mp4");
                    startActivity(videoIntent);
                }
                else
                {
                    Toast.makeText(this, "Network unavailable, please try again later", Toast.LENGTH_SHORT).show();
                }

               /* Actv8MediaCoreLibrary.getInstance().getTimemarks();
                Actv8MediaCoreLibrary.getInstance().catchOffers("184451930", "4");*/
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Actv8MediaCoreLibrary.getInstance().shutdown();
    }
}
