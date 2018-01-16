package com.halfway.yogamylife;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Name extends AppCompatActivity implements View.OnClickListener{

    Button btnNext, btnCancel;
    EditText etFirstName, etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        btnNext = (Button)findViewById(R.id.btnNext);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        etFirstName = (EditText)findViewById(R.id.et_first_name);
        etLastName = (EditText)findViewById(R.id.et_last_name);
        etFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);

        btnNext.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                Intent intent = new Intent(this, Email.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                startActivity(intent);
                break;
            case R.id.btnCancel:
                startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
