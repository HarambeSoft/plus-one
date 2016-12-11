package harambesoft.com.plusone.fragments;

/**
 * Created by isa on 11.12.2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.PlusOneAPI;

public class SignUpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonSignUp = (Button) view.findViewById(R.id.buttonSignUp);
        final TextView textViewUserName = (TextView) view.findViewById(R.id.textviewUsernameSignUp);
        final TextView textViewEmail = (TextView) view.findViewById(R.id.textviewEmailSignUp);
        final TextView textViewPassword = (TextView) view.findViewById(R.id.textviewPasswordSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlusOneAPI.signUp(textViewUserName.getText().toString(), textViewEmail.getText().toString(), textViewPassword.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}