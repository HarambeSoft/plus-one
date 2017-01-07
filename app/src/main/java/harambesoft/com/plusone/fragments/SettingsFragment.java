package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import harambesoft.com.plusone.R;

/**
 * Created by gizemfitoz on 13/12/16.
 */

public class SettingsFragment extends Fragment {

    @BindView(R.id.swtichnot)
    SwitchCompat swtichnot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}

