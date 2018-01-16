package com.halfway.yogamylife;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orders extends AppCompatActivity {

    private static SharedPreferences settings;
    public static final String STORAGE_NAME = "StorageName";
    private static SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    List<Object> items;
    public static RequestInterface requestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_orders);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        requestInterface = Controller.getApi();
        items = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.orders_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PurchaseAdapter adapter = new PurchaseAdapter(this, items);
        recyclerView.setAdapter(adapter);

        settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        requestInterface.getAllPurchase(settings.getString("cookie", "")).enqueue(new Callback<List<Purchase>>() {
            @Override
            public void onResponse(Call<List<Purchase>> call, Response<List<Purchase>> response) {
                Log.d("myLogs", "Purchase code " + response.code());
                Log.d("myLogs", "Purchase count " + response.body().size());
                if (response.code() == 200) {
                    int n = 0;
                    for (int j = 0; j <= response.body().size() - 1; j++) {
                        if (response.code() == 200) {
                            for (int i = 0; i <= response.body().get(j).getProducts().size() - 1; i++) {
                                int itemI = i;
                                Log.d("myLogs", "itemI = " + itemI);
                                App.INSTANCE.setItem(itemI, n);
                                Log.d("myLogs", "n = " + n + "itemI 2 = " + App.INSTANCE.getItem(n));
                                items.add(response.body().get(j));
                                n++;
                            }
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Purchase>> call, Throwable t) {

            }

        });
    }
}
