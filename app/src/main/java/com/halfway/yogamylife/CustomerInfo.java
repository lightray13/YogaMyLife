package com.halfway.yogamylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.Driver;
import java.sql.DriverManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerInfo extends AppCompatActivity {

    Button btnPaymentMethod;
    public static RequestInterface requestInterface;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public static final String STORAGE_NAME = "StorageName";
    TextView tvShippingMethod;
    TextView tvCustomerFirstName;
    TextView tvCustomerLastName;
    TextView tvCustomerCompany;
    TextView tvCustomerAddress;
    TextView tvCustomerEtc;
    TextView tvCustomerCity;
    TextView tvCustomerCountry;
    TextView tvCustomerProvince;
    TextView tvCustomerZipCode;
    TextView tvCustomerPhone;
    EditText etCustomerFirstName;
    EditText etCustomerLastName;
    EditText etCustomerCompany;
    EditText etCustomerAddress;
    EditText etCustomerEtc;
    EditText etCustomerCity;
    EditText etCustomerCountry;
    EditText etCustomerProvince;
    EditText etCustomerZipCode;
    EditText etCustomerPhone;
    ScrollView svCustomer;

    static int rb;
    static Double rbValue;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        tvShippingMethod = (TextView)findViewById(R.id.tv_shipping_method);
        tvCustomerFirstName = (TextView)findViewById(R.id.tv_customer_first_name);
        tvCustomerLastName = (TextView)findViewById(R.id.tv_customer_last_name);
        tvCustomerCompany = (TextView)findViewById(R.id.tv_customer_company);
        tvCustomerAddress = (TextView)findViewById(R.id.tv_customer_address);
        tvCustomerEtc = (TextView)findViewById(R.id.tv_customer_etc);
        tvCustomerCity = (TextView)findViewById(R.id.tv_customer_city);
        tvCustomerCountry = (TextView)findViewById(R.id.tv_customer_country);
        tvCustomerProvince = (TextView)findViewById(R.id.tv_customer_province);
        tvCustomerZipCode = (TextView)findViewById(R.id.tv_customer_zip_code);
        tvCustomerPhone = (TextView)findViewById(R.id.tv_customer_phone);
        etCustomerFirstName = (EditText)findViewById(R.id.et_customer_first_name);
        etCustomerLastName = (EditText)findViewById(R.id.et_customer_last_name);
        etCustomerCompany = (EditText)findViewById(R.id.et_customer_company);
        etCustomerAddress = (EditText)findViewById(R.id.et_customer_address);
        etCustomerEtc = (EditText)findViewById(R.id.et_customer_etc);
        etCustomerCity = (EditText)findViewById(R.id.et_customer_city);
        etCustomerCountry = (EditText)findViewById(R.id.et_customer_country);
        etCustomerProvince = (EditText)findViewById(R.id.et_customer_province);
        etCustomerZipCode = (EditText)findViewById(R.id.et_customer_zip_code);
        etCustomerPhone = (EditText)findViewById(R.id.et_customer_phone);
        svCustomer = (ScrollView)findViewById(R.id.sv_customer);
        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        requestInterface = Controller.getApi();
        settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

        requestInterface.getUser(settings.getString("cookie", "")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String company;
                String address = user.getFirstAddress();
                String etc;
                String city = user.getCity();
                String country = user.getCountry();
                String province = user.getState();
                String zip_code = user.getZipCode();
                String phone = user.getPhoneNumber();
                if(response.code() == 200){
                    if(!firstName.isEmpty() && !firstName.equals("")){
                        etCustomerFirstName.setText(firstName);
                    }
                    if(!lastName.isEmpty() && !lastName.equals("")){
                        etCustomerLastName.setText(lastName);
                    }
                    if(!address.isEmpty() && !address.equals("")){
                        etCustomerAddress.setText(address);
                    }
                    if(!city.isEmpty() && !city.equals("")){
                        etCustomerCity.setText(city);
                    }
                    if(!country.isEmpty() && !country.equals("")){
                        etCustomerCountry.setText(country);
                    }
                    if(!province.isEmpty() && !province.equals("")){
                        etCustomerProvince.setText(province);
                    }
                    if(!zip_code.isEmpty() && !zip_code.equals("")){
                        etCustomerZipCode.setText(zip_code);
                    }
                    if(!phone.isEmpty() && !phone.equals("")){
                        etCustomerPhone.setText(phone);
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_customer);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CustomerInfo.this, ShopLenta.class));
                onBackPressed();
            }
        });



        rg = (RadioGroup) findViewById(R.id.rg);

        btnPaymentMethod = (Button) findViewById(R.id.btn_payment);

        btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    User user = new User();
                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                editor = settings.edit();
                    if(!etCustomerFirstName.getText().toString().isEmpty() && !etCustomerFirstName.getText().toString().equals("")) {
                        editor.putString("first_name", etCustomerFirstName.getText().toString());
                        user.setFirstName(settings.getString("first_name", ""));
                    }
                if(!etCustomerLastName.getText().toString().isEmpty() && !etCustomerLastName.getText().toString().equals("")) {
                    editor.putString("last_name", etCustomerLastName.getText().toString());
                    user.setLastName(settings.getString("last_name", ""));
                }
                if(!etCustomerAddress.getText().toString().isEmpty() && !etCustomerAddress.getText().toString().equals("")) {
                    editor.putString("address", etCustomerAddress.getText().toString());
                    user.setFirstAddress(settings.getString("address", ""));
                }
                if(!etCustomerCity.getText().toString().isEmpty() && !etCustomerCity.getText().toString().equals("")) {
                    editor.putString("city", etCustomerCity.getText().toString());
                    user.setCity(settings.getString("city", ""));
                }
                if(!etCustomerCountry.getText().toString().isEmpty() && !etCustomerCountry.getText().toString().equals("")) {
                    editor.putString("country", etCustomerCountry.getText().toString());
                    user.setCountry(settings.getString("country", ""));
                }
                if(!etCustomerProvince.getText().toString().isEmpty() && !etCustomerProvince.getText().toString().equals("")) {
                    editor.putString("state", etCustomerProvince.getText().toString());
                    user.setState(settings.getString("state", ""));
                }
                if(!etCustomerZipCode.getText().toString().isEmpty() && !etCustomerZipCode.getText().toString().equals("")) {
                    editor.putString("zip_code", etCustomerZipCode.getText().toString());
                    user.setZipCode(settings.getString("zip_code", ""));
                }
                if(!etCustomerPhone.getText().toString().isEmpty() && !etCustomerPhone.getText().toString().equals("")) {
                    editor.putString("phone", etCustomerPhone.getText().toString());
                    user.setPhoneNumber(settings.getString("phone", ""));
                }
                editor.apply();
                    user.setEmail(settings.getString("email", ""));
                if(!settings.getString("description", "").isEmpty() && !settings.getString("description", "").equals("")) {
                    user.setDescription(settings.getString("description", ""));
                }
                if(!settings.getString("image", "").isEmpty() && !settings.getString("image", "").equals("")) {
                    user.setImage(settings.getString("image", ""));
                }
                    requestInterface.editUser(settings.getString("cookie", ""), user).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("myLogs", "Code in edit: " + response.code());
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.rb_post:
                        rb = 1;
                        rbValue = 4.99;
                        break;
                    case R.id.rb_priority:
                        rb = 2;
                        rbValue = 8.91;
                        break;
                    case R.id.rb_express:
                        rb = 3;
                        rbValue = 33.81;
                        break;
                    default:
                        rb = 0;
                        break;
                }
                if(rb == 0) {
                    tvShippingMethod.setTextColor(getResources().getColor(R.color.red));
                }
                if(rb != 0) {
                    Log.d("myLogs", "etCustomerProvince: " + etCustomerProvince.getText().toString());
                    tvShippingMethod.setTextColor(getResources().getColor(R.color.black));
                    if(etCustomerFirstName.getText().toString().isEmpty()) {
                        Log.d("myLogs", "etCustomerProvince is empty ");
                        svCustomer.smoothScrollTo(0, tvCustomerFirstName.getTop());
                        etCustomerFirstName.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerLastName.getText().toString().isEmpty()) {
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        svCustomer.smoothScrollTo(0, tvCustomerLastName.getTop());
                        etCustomerLastName.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerAddress.getText().toString().isEmpty()) {
                        svCustomer.smoothScrollTo(0, tvCustomerAddress.getTop());
                        etCustomerAddress.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerCity.getText().toString().isEmpty()) {
                        svCustomer.smoothScrollTo(0, tvCustomerCity.getTop());
                        etCustomerCity.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerCountry.getText().toString().isEmpty()) {
                        svCustomer.smoothScrollTo(0, tvCustomerCountry.getTop());
                        etCustomerCountry.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerProvince.getText().toString().isEmpty()) {
                        svCustomer.smoothScrollTo(0, tvCustomerProvince.getTop());
                        etCustomerProvince.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerZipCode.getText().toString().isEmpty()) {
                        svCustomer.smoothScrollTo(0, tvCustomerZipCode.getTop());
                        etCustomerZipCode.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else if (etCustomerPhone.getText().toString().isEmpty()) {
                        svCustomer.smoothScrollTo(0, tvCustomerPhone.getTop());
                        etCustomerPhone.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    } else {
                        tvShippingMethod.setTextColor(getResources().getColor(R.color.black));
                        etCustomerFirstName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerLastName.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerAddress.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCity.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerCountry.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerProvince.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerZipCode.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        etCustomerPhone.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        Intent intent = new Intent(CustomerInfo.this, CustomerInfoTotal.class);
                        intent.putExtra("rb", rb);
                        intent.putExtra("rbValue", rbValue);
                        startActivity(intent);
                    }
                }
            }
        });

    }

}
