package com.halfway.yogamylife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCart extends AppCompatActivity implements OnItemClick {

    RecyclerView recyclerView;
    List<Object> items;
    public static RequestInterface requestInterface;
//    public static int count = -1;
    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    public static final String STORAGE_NAME = "StorageName";
    final static String myLogs = "MyLogs";
    public TextView subtotalPrice;
    TextView tvCardEmpty;
    Button btnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

//        Log.d("myLogs", "onCreate ShoppingCard " + App.INSTANCE.getNum());

        subtotalPrice = (TextView) findViewById(R.id.subtotal_price);
        tvCardEmpty = (TextView) findViewById(R.id.tv_card_empty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_leggins);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCart.this, ShopLenta.class);
                startActivity(intent);
//                onBackPressed();
            }
        });

        btnCheckOut = (Button) findViewById(R.id.btn_check_out);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
//                editor = settings.edit();
                if(subtotalPrice.getText().toString().equals("0")) {
                    tvCardEmpty.setVisibility(View.VISIBLE);
                } else {
                    tvCardEmpty.setVisibility(View.INVISIBLE);
                    editor.putInt("count", App.INSTANCE.getNum());
                    editor.putInt("price", Integer.parseInt(subtotalPrice.getText().toString()));
                    editor.apply();
                    Intent intent = new Intent(ShoppingCart.this, CustomerInfo.class);
                    startActivity(intent);
                }
            }
        });

//        int count = App.INSTANCE.getNum() + 1;
//        App.INSTANCE.setNum(count);

        requestInterface = Controller.getApi();
        items = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.cart_product_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CartAdapter adapter = new CartAdapter(this, items, this);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        int cardBool = intent.getIntExtra("cardBool", 0);
        if(cardBool != 3) {
            int count = App.INSTANCE.getNum() + 1;
            App.INSTANCE.setNum(count);

            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i <= App.INSTANCE.getNum(); i++) {
                            final Response response = requestInterface.getCart(getPref(i), getResources().getString(R.string.lang)).execute();
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
            getPrefPriceAll();
        } else {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 0; i <= App.INSTANCE.getNum(); i++) {
                            final Response response = requestInterface.getCart(getPref(i), getResources().getString(R.string.lang)).execute();
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
        }

        subtotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
        if(subtotalPrice.getText().toString().equals("0")) {
            tvCardEmpty.setVisibility(View.VISIBLE);
        } else {
            tvCardEmpty.setVisibility(View.INVISIBLE);
        }
//        getPrefPriceAll();
//        Log.d("myLogs", "ShoppingCard " + App.INSTANCE.getSize(App.INSTANCE.getNum()));
    }


    public int getPref(int i) {
        Intent intentCard = getIntent();
        int cardBool = intentCard.getIntExtra("cardBool", 0);
        if(cardBool != 3) {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        subtotalPrice = (TextView) findViewById(R.id.subtotal_price);
        settings = this.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        editor = settings.edit();

        String name = "name" + String.valueOf(App.INSTANCE.getNum());
        editor.putInt(name, id);
        editor.apply();
        int k;

        k = settings.getInt("name" + String.valueOf(i), 0);
        return k; } else {
            int k;

            k = settings.getInt("name" + String.valueOf(i), 0);
            return k;
        }
    }

    public void getPrefPriceAll() {
        Intent intent = getIntent();
        int price = intent.getIntExtra("price", 0) * App.INSTANCE.getCount(App.INSTANCE.getNum());
        settings = this.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        editor = settings.edit();
        String priceName = "price" + String.valueOf(App.INSTANCE.getNum());
        editor.putInt(priceName, price);
        editor.apply();

//        int k = App.INSTANCE.getPrice();
        int k = 0;
        for (int i = 0; i <= App.INSTANCE.getNum(); i++) {
            k = k + settings.getInt("price" + String.valueOf(i), 0);
            App.INSTANCE.setPrice(k);
            subtotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
//            int p = App.INSTANCE.getCount(i);
//            App.INSTANCE.setCount(p, i + 1);

        }
    }

    @Override
    public void onClickPlus(int position, int itemPrice) {
//        int price = Integer.parseInt(subtotalPrice.getText().toString());
        int price = App.INSTANCE.getPrice();
        price = price - settings.getInt("price" + String.valueOf(position), 0);
        int summPrice = settings.getInt("price" + String.valueOf(position), 0) + itemPrice;
        editor.remove("price" + String.valueOf(position));
        editor.putInt("price" + String.valueOf(position), summPrice);
        editor.apply();
//        price = price + settings.getInt("price" + String.valueOf(position), 0);
        price = price + settings.getInt("price" + String.valueOf(position), 0);
        App.INSTANCE.setPrice(price);
        subtotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));

    }

    @Override
    public void onClickMinus(int position, int itemPrice, int itemCount) {
//        int price = Integer.parseInt(subtotalPrice.getText().toString());
        int price = App.INSTANCE.getPrice();
//        App.INSTANCE.setCount(1, position);
        price = price - settings.getInt("price" + String.valueOf(position), 0);
        int summPrice = settings.getInt("price" + String.valueOf(position), 0) - itemPrice;
        editor.remove("price" + String.valueOf(position));
        editor.putInt("price" + String.valueOf(position), summPrice);
        editor.apply();
        price = price + settings.getInt("price" + String.valueOf(position), 0);
        App.INSTANCE.setPrice(price);
        subtotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
    }

    @Override
    public void onDeleteClick(int position, int itemPrice) {

//        int price = Integer.parseInt(subtotalPrice.getText().toString());

        int price = App.INSTANCE.getPrice();

//        App.INSTANCE.setCount(1, App.INSTANCE.getNum());

        int count = App.INSTANCE.getNum() - 1;
        App.INSTANCE.setNum(count);

        int k;
        int p;
        int s;
        items.remove(position);
        recyclerView.getAdapter().notifyDataSetChanged();
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
        subtotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
        if(subtotalPrice.getText().toString().equals("0")) {
            tvCardEmpty.setVisibility(View.VISIBLE);
        } else {
            tvCardEmpty.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
//        Log.d("myLogs", "ShoppingCard onResume " + App.INSTANCE.getNum());
        subtotalPrice.setText(String.valueOf(App.INSTANCE.getPrice()));
        recyclerView = (RecyclerView) findViewById(R.id.cart_product_recycle_view);
        recyclerView.getAdapter().notifyDataSetChanged();
        super.onResume();
    }

}
