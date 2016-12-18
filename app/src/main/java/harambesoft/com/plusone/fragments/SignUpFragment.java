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

import butterknife.BindView;
import harambesoft.com.plusone.MainActivity;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.PlusOneAPI;

public class SignUpFragment extends Fragment {
    @BindView(R.id.buttonSignUp)Button buttonSignUp;
    @BindView(R.id.textviewUsernameSignUp)TextView textviewUsernameSignUp;
    @BindView(R.id.textviewEmailSignUp)TextView textViewEmail;
    @BindView(R.id.textviewPasswordSignUp)TextView textViewPassword;
    @BindView(R.id.textViewErrorSignUp)TextView textViewError;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlusOneAPI.signUp(textviewUsernameSignUp.getText().toString(), textViewEmail.getText().toString(), textViewPassword.getText().toString(), new PlusOneAPI.SignupFinishedHandler() {
                        @Override
                        public void onSignupFinished(boolean success, String message) {
                            if (success) {
                                // Go log in.
                                ((MainActivity)getActivity()).checkUserLogin();
                            } else {
                                textViewError.setText(message);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}