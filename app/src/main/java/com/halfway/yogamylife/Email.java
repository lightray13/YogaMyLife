package com.halfway.yogamylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Email extends AppCompatActivity implements View.OnClickListener{

    Button emailNext, emailBack;
    EditText etEmail;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public static final String STORAGE_NAME = "StorageName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        emailNext = (Button)findViewById(R.id.emailNext);
        emailBack = (Button)findViewById(R.id.emailBack);
        etEmail = (EditText)findViewById(R.id.et_email_address);
        etEmail.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);

        emailNext.setOnClickListener(this);
        emailBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailNext:
                if(etEmail.getText().toString().isEmpty()) {
                    etEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                } else {
                    etEmail.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    String email = etEmail.getText().toString();
                    Intent intent = getIntent();
                    String firstName = intent.getStringExtra("firstName");
                    String lastName = intent.getStringExtra("lastName");
                    Intent newIntent = new Intent(this, Password.class);
                    newIntent.putExtra("firstName", firstName);
                    newIntent.putExtra("lastName", lastName);
                    newIntent.putExtra("email", email);
                    startActivity(newIntent);
                }
                break;
            case R.id.emailBack:
                startActivity(new Intent(this, Name.class));
        }
    }
}
