package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import harambesoft.com.plusone.PlusOne;
import harambesoft.com.plusone.R;

/**
 * Created by isa on 12.12.2016.
 */

public class ActivityStreamFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_stream, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView textViewWelcome = (TextView) view.findViewById(R.id.textViewWelcome);
        String userName = PlusOne.settings().getString("name", "Not logged in.");
        textViewWelcome.setText("Welcome " + userName + "!");
    }

    public static Fragment newInstance() {
        //TODO: implement this
        return new ActivityStreamFragment();
    }
}