package me.andrewcodispoti.pluto_nfc;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by andrewcodispoti on 2015-08-27.
 */
public class SendDataActivity extends Activity {
    String TAG = "PLUTO";
    public static final String PLUTO_PATH = "http://192.168.0.15:3000/users/%s/io";
    @Override
    protected void onCreate(Bundle savedInstanceState) {/**/
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] tagId  = tag.getId();
            String hexdump = new String();
            for (int i = 0; i < tagId.length; i++) {
                String x = Integer.toHexString(((int) tagId[i] & 0xff));
                if (x.length() == 1) {
                    x = '0' + x;
                }
                hexdump += x + ' ';
            }
            hexdump = hexdump.substring(0,hexdump.length()-1);
            hexdump = hexdump.replace(" ", ":");

            new LoginTask().execute(hexdump);
        }
    }
    private class LoginTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(String.format(PLUTO_PATH, params[0]));

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    JSONObject response = getInputStreamResponse(conn.getInputStream());

                    if (response.getString("status").equals("OK")){
                        if (response.getBoolean("in")){
                            playSoundWithID(R.raw.signin);
                        }else{
                            playSoundWithID(R.raw.signout);
                        }
                    }else{
                        playSoundWithID(R.raw.error);
                    }

                }else{
                    playSoundWithID(R.raw.error);
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            playSoundWithID(R.raw.error);
            return null;
        }
        private JSONObject getInputStreamResponse(InputStream is){
            try {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                return new JSONObject(responseStrBuilder.toString());
            }catch(IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        private void playSoundWithID(int ID){
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), ID);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                }
            });
        }
    }
}
