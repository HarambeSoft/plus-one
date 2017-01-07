package harambesoft.com.plusone.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
    private static final String TAG = "MyActivity";


    @BindView(R.id.swtichnot)
    SwitchCompat swtichnot;
    SharedPreferences preferences = this.getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
    String offnot = "App.settings().edit().putBoolean(showNotifications, false).commit();" + " FirebaseMessaging.getInstance().unsubscribeFromTopic(CurrentUser.notificationTopic());";
    String onnot = "App.settings().edit().putBoolean(showNotifications, true).commit();" + "FirebaseMessaging.getInstance().subscribeToTopic(CurrentUser.notificationTopic());";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}

