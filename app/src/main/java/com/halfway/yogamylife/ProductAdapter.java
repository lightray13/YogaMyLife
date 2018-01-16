package com.halfway.yogamylife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    public List<Object> items;

    private final int HEADER = 0, PRODUCT = 1, IMAGE = 2, INFO = 3, COMMENT = 4, CART = 5;

    public ProductAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Product) {
            return PRODUCT;
        }
        if (items.get(position) instanceof Header) {
            return HEADER;
        }
        if (items.get(position) instanceof Image) {
            return IMAGE;
        }
        if (items.get(position) instanceof Info) {
            return INFO;
        }
        if (items.get(position) instanceof Comment) {
            return COMMENT;
        }
        if (items.get(position) instanceof Cart) {
            return CART;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        switch (viewType) {
            case PRODUCT:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
                vh = new ProductViewHolder(v1);
                break;
            case HEADER:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
                vh = new HeaderViewHolder(v2);
                break;
            case IMAGE:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
                vh = new ImageViewHolder(v3);
                break;
            case INFO:
                View v4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item, parent, false);
                vh = new InfoViewHolder(v4);
                break;
            case COMMENT:
                View v5 = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
                vh = new CommentViewHolder(v5);
                break;
            case CART:
                View v6 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, parent, false);
                vh = new CartViewHolder(v6);
                break;
            default:
                View v7 = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
                vh = new ProductViewHolder(v7);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (this.getItemViewType(position)) {
            case PRODUCT:
                final ProductViewHolder productViewHolder = (ProductViewHolder) holder;
                final Product product = (Product) items.get(position);
                Picasso.with(context).load("http://api.yogamy.live/img/" + product.getImages().get(1)).into((productViewHolder).imageView);
                productViewHolder.title.setText(product.getTitle());
                productViewHolder.price.setText(String.valueOf(product.getPrice()) + " " + context.getResources().getString(R.string.dollar));
                productViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, YogaLeggins.class);
                        intent.putExtra("id", product.getId());
                        intent.putExtra("size", product.getImages().size());
                        intent.putExtra("rating", product.getRating());
                        context.startActivity(intent);
                    }
                });
                break;
            case HEADER:
                Header header = (Header) items.get(position);
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.header.setText(header.getHeader());
                break;
            case IMAGE:
                final Image image = (Image) items.get(position);
                final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                Picasso.with(context).load("http://api.yogamy.live/img/" + image.getImages().get(position)).into((imageViewHolder).image);
                break;
            case INFO:
                final InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
                final Info info = (Info) items.get(position);
                infoViewHolder.title.setText(info.getTitle());
                infoViewHolder.price.setText(String.valueOf(info.getPrice()) + " " + context.getResources().getString(R.string.dollar));
                infoViewHolder.ratingBar.setRating(info.getRating());
                String str = info.getDescription();
                String []splitArray = str.split("\n\n");
                if(info.getCategory() == 3 || info.getId() == 28) {
                    infoViewHolder.description.setText(splitArray[0]);
                } else {
                    infoViewHolder.description.setText(splitArray[1]);
                }
                if(info.getCategory() == 7 || info.getCategory() == 6){
                    infoViewHolder.color.setVisibility(View.GONE);
                } else {
                    if(info.getParameters().get(0).getV() == null) {
                        break;
                    } else {
                        String color = info.getParameters().get(0).getV();
                        infoViewHolder.btnColor.setBackgroundColor(Color.parseColor(color));
                    }
                }

                if(info.getId() == 11 || info.getId() == 13 || info.getId() == 14) {
                    infoViewHolder.tvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                    infoViewHolder.tvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                    infoViewHolder.tvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                    infoViewHolder.tvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                    App.INSTANCE.setSize(1, App.INSTANCE.getNum() + 1);
                    infoViewHolder.tvSizeXS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            infoViewHolder.tvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                            infoViewHolder.tvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                            App.INSTANCE.setSize(1, App.INSTANCE.getNum() + 1);
                        }
                    });
                    infoViewHolder.tvSizeS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            infoViewHolder.tvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                            infoViewHolder.tvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                            App.INSTANCE.setSize(2, App.INSTANCE.getNum() + 1);
                        }
                    });
                    infoViewHolder.tvSizeM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            infoViewHolder.tvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                            infoViewHolder.tvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                            App.INSTANCE.setSize(3, App.INSTANCE.getNum() + 1);
                        }
                    });
                    infoViewHolder.tvSizeL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            infoViewHolder.tvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            infoViewHolder.tvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                            App.INSTANCE.setSize(4, App.INSTANCE.getNum() + 1);
                        }
                    });
                } else {
                    infoViewHolder.tvSize.setVisibility(View.GONE);
                    infoViewHolder.tvSizeXS.setVisibility(View.GONE);
                    infoViewHolder.tvSizeS.setVisibility(View.GONE);
                    infoViewHolder.tvSizeM.setVisibility(View.GONE);
                    infoViewHolder.tvSizeL.setVisibility(View.GONE);
                    App.INSTANCE.setSize(0, App.INSTANCE.getNum() + 1);
                }

                infoViewHolder.btnAddToCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = LayoutInflater.from(context);
                        View view = layoutInflater.inflate(R.layout.fragment_buttonsheet_card, null);
                        final BottomSheetDialog dialog = new BottomSheetDialog(context);
                        dialog.setContentView(view);
                        dialog.show();
                        TextView bsProductTitle = (TextView) dialog.findViewById(R.id.bs_product_title);
                        bsProductTitle.setText(info.getTitle());
                        TextView bsProductPrice = (TextView) dialog.findViewById(R.id.bs_product_price);
                        bsProductPrice.setText(info.getPrice() + " " + context.getResources().getString(R.string.dollar));
                        ImageView bsProductPhoto = (ImageView) dialog.findViewById(R.id.bs_product_photo);
                        Picasso.with(context).load("http://api.yogamy.live/img/" + info.getImages().get(0)).into(bsProductPhoto);
                        TextView bsTvColor = (TextView) dialog.findViewById(R.id.bs_tv_color);
                        final Button bsBtnColor = (Button) dialog.findViewById(R.id.bs_btn_color);
                        if(info.getCategory() == 7 || info.getCategory() == 6){
                            bsTvColor.setVisibility(View.GONE);
                        } else if (info.getParameters().get(0).getV() != null){
                            bsBtnColor.setBackgroundColor(Color.parseColor(info.getParameters().get(0).getV()));
                        }

                        TextView bsTvSize = (TextView) dialog.findViewById(R.id.bs_tv_size);
                        final TextView bsTvSizeXS = (TextView) dialog.findViewById(R.id.bs_tv_size_xs);
                        final TextView bsTvSizeS = (TextView) dialog.findViewById(R.id.bs_tv_size_s);
                        final TextView bsTvSizeM = (TextView) dialog.findViewById(R.id.bs_tv_size_m);
                        final TextView bsTvSizeL = (TextView) dialog.findViewById(R.id.bs_tv_size_l);

                        final Intent cartIntent = new Intent(context, ShoppingCart.class);

                        switch (App.INSTANCE.getSize(App.INSTANCE.getNum() + 1)){
                            case 0:
                                bsTvSize.setVisibility(View.GONE);
                                bsTvSizeXS.setVisibility(View.GONE);
                                bsTvSizeS.setVisibility(View.GONE);
                                bsTvSizeM.setVisibility(View.GONE);
                                bsTvSizeL.setVisibility(View.GONE);
                                App.INSTANCE.setSize(0, App.INSTANCE.getNum() + 1);
                                break;
                            case 1:
                                bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                                bsTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                App.INSTANCE.setSize(1, App.INSTANCE.getNum() + 1);
                                break;
                            case 2:
                                bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                                bsTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                App.INSTANCE.setSize(2, App.INSTANCE.getNum() + 1);
                                break;
                            case 3:
                                bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                                bsTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                App.INSTANCE.setSize(3, App.INSTANCE.getNum() + 1);
                                break;
                            case 4:
                                bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                bsTvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                                App.INSTANCE.setSize(4, App.INSTANCE.getNum() + 1);
                                break;
                        }

                        if(info.getId() == 11 || info.getId() == 13 || info.getId() == 14) {
                            bsTvSizeXS.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                                    bsTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                    App.INSTANCE.setSize(1, App.INSTANCE.getNum() + 1);
                                }
                            });
                            bsTvSizeS.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                                    bsTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                    App.INSTANCE.setSize(2, App.INSTANCE.getNum() + 1);
                                }
                            });
                            bsTvSizeM.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                                    bsTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                                    App.INSTANCE.setSize(3, App.INSTANCE.getNum() + 1);
                                }
                            });
                            bsTvSizeL.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bsTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                                    bsTvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                                    App.INSTANCE.setSize(4, App.INSTANCE.getNum() + 1);
                                }
                            });
                        }
                        final TextView tvQuantityCount = (TextView) dialog.findViewById(R.id.tv_quantity_count);
                        TextView bsTvQuantityMinus = (TextView) dialog.findViewById(R.id.bs_tv_quantity_minus);
                        TextView bsTvQuantityPlus = (TextView) dialog.findViewById(R.id.bs_tv_quantity_plus);
                        App.INSTANCE.setCount(1, App.INSTANCE.getNum() + 1);
                        tvQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(App.INSTANCE.getNum() + 1)));
                        bsTvQuantityMinus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int count = Integer.parseInt(String.valueOf(tvQuantityCount.getText()));
                                Log.d("myLogs", "ProductAdapter tvQuantityCount minus = " + tvQuantityCount);
                                if(count != 0 && count != 1) {
                                    count = count - 1;
                                    App.INSTANCE.setCount(count, App.INSTANCE.getNum() + 1 );
                                    tvQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(App.INSTANCE.getNum() + 1)));
                                    Log.d("myLogs", "ProductAdapter tvQuantityCount minus after = " + tvQuantityCount);

                                }
                            }
                        });

                        bsTvQuantityPlus.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                int count = Integer.parseInt(String.valueOf(tvQuantityCount.getText()));
                                Log.d("myLogs", "ProductAdapter tvQuantityCount plus = " + tvQuantityCount);
                                if(count < info.getQuantity()){
                                    count = count + 1;
                                    App.INSTANCE.setCount(count, App.INSTANCE.getNum() + 1);
                                    tvQuantityCount.setText(String.valueOf(App.INSTANCE.getCount(App.INSTANCE.getNum() + 1)));
                                    Log.d("myLogs", "ProductAdapter tvQuantityCount minus after = " + tvQuantityCount);
                                }
                            }
                        });



                        Button btnGoToCard = (Button) dialog.findViewById(R.id.btn_go_to_card);
                        btnGoToCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cartIntent.putExtra("id", info.getId());
                                cartIntent.putExtra("price", info.getPrice());
                                cartIntent.putExtra("size", App.INSTANCE.getSize(App.INSTANCE.getNum() + 1));
                                dialog.dismiss();
                                context.startActivity(cartIntent);
                            }
                        });
                        Button btnContinueShopping = (Button) dialog.findViewById(R.id.btn_continue_shopping);
                        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ShopLenta.class);
                                context.startActivity(intent);

                            }
                        });
                    }
                });

                if(info.getCategory() == 3) {
                    infoViewHolder.tvDescription.setText(splitArray[1]);
                } else if (info.getId() == 28) {
                    break;
                } else {
                    if(context.getResources().getString(R.string.lang).equals("en")) {
                        infoViewHolder.tvDescription.setText(splitArray[2]);
                    }
                }

                infoViewHolder.buttonTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        infoViewHolder.buttonTv.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.arrow_drop_up), null);
                        infoViewHolder.expandableRelativeLayout.toggle();
                        if (infoViewHolder.expandableRelativeLayout.isExpanded()) {
                            infoViewHolder.buttonTv.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.arrow_drop_down), null);
                        }
                    }
                });


                if(info.getId() == 11 || info.getId() == 13 || info.getId() == 14) {

                    infoViewHolder.tvShippingInfo.setText(info.getSubdescriptions().get(0).getV());

                    infoViewHolder.buttonShippingInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            infoViewHolder.buttonShippingInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.arrow_drop_up), null);
                            infoViewHolder.expandableRelativeLayoutSi.toggle();
                            if (infoViewHolder.expandableRelativeLayoutSi.isExpanded()) {
                                infoViewHolder.buttonShippingInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.arrow_drop_down), null);
                            }
                        }
                    });

                    infoViewHolder.tvReturnInfo.setText(info.getSubdescriptions().get(1).getV());

                    infoViewHolder.buttonReturnInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            infoViewHolder.buttonReturnInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.arrow_drop_up), null);
                            infoViewHolder.expandableRelativeLayoutRi.toggle();
                            if (infoViewHolder.expandableRelativeLayoutRi.isExpanded()) {
                                infoViewHolder.buttonReturnInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.arrow_drop_down), null);
                            }
                        }
                    });
                } else {
                    infoViewHolder.buttonShippingInfo.setVisibility(View.GONE);
                    infoViewHolder.buttonReturnInfo.setVisibility(View.GONE);
                }

                infoViewHolder.tvCountComments.setText(context.getResources().getString(R.string.customer_reviews) + " " + String.valueOf(info.getCountComments()));
                break;
            case COMMENT:
                final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
                final Comment comment = (Comment) items.get(position);
                if(!comment.getImage().isEmpty()) {
                    Picasso.with(context).load(comment.getImage()).into((commentViewHolder).ivPhoto);
                }
                commentViewHolder.tvName.setText(comment.getFirstName() + " " + comment.getLastName());
                String strComment = comment.getCreated();
                String []splitArrayComment = strComment.split("T");
                commentViewHolder.tvDate.setText(splitArrayComment[0]);
                commentViewHolder.rattingBarComment.setRating((comment.getRatings().get(0) + comment.getRatings().get(1) + comment.getRatings().get(2)) / 3);
                commentViewHolder.tvComment.setText(comment.getText());
                break;
            case CART:
                final CartViewHolder cartViewHolder = (CartViewHolder) holder;
                final Cart cart = (Cart) items.get(position);
                cartViewHolder.cartProductTitle.setText(cart.getTitle());
                cartViewHolder.cartProductPrice.setText("$" + String.valueOf(cart.getPrice()));
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

                if(cart.getId() == 11 || cart.getId() == 13 || cart.getId() == 14) {
                    cartViewHolder.cartTvSizeXS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.color));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                        }
                    });
                    cartViewHolder.cartTvSizeS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.color));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                        }
                    });
                    cartViewHolder.cartTvSizeM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.color));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.grey));
                        }
                    });
                    cartViewHolder.cartTvSizeL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartViewHolder.cartTvSizeXS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeS.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeM.setTextColor(context.getResources().getColor(R.color.grey));
                            cartViewHolder.cartTvSizeL.setTextColor(context.getResources().getColor(R.color.color));
                        }
                    });
                } else {
                    cartViewHolder.cartTvSize.setVisibility(View.GONE);
                    cartViewHolder.cartTvSizeXS.setVisibility(View.GONE);
                    cartViewHolder.cartTvSizeS.setVisibility(View.GONE);
                    cartViewHolder.cartTvSizeM.setVisibility(View.GONE);
                    cartViewHolder.cartTvSizeL.setVisibility(View.GONE);
                }

                final Intent quantityIntent = new Intent(context, ShoppingCart.class);
                final int count = 1;
                final Intent cartIntent = new Intent(context, ShoppingCart.class);
                cartViewHolder.cartQuantityCount.setText(String.valueOf(count));
                cartViewHolder.cartTvQuantityMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = Integer.parseInt(String.valueOf(cartViewHolder.cartQuantityCount.getText()));
                        if(count != 0 && count != 1) {
                            count = count - 1;
                            int price = App.INSTANCE.getPrice();
                            cartIntent.putExtra("quantity", count);
                        }
                        cartViewHolder.cartQuantityCount.setText(String.valueOf(count));
//                        cartViewHolder.cartQuantityCount.addTextChangedListener();
                    }
                });


                cartViewHolder.cartTvQuantityPlus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int count = Integer.parseInt(String.valueOf(cartViewHolder.cartQuantityCount.getText()));
                        if(count < cart.getQuantity()){
                            count = count + 1;
                            cartIntent.putExtra("quantity", count);
                            cartViewHolder.cartQuantityCount.setText(String.valueOf(count));
                        }
                    }
                });
                break;
        }
    }

        @Override
        public int getItemCount() {
            if (items == null)
                return 0;
            return items.size();
        }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView price;
        CardView cardView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_product);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.tv_header);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        RatingBar ratingBar;
        TextView description;
        TextView color;
        Button btnColor;
        TextView tvSize;
        TextView tvSizeXS;
        TextView tvSizeS;
        TextView tvSizeM;
        TextView tvSizeL;
        Button btnAddToCard;
        ExpandableRelativeLayout expandableRelativeLayout;
        TextView buttonTv;
        TextView tvDescription;
        ExpandableRelativeLayout expandableRelativeLayoutSi;
        TextView buttonShippingInfo;
        TextView tvShippingInfo;
        ExpandableRelativeLayout expandableRelativeLayoutRi;
        TextView buttonReturnInfo;
        TextView tvReturnInfo;
        TextView tvCountComments;

        public InfoViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title_info);
            price = (TextView) itemView.findViewById(R.id.tv_price_info);
            ratingBar = (RatingBar) itemView.findViewById(R.id.reting_bar);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            btnColor = (Button) itemView.findViewById(R.id.btn_color);
            color = (TextView) itemView.findViewById(R.id.tv_color);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
            tvSizeXS = (TextView) itemView.findViewById(R.id.tv_size_xs);
            tvSizeS = (TextView) itemView.findViewById(R.id.tv_size_s);
            tvSizeM = (TextView) itemView.findViewById(R.id.tv_size_m);
            tvSizeL = (TextView) itemView.findViewById(R.id.tv_size_l);
            btnAddToCard = (Button) itemView.findViewById(R.id.btn_add_to_card);
            expandableRelativeLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.text_Exapndable_Layout);
            buttonTv = (TextView) itemView.findViewById(R.id.button_tv);
            tvDescription = (TextView) itemView.findViewById(R.id.text_description);
            expandableRelativeLayoutSi = (ExpandableRelativeLayout) itemView.findViewById(R.id.text_Exapndable_Layout_si);
            buttonShippingInfo = (TextView) itemView.findViewById(R.id.button_shipping_info);
            tvShippingInfo = (TextView) itemView.findViewById(R.id.text_shipping_info);
            expandableRelativeLayoutRi = (ExpandableRelativeLayout) itemView.findViewById(R.id.text_Exapndable_Layout_ri);
            buttonReturnInfo = (TextView) itemView.findViewById(R.id.button_return_info);
            tvReturnInfo = (TextView) itemView.findViewById(R.id.text_return_info);
            tvCountComments = (TextView) itemView.findViewById(R.id.tv_count_comments);
        }


    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvDate;
        RatingBar rattingBarComment;
        TextView tvComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvName = (TextView) itemView.findViewById(R.id.TV_name_facebook);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            rattingBarComment = (RatingBar) itemView.findViewById(R.id.reting_bar_comment);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
        }
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
        TextView count;
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


