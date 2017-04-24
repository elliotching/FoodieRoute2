package unimas.fcsit.foodieroute;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by elliotching on 15-Mar-17.
 */

public class CustomHTTP extends AsyncTask<String, Void, String>
{
    Context context;
    public static final int CONNECTION_TIMEOUT=5000;
    public static final int READ_TIMEOUT=5000;
//    ProgressDialog pdLoading;
    HttpURLConnection conn;
    private URL url = null;
    private final String urlString;

    InterfaceCustomHTTP ui=null;

    String[][] keyvalues=null;

    JSONObject jsonObject=null;

    CustomHTTP(Context c, String[][] keyvalue, String URL){
        context = c;
        this.keyvalues = keyvalue;
        this.urlString = URL;
    }

    CustomHTTP(Context c, JSONObject jsonObject, String URL){
        context = c;
        this.jsonObject = jsonObject;
        urlString = URL;
    }
    @Override
    protected String doInBackground(String... params) {
        try {

            // Enter URL address where your php file resides
            url = new URL(urlString);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return "exception";
        }
        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");
            if(jsonObject != null) {
                /* usingJSON: if this HTTP calling is to send msg to Firebase FCM ,
                * and with content of pure JSON only, (without any payload/RequestParams)
                * then we set up extra header as followed.*/
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", ResFR.string(context, R.string.firebase_auth_key));
            }

            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String query;
            if(keyvalues != null) {
                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder();
                for (int i = 0; i < keyvalues.length; i++) {
                    builder.appendQueryParameter(keyvalues[i][0], keyvalues[i][1]);
                }
                query = builder.build().getEncodedQuery();
            }else{
                query = jsonObject.toString();
            }

            // Open connection for sending data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            System.err.println("elliot: failed URL");
            e1.printStackTrace();
            return "exception";
        }

        try {

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                int line ;

                while ((line = reader.read()) != -1) {
                    result.append((char)line);
                }
                System.out.println("Elliot: Out: "+result);
                // Pass data to onPostExecute method
                return(result.toString());

            }else{
                return(""+response_code+"\nunsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        } finally {
            conn.disconnect();
        }


    }

    @Override
    protected void onPostExecute(String result) {

        //this method will be running on UI thread
//        try{
//            JSONObject d = new JSONObject(result);
//        }catch (JSONException e) {
//            e.printStackTrace();
//        }
        if(ui!=null)ui.onCompleted(result);
        System.out.println(this.getClass()+": "+result);
    }

}
