package com.halfway.yogamylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Password extends AppCompatActivity implements View.OnClickListener {

    Button btnDone, passBack;
    EditText etNewPassword;
    TextView tvError;
    public static RequestInterface requestInterface;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public static final String STORAGE_NAME = "StorageName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        btnDone = (Button) findViewById(R.id.btnDone);
        passBack = (Button) findViewById(R.id.passBack);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        etNewPassword.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        tvError = (TextView) findViewById(R.id.tv_error);

        btnDone.setOnClickListener(this);
        passBack.setOnClickListener(this);

        tvError.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                btnDone.setClickable(false);
                if(etNewPassword.getText().toString().isEmpty()){
                    etNewPassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                } else {
                    etNewPassword.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    requestInterface = Controller.getApi();

                    Intent intent = getIntent();
                    final String email = intent.getStringExtra("email");
                    String firstName = intent.getStringExtra("firstName");
                    String lastName = intent.getStringExtra("lastName");
                    String newPassword = etNewPassword.getText().toString();
                    JsonObject createdJson = new JsonObject();
                    createdJson.addProperty("email", email);
                    createdJson.addProperty("first_name", firstName);
                    createdJson.addProperty("last_name", lastName);
                    createdJson.addProperty("password", newPassword);
                    Log.d("myLogs", "Json " + createdJson);
                    requestInterface.registerUser(createdJson).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("myLogs", "Responce headers" + response.headers());
                            Log.d("myLogs", "Responce headers" + response.code());
                            if(response.code() == 200) {
                                tvError.setVisibility(View.GONE);
                                String cookie = response.headers().get("Set-Cookie").substring(0, 45);
                                Log.d("myLogs", cookie);
                                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                                editor = settings.edit();
                                editor.putString("email", email);
                                editor.putString("cookie", cookie);
                                saveUser();
                                editor.apply();

                                Intent newIntent = new Intent(Password.this, ShopLenta.class);
                                startActivity(newIntent);
                            } else if(response.code() == 404) {
                                tvError.setVisibility(View.VISIBLE);
                                btnDone.setClickable(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.passBack:
                startActivity(new Intent(this, Email.class));
        }

    }

    public void saveUser() {
        requestInterface.getUser(settings.getString("cookie", "")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String description = user.getDescription();
                String image = user.getImage();
                String address = user.getFirstAddress();
                String city = user.getCity();
                String country = user.getCountry();
                String province = user.getState();
                String zip_code = user.getZipCode();
                String phone = user.getPhoneNumber();
                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                editor = settings.edit();
                if(response.code() == 200){
                    if(!firstName.isEmpty() && !firstName.equals("")){
                        editor.putString("first_name", firstName);
                    }
                    if(!lastName.isEmpty() && !lastName.equals("")){
                        editor.putString("last_name", lastName);
                    }
                    if(!description.isEmpty() && !description.equals("")){
                        editor.putString("description", description);
                    }
                    if(!image.isEmpty() && !image.equals("")){
                        editor.putString("image", image);
                    }
                    if(!address.isEmpty() && !address.equals("")){
                        editor.putString("address", address);
                    }
                    if(!city.isEmpty() && !city.equals("")){
                        editor.putString("city", city);
                    }
                    if(!country.isEmpty() && !country.equals("")){
                        editor.putString("country", country);
                    }
                    if(!province.isEmpty() && !province.equals("")){
                        editor.putString("state", province);
                    }
                    if(!zip_code.isEmpty() && !zip_code.equals("")){
                        editor.putString("country", country);
                    }
                    if(!zip_code.isEmpty() && !zip_code.equals("")){
                        editor.putString("zip_code", zip_code);
                    }
                    editor.apply();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}

