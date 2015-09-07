package me.andrewcodispoti.pluto_nfc;

import android.app.Activity;
import android.content.Intent;
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
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String PLUTO_PATH = "192.168.0.1:3000/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {/**/
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        Toast.makeText(this,"Tag", Toast.LENGTH_LONG);
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
            new LoginTask().execute(hexdump.replace(" ", ":"));
        }
    }
    private class LoginTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(PLUTO_PATH + params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
