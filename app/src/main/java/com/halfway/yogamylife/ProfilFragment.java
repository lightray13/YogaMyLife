package com.halfway.yogamylife;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    RelativeLayout rlEditProfil;
    RelativeLayout btnOrders;
    RelativeLayout btnShippingAddress;
    RelativeLayout btnSupport;
    RelativeLayout btnHelp;
    Button btnLogOut;
    TextView profilFirstName;
    TextView profilLastName;
    ImageView ivProfil;
    public static RequestInterface requestInterface;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    public static final String STORAGE_NAME = "StorageName";

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        rlEditProfil = (RelativeLayout) view.findViewById(R.id.rl_edit_profil);
        btnOrders = (RelativeLayout) view.findViewById(R.id.btn_orders);
        btnShippingAddress = (RelativeLayout) view.findViewById(R.id.btn_shipping_address);
        btnSupport = (RelativeLayout) view.findViewById(R.id.btn_support);
        btnHelp = (RelativeLayout) view.findViewById(R.id.btn_help);
        btnLogOut = (Button) view.findViewById(R.id.btn_log_out);
        profilFirstName = (TextView) view.findViewById(R.id.tv_profil_first_name);
        profilLastName = (TextView) view.findViewById(R.id.tv_profil_last_name);
        ivProfil = (ImageView) view.findViewById(R.id.iv_profil);

        requestInterface = Controller.getApi();
        settings = getActivity().getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

        requestInterface.getUser(settings.getString("cookie", "")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String image = user.getImage();
                Log.d("myLogs", "image " + image);

                if(!firstName.isEmpty()){
                    profilFirstName.setText(firstName);
                }
                if(!lastName.isEmpty()){
                    profilLastName.setText(lastName);
                }
                if(!image.isEmpty()) {
                    Log.d("myLogs", "Yes!");
                    Picasso.with(getActivity()).load(image).into(ivProfil);
                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        rlEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Orders.class);
                startActivity(intent);
            }
        });
        btnShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingAddress.class);
                startActivity(intent);
            }
        });
        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Support.class);
                startActivity(intent);
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Help.class);
                startActivity(intent);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInterface.logoutUser(settings.getString("cookie", "")).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("myLogs", "logout user " + response.code());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                editor = settings.edit();
                editor.remove("cookie");
                editor.remove("email");
                editor.remove("first_name");
                editor.remove("last_name");
                editor.remove("image");
                editor.remove("description");
                editor.remove("address");
                editor.remove("city");
                editor.remove("country");
                editor.remove("state");
                editor.remove("zip_code");
                editor.remove("phone");
                editor.putString("cookie", "");
                editor.apply();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
