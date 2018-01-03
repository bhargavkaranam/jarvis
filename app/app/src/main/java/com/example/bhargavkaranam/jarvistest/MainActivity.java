package com.example.bhargavkaranam.jarvistest;

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

    private String SERVER_URL = "http://df1825e3.ngrok.io/command";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("token" , FirebaseInstanceId.getInstance().getToken());
    }

    public void sendToServer(View v)
    {
        String command = ((EditText)findViewById(R.id.editText)).getText().toString();
        RequestParams params = new RequestParams();
        params.put("command", command);


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
