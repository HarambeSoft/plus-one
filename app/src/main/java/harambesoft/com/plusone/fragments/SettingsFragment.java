package harambesoft.com.plusone.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;

/**
 * Created by gizemfitoz on 13/12/16.
 */

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.swtichnot)
    SwitchCompat swtichnot;

    @BindView(R.id.swtichVibrate)
    SwitchCompat swtichVibrate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,view);
        swtichnot.setOnCheckedChangeListener(this);
        boolean den=App.settings().getBoolean("showNotifications",false);
        swtichnot.setChecked(den);
        return view;
    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        App.settings().edit().putBoolean("showNotifications", b).apply();
        if (swtichnot.isChecked())
        {
            FirebaseMessaging.getInstance().subscribeToTopic(CurrentUser.notificationTopic());
        }
        else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(CurrentUser.notificationTopic());
        }

    }
}

