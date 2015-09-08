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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by andrewcodispoti on 2015-08-27.
 */
public class SendDataActivity extends Activity {
    String TAG = "PLUTO";
    public static final String PLUTO_PATH = "192.168.0.15:3000/users/%s/io";
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
            hexdump.substring(0,hexdump.length()-2);
            new LoginTask().execute(hexdump.replace(" ", ":"));
        }
    }
    private class LoginTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(String.format(PLUTO_PATH,  params[0]));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                if (conn.getResponseCode() == 200){
                    final MediaPlayer mediaPlayer =  new MediaPlayer();
                    mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("http://www.downloads.nl/cgi-bin/mp3get.cgi?id=866b6dfbdbec7439f71517bafbd92fa0&n=2"));
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            finish();
                        }
                    });
                    mediaPlayer.prepareAsync();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
