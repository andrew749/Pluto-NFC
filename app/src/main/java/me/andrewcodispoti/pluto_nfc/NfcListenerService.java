package me.andrewcodispoti.pluto_nfc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by andrewcodispoti on 2015-08-26.
 */
public class NfcListenerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
