package harambesoft.com.plusone.fragments;

/**
 * Created by isa on 11.12.2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import harambesoft.com.plusone.MainActivity;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.PlusOneAPI;
import harambesoft.com.plusone.views.BackPressedListener;

public class SignInFragment extends Fragment implements BackPressedListener {
    public static final String TAG = SignInFragment.class.getName();

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
        final Button buttonGoSignUp = (Button) view.findViewById(R.id.buttonGoSignUp);
        final TextView textViewError = (TextView) view.findViewById(R.id.textViewErrorSignIn);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlusOneAPI.login(textViewUserName.getText().toString(), textViewPassword.getText().toString(), new PlusOneAPI.LoginFinishedHandler() {
                        @Override
                        public void onLoginFinished(boolean success, String message) {
                            if (success) {
                                // To set related things about user, and redirect
                                ((MainActivity) getActivity()).checkUserLogin();
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


        buttonGoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new SignUpFragment(), SignUpFragment.TAG)
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}