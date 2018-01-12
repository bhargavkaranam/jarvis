package com.example.bhargavkaranam.jarvistest;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "http://9933e51d.ngrok.io/";
    private String SERVER_URL = BASE_URL + "command";
    private String SERVER_URL_WEBHOOK = BASE_URL + "/notification";
    private String PASSWORD = "4Ef-X%qW^@~.r5^LZ_Q}S!h~dN&@5#N4";
    private String DECRYPTPASSWORD = "q&FDMh\"5m<>?:<r5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //Log.d("token" , FirebaseInstanceId.getInstance().getToken());

            Intent i = getIntent();
            if(i.getExtras() != null) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", i.getStringExtra("copy"));
                clipboard.setPrimaryClip(clip);
            }
            LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));


    }

    public void sendToServer(View v)
    {
        final String command = ((EditText)findViewById(R.id.editText)).getText().toString();
        RequestParams params = new RequestParams();
        params.put("command", command);
        params.put("password", PASSWORD);
        if(command.contains("read") || command.contains("encrypt"))
            params.put("decryptPassword", DECRYPTPASSWORD);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(SERVER_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");

            RequestParams params = new RequestParams();
            params.put("package", pack);
            params.put("title", title);
            params.put("text", text);

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(SERVER_URL_WEBHOOK, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
                }
            });







        }
    };
}
