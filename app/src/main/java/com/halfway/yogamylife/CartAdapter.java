package com.halfway.yogamylife;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final String STORAGE_NAME = "StorageName";

    Context context;
    public List<Object> items;
    private OnItemClick mCallback;

    private final int CART = 0;

    public CartAdapter(Context context, List<Object> items, OnItemClick mCallback) {
        this.mCallback = mCallback;
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Cart) {
            return CART;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            case CART:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, parent, false);
                vh = new CartViewHolder(v1);
                break;
            default:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, parent, false);
                vh = new CartViewHolder(v2);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (this.getItemViewType(position)) {
            case CART:
                final CartViewHolder cartViewHolder = (CartViewHolder) holder;
                final Cart cart = (Cart) items.get(position);
                cartViewHolder.cartProductTitle.setText(cart.getTitle());
                cartViewHolder.cartProductPrice.setText(String.valueOf(cart.getPrice()) + " " + context.getResources().getString(R.string.dollar));
                Picasso.with(context).load("http://api.yogamy.live/img/" + cart.getImages().get(0)).into(cartViewHolder.cartProductPhoto);
                if(cart.getCategory() == 7 || cart.getCategory() == 6){
                    cartViewHolder.cartTvColor.setVisibility(View.GONE);
                } else {
                    if(cart.getParameters().get(0).getV() == null) {
                        break;
                    } else {
                        String color = cart.getParameters().get(0).getV();
                        cartViewHolder.cartBtnColor.setBackgroundColor(Color.parseColor(color));
                    }
                }

                switch (App.INSTANCE.getSize(position)) {
                    case 0:
                        cartViewHolder.cartTvSize.setVisibility(View.GONE);
                        cartViewHolder.cartTvSizeXS.setVisibility(View.GONE);
                        cartViewHolder.cartTvSizeS.setVisibility(View.GONE);
                        cartViewHolder.cartTvSizeM.setVisibility(View.GONE);
                        cartViewHolder.cartTvSizeL.setVisibility(View.GONE);
                        break;
                    case 1:
                        cartViewHolder.cartTvSize.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeM.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeL.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                        cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                        break;
                    case 2:
                        cartViewHolder.cartTvSize.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeM.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeL.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                        cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                        break;
                    case 3:
                        cartViewHolder.cartTvSize.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeM.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeL.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                        cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                        break;
                    case 4:
                        cartViewHolder.cartTvSize.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeS.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeM.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeL.setVisibility(View.VISIBLE);
                        cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                        cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                        break;
                }
                if(cart.getId() == 11 || cart.getId() == 13 || cart.getId() == 14) {
                    cartViewHolder.cartTvSizeXS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                            App.INSTANCE.setSize(1, App.INSTANCE.getNum());
                        }
                    });
                    cartViewHolder.cartTvSizeS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                            App.INSTANCE.setSize(2, App.INSTANCE.getNum());
                        }
                    });
                    cartViewHolder.cartTvSizeM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                            App.INSTANCE.setSize(3, App.INSTANCE.getNum());
                        }
                    });
                    cartViewHolder.cartTvSizeL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                            App.INSTANCE.setSize(4, App.INSTANCE.getNum());
                        }
                    });
                }
                cartViewHolder.cartQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(position)));
                cartViewHolder.cartTvQuantityMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = Integer.parseInt(String.valueOf(cartViewHolder.cartQuantityCount.getText()));
                        if(count != 0 && count != 1) {
                            count = count - 1;
                            App.INSTANCE.setCount(count, position);
                            mCallback.onClickMinus(position, cart.getPrice(), App.INSTANCE.getCount(position));
                            cartViewHolder.cartQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(position)));
                        }
                    }
                });


                cartViewHolder.cartTvQuantityPlus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int count = Integer.parseInt(String.valueOf(cartViewHolder.cartQuantityCount.getText()));
                        if(App.INSTANCE.getCount(position) < cart.getQuantity()){
                            count = count + 1;
                            App.INSTANCE.setCount(count, position);
                            mCallback.onClickPlus(position, cart.getPrice());
                            cartViewHolder.cartQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(position)));

                        }
                    }
                });

                cartViewHolder.cardBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("myLogs", "CartAdapter position:" + position);
                        Log.d("myLogs", "CartAdapter size:" + App.INSTANCE.getSize(position));
                        int k;
                        int s;
                        mCallback.onDeleteClick(position, cart.getPrice());
                        for (int i = position; i <= items.size(); i++) {
                            k = App.INSTANCE.getCount(position + 1);
                            App.INSTANCE.setCount(k, position);

                            s = App.INSTANCE.getSize(i + 1);
                            App.INSTANCE.setSize(s, i);
                        }
                        cartViewHolder.cartQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(position)));
                    }
                    });
        }
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView cartProductTitle;
        TextView cartProductPrice;
        ImageView cartProductPhoto;
        TextView cartTvColor;
        Button cartBtnColor;
        TextView cartTvSize;
        TextView cartTvSizeXS;
        TextView cartTvSizeS;
        TextView cartTvSizeM;
        TextView cartTvSizeL;
        TextView cartQuantityCount;
        TextView cartTvQuantityMinus;
        TextView cartTvQuantityPlus;
        Button cardBtnDelete;

        public CartViewHolder(View itemView) {
            super(itemView);

            cartProductTitle = (TextView) itemView.findViewById(R.id.cart_product_title);
            cartProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
            cartProductPhoto = (ImageView) itemView.findViewById(R.id.cart_product_photo);
            cartTvColor = (TextView) itemView.findViewById(R.id.cart_tv_color);
            cartBtnColor = (Button) itemView.findViewById(R.id.cart_btn_color);
            cartTvSize = (TextView) itemView.findViewById(R.id.cart_tv_size);
            cartTvSizeXS = (TextView) itemView.findViewById(R.id.cart_tv_size_xs);
            cartTvSizeS = (TextView) itemView.findViewById(R.id.cart_tv_size_s);
            cartTvSizeM = (TextView) itemView.findViewById(R.id.cart_tv_size_m);
            cartTvSizeL = (TextView) itemView.findViewById(R.id.cart_tv_size_l);
            cartQuantityCount = (TextView) itemView.findViewById(R.id.cart_quantity_count);
            cartTvQuantityMinus = (TextView) itemView.findViewById(R.id.cart_tv_quantity_minus);
            cartTvQuantityPlus = (TextView) itemView.findViewById(R.id.cart_tv_quantity_plus);
            cardBtnDelete = (Button) itemView.findViewById(R.id.cart_btn_delete);
        }

    }

}
