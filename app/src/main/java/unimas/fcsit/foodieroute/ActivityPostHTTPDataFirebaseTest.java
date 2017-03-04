package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.Base64OutputStream;
import com.loopj.android.http.JsonStreamerEntity;
import com.loopj.android.http.JsonValueInterface;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Created by elliotching on 03-Mar-17.
 */

public class ActivityPostHTTPDataFirebaseTest extends AppCompatActivity {

    static String firebase_HTTP = "https://fcm.googleapis.com/fcm/send";
    static String HEAD_AUTH = "Authorization";
    static String HEAD_AUTH_SERVER_KEY = "key=AAAAwbp2NJ0:APA91bHxLB7W_cnedvCwdcFI" +
            "_-ji4bbljFzlMdPhoeFM7ARegRvP2hG9n" +
            "MgrIvzEKJZ8BNU5ZdtirO7kGbKVKheKUv" +
            "k7G0yiVhXpc747vBtqZaRZoHKV22JL5oL" +
            "IPDHBLFwsJj3lVGAH";

    static String HEAD_CONTENT_TYPE_VALUE = "application/json";
    Context context = this;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_http_data_firebase_test);
        btn = (Button) findViewById(R.id.send_fcm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeHTTPCall();
            }
        });
    }

    private void makeHTTPCall() {
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader(HEAD_AUTH, HEAD_AUTH_SERVER_KEY);
//        client.addHeader(HEAD_CONTENT_TYPE, HEAD_CONTENT_TYPE_VALUE);
//        client.post(this,firebase_HTTP,,jsonData() , new MyAsyncHttpRespondHandler());
        client.post(this, firebase_HTTP, jsonData(), HEAD_CONTENT_TYPE_VALUE, new MyAsyncHttpRespondHandler());
    }

    int i = 0;
    private MyEntity jsonData() {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonNoti = new JSONObject();
            jsonNoti.put("title", "ELLIOT TITLE");
            jsonNoti.put("body", "ELLIOT MSG BODY "+(++i));
            jsonNoti.put("sound", "default");
//            jsonNoti.put("tag", "tag_a");

            json.put("data", jsonNoti);
//            if(FirebaseInstanceId.getInstance().getToken() != null)
            json.put("to", "fqxkRkZ_0QU:APA91bH4D4nwvVv-ML0AizEGVlk8DrV-uGm3ovRY8xSP6lB2im6dqrE4Aoru9NaWeyirhA4ng2IXoY_MGyelGkblYRFVbuhl7DJNdoWFTP03Y2TLOenu65bS_qSdIfJgCgqmRqxXj4UV");
            System.out.println("json = " + json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyEntity j = new MyEntity(json.toString());
//        j.addPart("to",FirebaseInstanceId.getInstance().getToken());
//        RequestParams params = new RequestParams();
//        params.put("", json.toString());
//        try {
        System.out.println("jsonstreamer = " + j.toString());
//        }
        return j;
    }

    private class MyEntity implements HttpEntity {

        // Size of the byte-array buffer used in I/O streams.
        private final int BUFFER_SIZE = 4096;
        private String json;

        public MyEntity(String jsonstring) {
            json = jsonstring;
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public boolean isChunked() {
            return false;
        }

        @Override
        public boolean isStreaming() {
            return false;
        }

        @Override
        public long getContentLength() {
            return -1;
        }

        @Override
        public Header getContentEncoding() {
            return null;
        }

        @Override
        public Header getContentType() {
            return null;
        }

        @Override
        public void consumeContent() throws IOException, UnsupportedOperationException {
        }

        @Override
        public InputStream getContent() throws IOException, UnsupportedOperationException {
            return null;
        }

        @Override
        public void writeTo(final OutputStream out) throws IOException {
            if (out == null) {
                throw new IllegalStateException("Output stream cannot be null.");
            }

            // Record the time when uploading started.
            long now = System.currentTimeMillis();

            OutputStream os = out;

            int[] chararray = new int[json.length()];
            for (int count = 0; count < json.length(); count++)
                chararray[count] = json.charAt(count);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (int count = 0; count < json.length(); count++)
                bos.write(chararray[count]);
            bos.close();
            byte[] arr = bos.toByteArray();

            os.write(arr); // or: bos.writeTo(os);
            os.close();

            System.out.println("bos.toString() = " + new String(bos.toByteArray()));
            os.flush();
            AsyncHttpClient.silentCloseOutputStream(os);
        }
    }

    private class MyAsyncHttpRespondHandler extends AsyncHttpResponseHandler {


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if (statusCode == 200) {
                // Hide Progress Dialog
                String echoResult = new String(responseBody);
                Toast.makeText(context, echoResult,
                        Toast.LENGTH_LONG).show();
                System.out.println("onSuccess: " + echoResult);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            // When Http response code is '404'
            if (statusCode == 404) {
                Toast.makeText(context,
                        "Requested resource not found",
                        Toast.LENGTH_LONG).show();
            }

            // When Http response code is '500'
            else if (statusCode == 500) {
                Toast.makeText(context,
                        "Something went wrong at server end",
                        Toast.LENGTH_LONG).show();
            }

            // When Http response code other than 404, 500
            else {
                Toast.makeText(
                        context,
                        "Error Occured n Most Common Error: \n" +
                                "1. Device not connected to Internet\n" +
                                "2. Web App is not deployed in App server\n" +
                                "3. App server is not running\n HTTP Status code : "
                                + statusCode, Toast.LENGTH_LONG)
                        .show();
//                System.out.println(headers.length >=1?headers[0].toString():"headers is empty");
//                System.out.println(headers.length >=2?headers[1].toString():"headers 2 is empty");
//                System.out.println(headers.length >=3?headers[2].toString():"headers 3 is empty");
            }
        }
    }
}