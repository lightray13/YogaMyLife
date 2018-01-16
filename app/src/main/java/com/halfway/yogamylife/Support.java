package com.halfway.yogamylife;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Support extends AppCompatActivity {

    EditText etWriteTopic;
    EditText etEnterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        etWriteTopic = (EditText) findViewById(R.id.et_write_topic);
        etEnterText = (EditText) findViewById(R.id.et_enter_text);

        etWriteTopic.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etEnterText.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etEnterText.setSelection(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
