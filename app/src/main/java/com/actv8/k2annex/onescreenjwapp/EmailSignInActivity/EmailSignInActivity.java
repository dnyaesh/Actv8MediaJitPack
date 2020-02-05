package com.actv8.k2annex.onescreenjwapp.EmailSignInActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actv8.k2annex.actv8mediaplayer.Actv8MediaCoreLibrary;
import com.actv8.k2annex.actv8mediaplayer.VideoActivity.VideoActivity;
import com.actv8.k2annex.onescreenjwapp.Common.MyPreferences;
import com.actv8.k2annex.onescreenjwapp.HomeActivity.HomeActivity;
import com.actv8.k2annex.onescreenjwapp.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.actv8.core.Actv8User;
import me.actv8.core.CoreLibrary;
import me.actv8.core.UserDetails;
import me.actv8.core.UserPii;
import me.actv8.core.objects.ServerResponseObject;
import me.actv8.core.util.Actv8CallbackInterface;
import me.actv8.core.util.LoginMethods;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EmailSignInActivity extends AppCompatActivity implements View.OnClickListener, Actv8CallbackInterface.OnUserLoginListener, Actv8CallbackInterface.GetUserDetailsListener, Actv8CallbackInterface.GetUserProfileListener
{
    EditText etEmail, etPassword;
    RelativeLayout rlProgress;
    String TAG;
    Actv8User actv8User;
    com.actv8.k2annex.actv8mediaplayer.Model.Actv8User actv8MediaUser;
    static final int REQUEST_PERMISSION_COED = 333;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_in);

        TAG = EmailSignInActivity.class.getName();
        //Init Core Lib
        CoreLibrary.getInstance().init(this);
        Actv8MediaCoreLibrary.getInstance().init(this);

        if(Actv8MediaCoreLibrary.getInstance().getCurrentUser()!=null)
        {
            final Intent i = new Intent(EmailSignInActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        // printHashKey(this);
        initViews();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // Remove listeners
        CoreLibrary.getInstance().removeOnUserLoginListener(this);
        CoreLibrary.getInstance().removeGetUserDetailsListener(this);
        CoreLibrary.getInstance().removeGetUserProfileListener(this);
    }

    private void initViews()
    {
        // Add required listeners
        CoreLibrary.getInstance().addOnUserLoginListener(this);
        CoreLibrary.getInstance().addGetUserDetailsListener(this);
        CoreLibrary.getInstance().addGetUserProfileListener(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        rlProgress = (RelativeLayout) findViewById(R.id.rlProgress);

    }

    //
    // Validate user inputs
    //
    private boolean validateInput()
    {
        String strEmaiId = etEmail.getText().toString();
        if(strEmaiId==null || strEmaiId.isEmpty())
        {
            Toast.makeText(EmailSignInActivity.this, "Email should not be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String strPassword = etPassword.getText().toString();
        if(strPassword==null || strPassword.isEmpty())
        {
            Toast.makeText(EmailSignInActivity.this, "Password should not be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnSignIn:
                if(checkPermission())
                {
                    if(validateInput())
                    {
                        rlProgress.setVisibility(View.VISIBLE);

                        // Create and init UserObject
                        UserPii userPii = new UserPii();

                        userPii.setLogin_method(LoginMethods.EMAIL);
                        userPii.setEmail(etEmail.getText().toString());

                        // Call login task
                        CoreLibrary.getInstance().login(userPii, etPassword.getText().toString());
                    }
                }
                else
                {
                    requestPermission();
                }

                break;
        }
    }

    //
    // Login callback from ACTV8me server
    //
    @Override
    public void onUserLogin(ServerResponseObject response)
    {
        rlProgress.setVisibility(View.GONE);

        // In 4.3 SDK Login-Endpoint mainly call to get "access_token". No other fields in the response are required.
        // We will call other end-points to get complete user details

        // Check for success
        if (response != null && response.getResponseCode() == 200)
        {
            boolean isAllDone = false;
            if(response.getResponseBody()!=null && (!response.getResponseBody().isEmpty()))
            {
                try
                {
                    // Parse access token
                    JSONObject loginJson = new JSONObject(response.getResponseBody());

                    if(!loginJson.isNull("access_token"))
                    {
                        String strJwtToken = loginJson.getString("access_token");

                        // Set access token in core sdk using below method. This step is very important,
                        // without this step login process is incomplete.
                        CoreLibrary.getInstance().setUserJWT(strJwtToken);
                        Actv8MediaCoreLibrary.getInstance().setUserJWT(strJwtToken);
                        isAllDone =true;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // Check if everything worked fine.
                if(isAllDone)
                {
                    // Call below end point to get User-Id. This method do not requires any parameter, sdk internally uses the "acc
                    rlProgress.setVisibility(View.VISIBLE);
                    CoreLibrary.getInstance().getUserDetails();
                }
                else
                {
                    Toast.makeText(EmailSignInActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(EmailSignInActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
            }
        }
        else if (response != null && response.getErrorBody() != null && !response.getErrorBody().isEmpty())
        {
            String strErrorMessage= "";
            try
            {
                JSONObject errorJson = new JSONObject(response.getErrorBody());

                if(!errorJson.isNull("message"))
                {
                    strErrorMessage = errorJson.getString("message");
                }
                else
                {
                    strErrorMessage = "Unknown Error";
                }
            }
            catch (Exception ex)
            {
                strErrorMessage = "Unknown Error";
            }

            Toast.makeText(EmailSignInActivity.this, strErrorMessage, Toast.LENGTH_SHORT).show();
        }
        else if(response!=null && response.getResponseMessage()!=null)
        {
            Toast.makeText(EmailSignInActivity.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(EmailSignInActivity.this, "Something wen't wrong. Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    public void printHashKey(Context pContext)
    {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e("TAG", "printHashKey()", e);
        }
        catch (Exception e)
        {
            Log.e("TAG", "printHashKey()", e);
        }
    }

    @Override
    public void onUserDetailsResponse(ServerResponseObject response)
    {
        // This endpoint returns partial user details like id, client_is, login_method, categories and other

        rlProgress.setVisibility(View.GONE);
        if(response!=null && response.getResponseCode()==200 && response.getResponseBody()!=null && !response.getResponseBody().isEmpty())
        {
            boolean isAllDone = false;
            try
            {
                // Parse response
                JSONObject detailJson = new JSONObject(response.getResponseBody());
                Gson gson = new Gson();

                UserDetails userDetails = gson.fromJson(detailJson.toString(), UserDetails.class);

                com.actv8.k2annex.actv8mediaplayer.Model.UserDetails actv8MediaUserDetails= gson.fromJson(detailJson.toString(), com.actv8.k2annex.actv8mediaplayer.Model.UserDetails.class);

                if (userDetails!=null)
                {
                    // Set this partial details to the main actv8 user object.
                    actv8User = new Actv8User();
                    actv8User.setTopDetails(userDetails);


                    // Set partial details to the Actv8 Media User
                    actv8MediaUser = new com.actv8.k2annex.actv8mediaplayer.Model.Actv8User();
                    actv8MediaUser.setTopDetails(actv8MediaUserDetails);

                    rlProgress.setVisibility(View.VISIBLE);
                    isAllDone = true;

                    // Call below end-point to get user profile details.
                    CoreLibrary.getInstance().getUserProfile(""+userDetails.getId());
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            if(!isAllDone)
            {
                Toast.makeText(EmailSignInActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        }
        else if (response != null && response.getErrorBody() != null && !response.getErrorBody().isEmpty())
        {
            String strErrorMessage= "";
            try
            {
                JSONObject errorJson = new JSONObject(response.getErrorBody());

                if(!errorJson.isNull("message"))
                {
                    strErrorMessage = errorJson.getString("message");
                }
                else
                {
                    strErrorMessage = "Unknown Error";
                }
            }
            catch (Exception ex)
            {
                strErrorMessage = "Unknown Error";
            }

            Toast.makeText(EmailSignInActivity.this, ""+strErrorMessage, Toast.LENGTH_SHORT).show();
        }
        else if(response.getResponseMessage()!=null && !(response.getResponseMessage().isEmpty()))
        {
            Toast.makeText(EmailSignInActivity.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(EmailSignInActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUserProfileResponse(ServerResponseObject response)
    {
        rlProgress.setVisibility(View.GONE);

        rlProgress.setVisibility(View.GONE);
        if(response!=null && response.getResponseCode()==200 && response.getResponseBody()!=null && !response.getResponseBody().isEmpty())
        {
            boolean isAllDone = false;

            try
            {
                JSONObject profileJson = new JSONObject(response.getResponseBody());

                Gson gson = new Gson();

                UserPii userPii = gson.fromJson(profileJson.toString(), UserPii.class);

                com.actv8.k2annex.actv8mediaplayer.Model.UserPii actv8MediaUserPii = gson.fromJson(profileJson.toString(), com.actv8.k2annex.actv8mediaplayer.Model.UserPii.class);

                if (userPii!=null)
                {
                    isAllDone =true;
                    actv8User.setPii(userPii);
                    actv8User.getPii().setPassword(etPassword.getText().toString());
                    actv8User.setPassword(etPassword.getText().toString());
                    actv8User.getPii().setLogin_method(actv8User.getTopDetails().getLogin_method());
                    CoreLibrary.getInstance().setCurrentUser(actv8User);

                    actv8MediaUser.setPii(actv8MediaUserPii);
                    actv8MediaUser.getPii().setPassword(etPassword.getText().toString());
                    actv8MediaUser.setPassword(etPassword.getText().toString());
                    actv8MediaUser.getPii().setLogin_method(actv8MediaUser.getTopDetails().getLogin_method());
                    Actv8MediaCoreLibrary.getInstance().setCurrentUser(actv8MediaUser);

                    new MyPreferences(this).setJwtToken(CoreLibrary.getInstance().getUserJWT());
                    new MyPreferences(this).setLoggedInUser(CoreLibrary.getInstance().getCurrentUser());

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if (isAllDone)
            {
                // Call the callback method in HomeActivity to inform login complete
                // CoreLibrary.getInstance().onLoginConfirm();

                final Intent i = new Intent(EmailSignInActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(EmailSignInActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
            }
        }
        else if (response != null && response.getErrorBody() != null && !response.getErrorBody().isEmpty())
        {
            String strErrorMessage= "";
            try
            {
                JSONObject errorJson = new JSONObject(response.getErrorBody());

                if(!errorJson.isNull("message"))
                {
                    strErrorMessage = errorJson.getString("message");
                }
                else
                {
                    strErrorMessage = "Unknown Error";
                }
            }
            catch (Exception ex)
            {
                strErrorMessage = "Unknown Error";
            }

            // alertManager.showAlertOk(SignInEmailActivity.this, "" + strErrorMessage, getResources().getString(R.string.ok));
            Toast.makeText(EmailSignInActivity.this, ""+strErrorMessage, Toast.LENGTH_SHORT).show();
        }
        else if(response.getResponseMessage()!=null && !(response.getResponseMessage().isEmpty()))
        {
            Toast.makeText(EmailSignInActivity.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(EmailSignInActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission()
    {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
       /* int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);*/

        return (result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED);// && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED && result7 == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        //ActivityCompat.requestPermissions(EmailSignInActivity.this, new String[]{ACCESS_NETWORK_STATE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE, RECORD_AUDIO}, REQUEST_PERMISSION_COED);
        ActivityCompat.requestPermissions(EmailSignInActivity.this, new String[]{ACCESS_NETWORK_STATE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_COED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_PERMISSION_COED:
                if (grantResults.length> 0)
                {
                    boolean permission1 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permission2 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean permission3 = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                  /*  boolean permission4 = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean permission5 = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean permission6 = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean permission7 = grantResults[6] == PackageManager.PERMISSION_GRANTED;*/

                    if (permission1 && permission2 && permission3)// && permission4 && permission5 && permission6 && permission7)
                    {
                        // Start Sound detection sdk
                        //Actv8SoundLib.getInstance().startListening(this);

                        btnSignIn.performClick();
                    }
                    else
                    {
                        Toast.makeText(EmailSignInActivity.this,getResources().getString(R.string.permission_denied),Toast.LENGTH_LONG).show();
                        //finish();
                    }
                }
                break;
        }
    }
}