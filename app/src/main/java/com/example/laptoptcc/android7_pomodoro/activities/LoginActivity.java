package com.example.laptoptcc.android7_pomodoro.activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptoptcc.android7_pomodoro.R;
import com.example.laptoptcc.android7_pomodoro.networks.jsonmodels.LoginBodyJson;
import com.example.laptoptcc.android7_pomodoro.networks.jsonmodels.LoginRespondJson;
import com.example.laptoptcc.android7_pomodoro.networks.services.LoginServices;
import com.example.laptoptcc.android7_pomodoro.settings.LoginCredentials;
import com.example.laptoptcc.android7_pomodoro.settings.SharedPrefs;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private EditText etUsername;
    private EditText etPassword;
    private Button btRegister;
    private Button btLogin;
    private Retrofit retrofit;
    private String username;
    private String password;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) this.findViewById(R.id.et_username);
        etPassword = (EditText) this.findViewById(R.id.et_password);
        btLogin = (Button) this.findViewById(R.id.bt_login);
        btRegister = (Button) this.findViewById(R.id.bt_register);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                attemptLogin();
                return true;}
                return false;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void sendLogin(String username, String password) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Tao ra mot loginServices tu retrofit gom lenh POST, CallRespond
        LoginServices loginServices = retrofit.create(LoginServices.class);
        //data & format
        //format <> mediatype
        //data <> json
        //Tao kieu
        MediaType jsonType = MediaType.parse("application/json");
        //Chuyen LoginBodyJson ve dang String loginJson.
        String longinJson = new Gson().toJson(new LoginBodyJson(username, password));
        //Tao Json Respond request cau longinJson
        RequestBody loginBody = RequestBody.create(jsonType, longinJson);
        loginServices
                .login(loginBody)
                .enqueue(new Callback<LoginRespondJson>() {
                    @Override
                    public void onResponse(Call<LoginRespondJson> call, Response<LoginRespondJson> response) {
                        LoginRespondJson loginRespondJson = response.body();
                        if (loginRespondJson == null) {
                            Log.d(TAG, "onRespond: Couldn't parse body");
                        } else {
                            Log.d(TAG, String.format("onRespond, oh yeah: %s", loginRespondJson));
                            if (response.code() == 200) {
                                onLoginSuccess();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRespondJson> call, Throwable t) {
                        Log.d(TAG, String.format("onFailure:%s", t));
                    }
                });

    }

    private void skipLoginAsPossible() {
        if (SharedPrefs.getInstance().getAccessToken() != null) {
            gotoTaskActivity();
        }

    }

    private void onLoginSuccess() {
        SharedPrefs.getInstance().put(new LoginCredentials(username, password));
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
        gotoTaskActivity();
    }

    private void attemptRegister() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        if (username.equals("admin") && password.equals("admin")) {
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Username or Password invalid", Toast.LENGTH_LONG).show();
        }
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        sendLogin(username, password);
        /*if(username.equals("admin") && password.equals("admin")){
            // Notifications

            SharedPrefs.getInstance().put(new LoginCredentials(username,password));
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Username or Password invalid",Toast.LENGTH_LONG).show();
        }*/
    }

    private void gotoTaskActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
