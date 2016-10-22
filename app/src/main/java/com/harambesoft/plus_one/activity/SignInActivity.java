package com.harambesoft.plus_one.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.harambesoft.plus_one.R;


public class SignInActivity extends AppCompatActivity {
    private Button buttonCalisin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonCalisin = (Button) findViewById(R.id.buttonCalisin);

        buttonCalisin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


    }

    private void openMainActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
