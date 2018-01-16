package com.halfway.yogamylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingAddress extends AppCompatActivity {

    public static RequestInterface requestInterface;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public static final String STORAGE_NAME = "StorageName";
    EditText etProfilCompany;
    EditText etProfilAddress;
    EditText etProfilEtc;
    EditText etProfilCity;
    EditText etProfilCountry;
    EditText etProfilProvince;
    EditText etProfilZipCode;
    EditText etProfilPhone;
    Button btnProfilSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shopping_address);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ShoppingAddress.this, ShopLenta.class));
                onBackPressed();
            }
        });

        etProfilCompany = (EditText)findViewById(R.id.et_profil_company);
        etProfilAddress = (EditText)findViewById(R.id.et_profil_address);
        etProfilEtc = (EditText)findViewById(R.id.et_profil_etc);
        etProfilCity = (EditText)findViewById(R.id.et_profil_city);
        etProfilCountry = (EditText)findViewById(R.id.et_profil_country);
        etProfilProvince = (EditText)findViewById(R.id.et_profil_province);
        etProfilZipCode = (EditText)findViewById(R.id.et_profil_zip_code);
        etProfilPhone = (EditText)findViewById(R.id.et_profil_phone);
        btnProfilSave = (Button)findViewById(R.id.btn_profil_save);

        requestInterface = Controller.getApi();
        settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

        requestInterface.getUser(settings.getString("cookie", "")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                String company;
                String address = user.getFirstAddress();
                String etc;
                String city = user.getCity();
                String country = user.getCountry();
                String province = user.getState();
                String zip_code = user.getZipCode();
                String phone = user.getPhoneNumber();
                if(response.code() == 200){
                    if(!address.isEmpty() && !address.equals("")){
                        etProfilAddress.setText(address);
                    }
                    if(!city.isEmpty() && !city.equals("")){
                        etProfilCity.setText(city);
                    }
                    if(!country.isEmpty() && !country.equals("")){
                        etProfilCountry.setText(country);
                    }
                    if(!province.isEmpty() && !province.equals("")){
                        etProfilProvince.setText(province);
                    }
                    if(!zip_code.isEmpty() && !zip_code.equals("")){
                        etProfilZipCode.setText(zip_code);
                    }
                    if(!phone.isEmpty() && !phone.equals("")){
                        etProfilPhone.setText(phone);
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnProfilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                editor = settings.edit();
                if(!etProfilAddress.getText().toString().isEmpty() && !etProfilAddress.getText().toString().equals("")) {
                    user.setFirstAddress(etProfilAddress.getText().toString());
                    editor.putString("address", etProfilAddress.getText().toString());
                }
                if(!etProfilCity.getText().toString().isEmpty() && !etProfilCity.getText().toString().equals("")) {
                    user.setCity(etProfilCity.getText().toString());
                    editor.putString("city", etProfilCity.getText().toString());
                }
                if(!etProfilCountry.getText().toString().isEmpty() && !etProfilCountry.getText().toString().equals("")) {
                    user.setCountry(etProfilCountry.getText().toString());
                    editor.putString("country", etProfilCountry.getText().toString());
                }
                if(!etProfilProvince.getText().toString().isEmpty() && !etProfilProvince.getText().toString().equals("")) {
                    user.setState(etProfilProvince.getText().toString());
                    editor.putString("state", etProfilProvince.getText().toString());
                }
                if(!etProfilZipCode.getText().toString().isEmpty() && !etProfilZipCode.getText().toString().equals("")) {
                    user.setZipCode(etProfilZipCode.getText().toString());
                    editor.putString("zip_code", etProfilZipCode.getText().toString());
                }
                if(!etProfilPhone.getText().toString().isEmpty() && !etProfilPhone.getText().toString().equals("")) {
                    user.setPhoneNumber(etProfilPhone.getText().toString());
                    editor.putString("phone", etProfilPhone.getText().toString());
                }
                editor.apply();
                user.setEmail(settings.getString("email", ""));
                if(!settings.getString("first_name", "").isEmpty() && !settings.getString("first_name", "").equals("")) {
                    user.setFirstName(settings.getString("first_name", ""));
                }
                if(!settings.getString("last_name", "").isEmpty() && !settings.getString("last_name", "").equals("")) {
                    user.setLastName(settings.getString("last_name", ""));
                }
                if(!settings.getString("description", "").isEmpty() && !settings.getString("description", "").equals("")) {
                    user.setDescription(settings.getString("description", ""));
                }
                if(!settings.getString("image", "").isEmpty() && !settings.getString("image", "").equals("")) {
                    user.setImage(settings.getString("image", ""));
                }


                requestInterface.editUser(settings.getString("cookie", ""), user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("myLogs", "Code in shopping address: " + response.code());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(ShoppingAddress.this, ShopLenta.class);
                intent.putExtra("fragment", 33);
                startActivity(intent);
            }
        });
    }
}
