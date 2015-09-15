package me.andrewcodispoti.pluto_nfc;

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

    private TextView mUsername;
    private Button mSubmitButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mSubmitButton = (Button)v.findViewById(R.id.createUser);
        mSubmitButton.setOnClickListener(this);
        mUsername = ((TextView)v.findViewById(R.id.createUser));
        mUsername.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitButton) {
            registerNewUser();
        }
    }

    private void registerNewUser() {

    }
}
