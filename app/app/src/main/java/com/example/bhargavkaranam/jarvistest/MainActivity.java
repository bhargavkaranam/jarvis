package com.example.bhargavkaranam.jarvistest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;

    private String BASE_URL = "http://8c643a0a.ngrok.io/";
    private String SERVER_URL = BASE_URL + "command";
    private String SERVER_URL_WEBHOOK = BASE_URL + "/notification";
    private String SERVER_URL_PHOTO = BASE_URL + "/image";

    private String PASSWORD = "4Ef-X%qW^@~.r5^LZ_Q}S!h~dN&@5#N4";
    private String DECRYPTPASSWORD = "q&FDMh\"5m<>?:<r5";

    Uri photoURI;

    private String mCurrentPhotoPath;

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


    private void serverRequest(String url, RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
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


    public void sendToServer(View v)
    {
        final String command = ((EditText)findViewById(R.id.editText)).getText().toString();
        RequestParams params = new RequestParams();
        params.put("command", command);
        params.put("password", PASSWORD);
        if(command.contains("read") || command.contains("encrypt"))
            params.put("decryptPassword", DECRYPTPASSWORD);

        if(command.contains("camera"))
            triggerCamera();
        else
            serverRequest(SERVER_URL, params);


    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");

            Pattern pattern = Pattern.compile("(\\d{6})");

            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                text = matcher.group(1);  // 4 digit number
            }

            RequestParams params = new RequestParams();
            params.put("package", pack);
            params.put("title", title);
            params.put("text", text);

            serverRequest(SERVER_URL_WEBHOOK, params);

        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            String path = mCurrentPhotoPath.toString();
            String base64 = convertToBase64(path);
            RequestParams params = new RequestParams();
            params.put("image", base64);

            serverRequest(SERVER_URL_PHOTO, params);

        }
    }








    private void triggerCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photoFile = null;
        try {
            //THIS CREATES A JPG FILE AND PASSES THE PATH TO THE CAMERA SO THAT THE IMAGE IS STORED THERE
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String convertToBase64(String imagePath)
    {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;
    }

}
