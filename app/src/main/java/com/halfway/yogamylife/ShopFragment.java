package com.halfway.yogamylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    public static RequestInterface requestInterface;
    RecyclerView recyclerView;
    List<Object> items;
    Button btnCard;
    ProgressBar pbShop;
    int stop = 0;


    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        btnCard = (Button) view.findViewById(R.id.btn_card);
        pbShop = (ProgressBar) view.findViewById(R.id.pb_shop);

        requestInterface = Controller.getApi();

        items = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.product_recycle_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 4 || position == 18 || position == 27 || position == 43 || position == 51 || position == 55 || position == 58 || position == 68) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        ProductAdapter adapter = new ProductAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);


        final Header header = new Header();
        header.setHeader(getResources().getString(R.string.yoga_leggins));

        final Header header2 = new Header();
        header2.setHeader(getResources().getString(R.string.mats_3_5_mm));

        final Header header3 = new Header();
        header3.setHeader(getResources().getString(R.string.mats_1_5_mm));

        final Header header4 = new Header();
        header4.setHeader(getResources().getString(R.string.mats_1_0_mm));

        final Header header5 = new Header();
        header5.setHeader(getResources().getString(R.string.towels));

        final Header header6 = new Header();
        header6.setHeader(getResources().getString(R.string.hand_towels));

        final Header header7 = new Header();
        header7.setHeader(getResources().getString(R.string.mats_bags));

        final Header header8 = new Header();
        header8.setHeader(getResources().getString(R.string.yoga_accessories));

        final Header header9 = new Header();
        header9.setHeader(getResources().getString(R.string.thermos));

        final int mass[] = {11, 13, 14, 59, 62, 64, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 79, 80, 81, 82, 83,
        84, 85, 86, 27, 30, 33, 34, 52, 53, 54, 55, 56, 57, 58, 60, 61, 63, 65, 29, 31, 39, 40, 41, 43, 44, 46,
        48, 50, 77, 78, 28, 35, 37, 38, 42, 45, 47, 49, 51, 76};
        pbShop.setVisibility(ProgressBar.VISIBLE);
        Thread t = new Thread(new Runnable() {
            public void run() {
                    try {
                        final Response massRes[] = new Response[61];
                        for(int i = 0; i < massRes.length; i++) {
                            massRes[i] =  requestInterface.getProduct(mass[i], getResources().getString(R.string.lang)).execute();
                            if(isAdded()){
                                getResources().getString(R.string.lang);
                            } else {
                                stop = 1;
                                break;
                            }
                        }
                        if(stop != 1) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pbShop.setVisibility(ProgressBar.INVISIBLE);
                                    items.add(header);
                                    for(int i = 0; i < 3; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header2);
                                    for(int i = 3; i < 16; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header3);
                                    for(int i = 16; i < 24; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header4);
                                    for(int i = 24; i < 39; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header5);
                                    for(int i = 39; i < 46; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header6);
                                    for(int i = 46; i < 49; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header7);
                                    for(int i = 49; i < 51; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header8);
                                    for(int i = 51; i < 60; i++) {
                                        items.add(massRes[i].body());
                                    }
                                    items.add(header9);
                                    items.add(massRes[60].body());
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

        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingCart.class);
                intent.putExtra("cardBool", 3);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
