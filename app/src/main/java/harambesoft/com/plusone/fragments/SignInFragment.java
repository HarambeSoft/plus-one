package harambesoft.com.plusone.fragments;

/**
 * Created by isa on 11.12.2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import harambesoft.com.plusone.MainActivity;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.PlusOneAPI;

public class SignInFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button buttonSignIn = (Button) view.findViewById(R.id.buttonSignIn);
        final TextView textViewUserName = (TextView) view.findViewById(R.id.textviewUsernameSignIn);
        final TextView textViewPassword = (TextView) view.findViewById(R.id.textviewPasswordSignIn);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean result = PlusOneAPI.login(textViewUserName.getText().toString(), textViewPassword.getText().toString());
                    if (result) {
                        // To set related things about user, and redirect
                        ((MainActivity) getActivity()).checkUserLogin();
                    } else {
                        //TODO: show error message about why that fucker is fucking retarded enough to write wrong shits to login page
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}