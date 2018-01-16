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

public class PasswordLogin extends AppCompatActivity implements View.OnClickListener{

    Button btnDoneLogin, passBackLogin;
    EditText etNewPasswordLogin;
    public static RequestInterface requestInterface;
    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    public static final String STORAGE_NAME = "StorageName";
    int code;
    TextView tvErrorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);

        btnDoneLogin = (Button) findViewById(R.id.btnDoneLogin);
        passBackLogin = (Button) findViewById(R.id.passBackLogin);
        etNewPasswordLogin = (EditText) findViewById(R.id.et_new_password_login);
        etNewPasswordLogin.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        tvErrorLogin = (TextView) findViewById(R.id.tv_error_login);

        btnDoneLogin.setOnClickListener(this);
        passBackLogin.setOnClickListener(this);

        tvErrorLogin.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDoneLogin:
                if(etNewPasswordLogin.getText().toString().isEmpty()) {
                    etNewPasswordLogin.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                } else {
                    etNewPasswordLogin.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    requestInterface = Controller.getApi();


                    Intent intent = getIntent();
                    final String email = intent.getStringExtra("email");
                    String newPassword = etNewPasswordLogin.getText().toString();
                    JsonObject createdJson = new JsonObject();
                    createdJson.addProperty("email", email);
                    createdJson.addProperty("password", newPassword);
                    Log.d("myLogs", "Json " + createdJson);

                    requestInterface.loginUser(createdJson).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("myLogs", "Responce " + response.headers());
                            Log.d("myLogs", "Responce " + response.code());

                            code = response.code();
                            if(response.code() == 200) {
                                tvErrorLogin.setVisibility(View.GONE);
                                String cookie = response.headers().get("Set-Cookie").substring(0, 45);
                                Log.d("myLogs", cookie);
                                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                                editor = settings.edit();
                                editor.putString("email", email);
                                editor.putString("cookie", cookie);
                                editor.apply();
                                saveUser();
                                Intent newIntent = new Intent(PasswordLogin.this, ShopLenta.class);
                                startActivity(newIntent);
                                tvErrorLogin.setVisibility(View.GONE);
                            } else if (response.code() == 404) {
                                tvErrorLogin.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("myLogs", "onFailure" + t);
                        }
                    });

                    settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                    Log.d("muLogs", "PasswordLogin code:" + String.valueOf(code));
                }
                break;

            case R.id.passBackLogin:
                startActivity(new Intent(this, EmailLogin.class));
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
