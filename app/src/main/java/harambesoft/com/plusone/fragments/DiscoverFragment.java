package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import harambesoft.com.plusone.R;
import harambesoft.com.plusone.helpers.ActivityStream;
import harambesoft.com.plusone.views.BackPressedListener;

/**
 * Created by gizemfitoz on 13/12/16.
 */

public class DiscoverFragment extends Fragment implements BackPressedListener {
    public static final String TAG = DiscoverFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ActivityStreamFragment(), ActivityStreamFragment.TAG)
                .commit();
    }
}
