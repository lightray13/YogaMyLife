package com.halfway.yogamylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerInfoTotal extends AppCompatActivity implements OnItemClick{

    TextView tvShipping;
    private static SharedPreferences settings;
    public static final String STORAGE_NAME = "StorageName";
    private static SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    List<Object> items;
    public static RequestInterface requestInterface;
    AddPurchase addPurchase;
    TextView customTotalPrice;
    TextView tvPriceEnd;
    TextView tvEdit;
    TextView tvEditShippingAddress;
    TextView tvShippingPrice;
    EditText etMmyy;
    Button btnPaymentMethod;
    RadioGroup rgTotal;
    static int rbTotal;
    TextView tvTotalShippingMethod;
    EditText etCardNumber;
    EditText etNameCard;
    EditText etCw;
    ScrollView svCustomerTotal;
    List<AddPurchase.Product> products;
    List<AddPurchase.SelectedParameter> productsParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info_total);
        Log.d("myLogs", "onCreate CustomerInfoTotal " + App.INSTANCE.getNum());

        etCardNumber = (EditText) findViewById(R.id.et_card_number);
        etNameCard = (EditText) findViewById(R.id.et_name_on_card);
        etMmyy = (EditText) findViewById(R.id.et_mmyy);
        etCw = (EditText) findViewById(R.id.et_cw);
        rgTotal = (RadioGroup) findViewById(R.id.rg_total);
        tvTotalShippingMethod = (TextView) findViewById(R.id.tv_total_shipping_method);
        svCustomerTotal = (ScrollView) findViewById(R.id.sv_customer_total);
        tvTotalShippingMethod.setTextColor(getResources().getColor(R.color.black));
        etCardNumber.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
        etNameCard.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
        etMmyy.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
        etCw.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_customer_total);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvShippingPrice = (TextView)findViewById(R.id.tv_shipping_price);
        tvEdit = (TextView)findViewById(R.id.tv_edit);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerInfoTotal.this, CustomerInfo.class);
                startActivity(intent);
            }
        });

        settings = this.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        requestInterface = Controller.getApi();

        tvEditShippingAddress = (TextView) findViewById(R.id.tv_edit_shipping_address);
        requestInterface.getUser(settings.getString("cookie", "")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                String address = user.getFirstAddress();
                if(response.code() == 200){
                    tvEditShippingAddress.setText(address);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnPaymentMethod = (Button) findViewById(R.id.btn_payment_method);

        birsdayTextChangeListener();

        tvShipping = (TextView) findViewById(R.id.tv_shipping);
        Intent intent = getIntent();
        int rb = intent.getIntExtra("rb", 0);
        double rbValue = intent.getDoubleExtra("rbValue", 0);
        tvShippingPrice.setText(String.valueOf(rbValue));
        switch (rb) {
            case 1:
                tvShipping.setText(getResources().getString(R.string.post));
                break;
            case 2:
                tvShipping.setText(getResources().getString(R.string.priority));
                break;
            case 3:
                tvShipping.setText(getResources().getString(R.string.express));
                break;
        }
        
        Log.d("myLogs", "count " + App.INSTANCE.getNum());

        for(int i = 0; i <= App.INSTANCE.getNum(); i++) {
            int id = settings.getInt("name" + String.valueOf(i), 0);
            Log.d("myLogs", "Id " + id);
        }

        items = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.custom_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CartAdapter adapter = new CartAdapter(this, items, this);
        recyclerView.setAdapter(adapter);

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
                    for (int i = 0; i <= App.INSTANCE.getNum(); i++) {
                        int id = settings.getInt("name" + String.valueOf(i), 0);
                        final Response response = requestInterface.getCart(id, getResources().getString(R.string.lang)).execute();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                items.add(response.body());
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        int price = settings.getInt("price", 0);
        double priceEnd;
        customTotalPrice = (TextView) findViewById(R.id.custom_total_price);
        tvPriceEnd = (TextView) findViewById(R.id.price_end);
        customTotalPrice.setText(String.valueOf(price));
        if(price > 0){
            priceEnd = (double)price + rbValue;
            tvPriceEnd.setText(String.valueOf(priceEnd));
        }
        btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rgTotal.getCheckedRadioButtonId()) {
                    case R.id.rb_credit_card:
                        rbTotal = 1;
                        break;
                    case R.id.rb_paypal:
                        rbTotal = 2;
                        break;
                    case R.id.rb_yandex_money:
                        rbTotal = 3;
                        break;
                    default:
                        rbTotal = 0;
                        break;
                }
                if (rbTotal == 0) {
                    svCustomerTotal.smoothScrollTo(0, tvTotalShippingMethod.getTop());
                    tvTotalShippingMethod.setTextColor(getResources().getColor(R.color.red));
                }
                if (rbTotal != 0) {
                    tvTotalShippingMethod.setTextColor(getResources().getColor(R.color.black));
                    if (etCardNumber.getText().toString().isEmpty()) {
                        svCustomerTotal.smoothScrollTo(0, etCardNumber.getTop());
                        etCardNumber.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text_red));
                        etNameCard.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etMmyy.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etCw.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                    } else if (etNameCard.getText().toString().isEmpty()) {
                        svCustomerTotal.smoothScrollTo(0, etNameCard.getTop());
                        etNameCard.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text_red));
                        etCardNumber.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etMmyy.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etCw.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                    } else if (etMmyy.getText().toString().isEmpty()) {
                        svCustomerTotal.smoothScrollTo(0, etMmyy.getTop());
                        etMmyy.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text_red));
                        etCardNumber.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etNameCard.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etCw.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                    } else if (etCw.getText().toString().isEmpty()) {
                        svCustomerTotal.smoothScrollTo(0, etCw.getTop());
                        etCw.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text_red));
                        etCardNumber.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etNameCard.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etMmyy.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                    } else {
                        btnPaymentMethod.setClickable(false);
                        tvTotalShippingMethod.setTextColor(getResources().getColor(R.color.black));
                        etCardNumber.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etNameCard.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etMmyy.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));
                        etCw.setBackground(getResources().getDrawable(R.drawable.rounded_edit_text));

                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    final Response response1 = requestInterface.getUser(settings.getString("cookie", "")).execute();
                                    User user = (User)response1.body();
                                    final String firstName = user.getFirstName();
                                    String lastName = user.getLastName();
                                    String country = user.getCountry();
                                    String state = user.getState();
                                    String city = user.getCity();
                                    String zipCode = user.getZipCode();
                                    String firstAddress = user.getFirstAddress();
                                    String secondAddress = user.getSecondAddress();
                                    String phoneNumber = user.getPhoneNumber();
                                    addPurchase = new AddPurchase();
                                    addPurchase.setFirstName(firstName);
                                    addPurchase.setLastName(lastName);
                                    addPurchase.setCountry(country);
                                    addPurchase.setState(state);
                                    addPurchase.setCity(city);
                                    addPurchase.setZipCode(zipCode);
                                    addPurchase.setFirstAddress(firstAddress);
                                    addPurchase.setSecondAddress(secondAddress);
                                    addPurchase.setPhoneNumber(phoneNumber);
                                    addPurchase.setShippingMethod(rbTotal);
                                    products = new ArrayList<AddPurchase.Product>();
                                    for (int i = 0; i <= App.INSTANCE.getNum(); i++) {
                                        Cart cart = (Cart) items.get(i);
                                        productsParameter = new ArrayList<AddPurchase.SelectedParameter>();
                                        AddPurchase.Product product = addPurchase.new Product();
                                        AddPurchase.SelectedParameter selectedParameter = addPurchase.new SelectedParameter();
                                        AddPurchase.SelectedParameter selectedParameter2 = addPurchase.new SelectedParameter();
                                        selectedParameter.setK("Size");
                                        selectedParameter2.setK("Color");
                                        product.setId(cart.getId());
                                        product.setQuantity(App.INSTANCE.getCount(i));
                                        if (cart.getId() == 11 || cart.getId() == 13 || cart.getId() == 14) {
                                            switch (App.INSTANCE.getSize(i)) {
                                                case 1:
                                                    selectedParameter.setV("XS");
                                                    break;
                                                case 2:
                                                    selectedParameter.setV("S");
                                                    break;
                                                case 3:
                                                    selectedParameter.setV("M");
                                                    break;
                                                case 4:
                                                    selectedParameter.setV("L");
                                                    break;
                                            }
                                        }
                                        if ((cart.getCategory() != 7 || cart.getCategory() != 6) && (cart.getParameters().get(0).getV() != null)) {
                                            selectedParameter2.setV(cart.getParameters().get(0).getV());
                                        }
                                        productsParameter.add(selectedParameter);
                                        productsParameter.add(selectedParameter2);
                                        product.setSelectedParameters(productsParameter);
                                        products.add(i, product);
                                    }
                                    addPurchase.setProducts(products);
                                    final Response response2 = requestInterface.addPurchase(settings.getString("cookie", ""), addPurchase).execute();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        t.start();

                        Log.d("myLogs", etMmyy.getText().toString());
                    }
                }
            }
        });
    }

    @Override
    public void onClickPlus(int position, int itemPrice) {
        int price = App.INSTANCE.getPrice();
        settings = this.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        editor = settings.edit();
        price = price - settings.getInt("price" + String.valueOf(position), 0);
        int summPrice = settings.getInt("price" + String.valueOf(position), 0) + itemPrice;
        editor.remove("price" + String.valueOf(position));
        editor.putInt("price" + String.valueOf(position), summPrice);
        editor.apply();
        price = price + settings.getInt("price" + String.valueOf(position), 0);
        App.INSTANCE.setPrice(price);
        Intent intent = getIntent();
        double rbValue = intent.getDoubleExtra("rbValue", 0);
        customTotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
        double priceEnd = (double)App.INSTANCE.getPrice() + rbValue;
        tvPriceEnd.setText(String.valueOf(priceEnd));
    }

    @Override
    public void onClickMinus(int position, int itemPrice, int itemCount) {
        int price = App.INSTANCE.getPrice();
        settings = this.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        editor = settings.edit();
        price = price - settings.getInt("price" + String.valueOf(position), 0);
        int summPrice = settings.getInt("price" + String.valueOf(position), 0) - itemPrice;
        editor.remove("price" + String.valueOf(position));
        editor.putInt("price" + String.valueOf(position), summPrice);
        editor.apply();
        price = price + settings.getInt("price" + String.valueOf(position), 0);
        App.INSTANCE.setPrice(price);
        customTotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
        Intent intent = getIntent();
        double rbValue = intent.getDoubleExtra("rbValue", 0);
        double priceEnd = (double)App.INSTANCE.getPrice() + rbValue;
        tvPriceEnd.setText(String.valueOf(priceEnd));
    }

    @Override
    public void onDeleteClick(int position, int itemPrice) {
        int price = App.INSTANCE.getPrice();
        int count = App.INSTANCE.getNum() - 1;
        App.INSTANCE.setNum(count);
        int k;
        int p;
        items.remove(position);
        int s;
        recyclerView.getAdapter().notifyDataSetChanged();
        settings = this.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        editor = settings.edit();
        price = price - settings.getInt("price" + String.valueOf(position), 0);
        App.INSTANCE.setPrice(price);
        for (int i = position; i <= App.INSTANCE.getNum(); i++) {
            editor.remove("name" + String.valueOf(i));
            editor.remove("price" + String.valueOf(i));
            k = settings.getInt("name" + String.valueOf(i + 1), 0);
            p = settings.getInt("price" + String.valueOf(i + 1), 0);
            editor.putInt("name" + String.valueOf(i), k);
            editor.putInt("price" + String.valueOf(i), p);
            editor.apply();
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        customTotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
        Intent intent = getIntent();
        double rbValue = intent.getDoubleExtra("rbValue", 0);
        double priceEnd = (double)App.INSTANCE.getPrice() + rbValue;
        tvPriceEnd.setText(String.valueOf(priceEnd));
    }

    private void birsdayTextChangeListener() {
        TextWatcher tw = new TextWatcher() {
            String current = "";
            String ddmm = "    ";
            Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 4; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 5) {
                        clean = clean + ddmm.substring(clean.length());
                    }

                    else {

                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d", day, mon);
                    }

                    clean = String.format("%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    etMmyy.setText(current);
                    etMmyy.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etMmyy.addTextChangedListener(tw);
    }
}
