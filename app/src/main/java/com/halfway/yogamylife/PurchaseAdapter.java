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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    public List<Object> items;

    private final int PURCHASE = 0;
    public static RequestInterface requestInterface;

    public PurchaseAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Purchase) {
            return PURCHASE;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            case PURCHASE:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false);
                vh = new PurchaseAdapter.PurchaseViewHolder(v1);
                break;
            default:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false);
                vh = new PurchaseAdapter.PurchaseViewHolder(v2);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (this.getItemViewType(position)) {
            case PURCHASE:
                final PurchaseAdapter.PurchaseViewHolder purchaseViewHolder = (PurchaseAdapter.PurchaseViewHolder) holder;
                final Purchase purchase = (Purchase) items.get(position);
                requestInterface = Controller.getApi();
                Log.d("myLogs", "App.INSTANCE.getItem() " + App.INSTANCE.getItem(position) + " size " + purchase.getProducts().size());
                Log.d("myLogs", String.valueOf(purchase.getProducts().get(App.INSTANCE.getItem(position)).getId()));
                requestInterface.getProduct(purchase.getProducts().get(App.INSTANCE.getItem(position)).getId(), context.getResources().getString(R.string.lang)).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Product product = response.body();
                        purchaseViewHolder.purchaseProductTitle.setText(product.getTitle());
                        purchaseViewHolder.purchaseProductPrice.setText(String.valueOf(product.getPrice()) + " " + context.getResources().getString(R.string.dollar));
                        Picasso.with(context).load("http://api.yogamy.live/img/" + product.getImages().get(0)).into(purchaseViewHolder.purchaseProductPhoto);
                        Log.d("myLogs", "purchase color: " + App.INSTANCE.getItem(position));
                        Log.d("myLogs", "purchase color: " + purchase.getProducts().get(App.INSTANCE.getItem(position)).getSelectedParameters().get(1).getV());
                        if((product.getCategory() != 7 || product.getCategory() != 6) && (purchase.getProducts().get(App.INSTANCE.getItem(position)).getSelectedParameters().get(1).getV() != null)) {
                            String color = purchase.getProducts().get(App.INSTANCE.getItem(position)).getSelectedParameters().get(1).getV();
                            purchaseViewHolder.purchaseBtnColor.setBackgroundColor(Color.parseColor(color));
                        } else {
                            purchaseViewHolder.purchaseTvColor.setVisibility(View.GONE);
                        }
                        Log.d("myLogs", "COLOR: " + purchase.getProducts().get(App.INSTANCE.getItem(position)).getSelectedParameters().get(0).getV());
                        if((product.getId() == 11 || product.getId() == 13 || product.getId() == 14) && ((purchase.getProducts().get(App.INSTANCE.getItem(position)).getSelectedParameters().get(0).getV() != null))) {
                            switch (purchase.getProducts().get(App.INSTANCE.getItem(position)).getSelectedParameters().get(0).getV()){
                                case "XS":
                                    purchaseViewHolder.purchaseTvSize.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeM.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeL.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                                    purchaseViewHolder.purchaseTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                    break;
                                case "S":
                                    purchaseViewHolder.purchaseTvSize.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeM.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeL.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                                    purchaseViewHolder.purchaseTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                    break;
                                case "M":
                                    purchaseViewHolder.purchaseTvSize.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeM.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeL.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                                    purchaseViewHolder.purchaseTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                    break;
                                case "L":
                                    purchaseViewHolder.purchaseTvSize.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeS.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeM.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeL.setVisibility(View.VISIBLE);
                                    purchaseViewHolder.purchaseTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                    purchaseViewHolder.purchaseTvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                                    break;
                            }
                        } else {
                            purchaseViewHolder.purchaseTvSize.setVisibility(View.GONE);
                            purchaseViewHolder.purchaseTvSizeXS.setVisibility(View.GONE);
                            purchaseViewHolder.purchaseTvSizeS.setVisibility(View.GONE);
                            purchaseViewHolder.purchaseTvSizeM.setVisibility(View.GONE);
                            purchaseViewHolder.purchaseTvSizeL.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
                purchaseViewHolder.purchaseQuantityCount.setText(String.valueOf(purchase.getProducts().get(App.INSTANCE.getItem(position)).getQuantity()));
                String str = purchase.getCreated();
                String []splitArray = str.split("T");
                purchaseViewHolder.purchaseDate.setText(splitArray[0]);
                purchaseViewHolder.purchaseOrder.setText(String.valueOf(purchase.getId()));
                purchaseViewHolder.purchaseStatus.setText(String.valueOf(purchase.getStatus()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView purchaseProductTitle;
        TextView purchaseProductPrice;
        ImageView purchaseProductPhoto;
        Button purchaseBtnColor;
        TextView purchaseQuantityCount;
        TextView purchaseDate;
        TextView purchaseOrder;
        TextView purchaseStatus;
        TextView purchaseTvSize;
        TextView purchaseTvSizeXS;
        TextView purchaseTvSizeS;
        TextView purchaseTvSizeM;
        TextView purchaseTvSizeL;
        TextView purchaseTvColor;



        public PurchaseViewHolder(View itemView) {
            super(itemView);
            purchaseProductTitle = (TextView) itemView.findViewById(R.id.purchase_product_title);
            purchaseProductPrice = (TextView) itemView.findViewById(R.id.purchase_product_price);
            purchaseProductPhoto = (ImageView) itemView.findViewById(R.id.purchase_product_photo);
            purchaseBtnColor = (Button) itemView.findViewById(R.id.purchase_btn_color);
            purchaseQuantityCount = (TextView) itemView.findViewById(R.id.purchase_quantity_count);
            purchaseDate = (TextView) itemView.findViewById(R.id.purchase_date);
            purchaseOrder = (TextView) itemView.findViewById(R.id.purchase_order);
            purchaseStatus = (TextView) itemView.findViewById(R.id.purchase_status);
            purchaseTvSize = (TextView) itemView.findViewById(R.id.purchase_tv_size);
            purchaseTvSizeXS = (TextView) itemView.findViewById(R.id.purchase_tv_size_xs);
            purchaseTvSizeS = (TextView) itemView.findViewById(R.id.purchase_tv_size_s);
            purchaseTvSizeM = (TextView) itemView.findViewById(R.id.purchase_tv_size_m);
            purchaseTvSizeL = (TextView) itemView.findViewById(R.id.purchase_tv_size_l);
            purchaseTvColor = (TextView) itemView.findViewById(R.id.purchase_tv_color);
        }
    }
}
