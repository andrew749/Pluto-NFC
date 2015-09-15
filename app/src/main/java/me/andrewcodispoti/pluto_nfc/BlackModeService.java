package me.andrewcodispoti.pluto_nfc;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class BlackModeService extends Service implements View.OnClickListener {

    private WindowManager windowManager;
    private LinearLayout background;

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        background = new LinearLayout(this);
        ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        background.setLayoutParams(LLParams);
        background.setBackgroundColor(Color.BLACK);
        background.setOnClickListener(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        windowManager.addView(background, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (background != null) windowManager.removeView(background);
    }

    @Override
    public void onClick(View v) {
        stopSelf();
    }
}
