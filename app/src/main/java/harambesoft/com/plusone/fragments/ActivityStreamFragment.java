package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import harambesoft.com.plusone.R;
import harambesoft.com.plusone.helpers.ActivityStream;
import harambesoft.com.plusone.models.ActivityModel;

/**
 * Created by isa on 12.12.2016.
 */

public class ActivityStreamFragment extends Fragment {
    public static Fragment newInstance() {
        return new ActivityStreamFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_stream, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (ActivityModel activity: ActivityStream.get()) {
            Log.d("ACTIVITY FOUND:", activity.getTitle());
        }
    }
}