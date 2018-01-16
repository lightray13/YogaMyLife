package com.halfway.yogamylife;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailLogin extends AppCompatActivity implements View.OnClickListener{

    Button emailNextLogin, emailBackLogin;
    EditText etEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        emailNextLogin = (Button)findViewById(R.id.emailNextLogin);
        emailBackLogin = (Button)findViewById(R.id.emailBackLogin);
        etEmailLogin = (EditText)findViewById(R.id.et_email_address_login);
        etEmailLogin.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);

        emailNextLogin.setOnClickListener(this);
        emailBackLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailNextLogin:
                if(etEmailLogin.getText().toString().isEmpty()) {
                    etEmailLogin.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                } else {
                    etEmailLogin.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    String email = etEmailLogin.getText().toString();
                    Intent newIntent = new Intent(this, PasswordLogin.class);
                    newIntent.putExtra("email", email);
                    startActivity(newIntent);
                }
                break;
            case R.id.emailBackLogin:
                startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
