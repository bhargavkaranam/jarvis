package com.example.bhargavkaranam.jarvistest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

    private String SERVER_URL = "http://9933e51d.ngrok.io/command";
    private String PASSWORD = "4Ef-X%qW^@~.r5^LZ_Q}S!h~dN&@5#N4";
    private String DECRYPTPASSWORD = "q&FDMh\"5m<>?:<r5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("token" , FirebaseInstanceId.getInstance().getToken());

            Intent i = getIntent();
            if(i.getExtras() != null) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", i.getStringExtra("copy"));
                clipboard.setPrimaryClip(clip);
            }

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
}
