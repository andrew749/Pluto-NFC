package me.andrewcodispoti.pluto_nfc;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private Button mStartButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mStartButton = (Button)v.findViewById(R.id.startBlackMode);
        mStartButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == mStartButton) {
            getActivity().startService(new Intent(getActivity(), BlackModeService.class));
        }
    }
}
