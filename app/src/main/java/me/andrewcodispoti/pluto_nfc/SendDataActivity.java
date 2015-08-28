package me.andrewcodispoti.pluto_nfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by andrewcodispoti on 2015-08-27.
 */
public class SendDataActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
    }
}
