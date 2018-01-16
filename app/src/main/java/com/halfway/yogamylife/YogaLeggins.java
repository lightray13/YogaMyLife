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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogaLeggins extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    List<Object> items;
    List<Object> items2;
    List<Object> items3;
    public static RequestInterface requestInterface;
    private static SharedPreferences settings;
    public static final String STORAGE_NAME = "StorageName";
    final static String LOG_TAG = "myLogs";
    public final static String BACK = "back";
    Button btnWriteReview;
    ExpandableRelativeLayout textExapndableLayoutReview;
    RatingBar rbOveralRating;
    RatingBar rbRecommendRating;
    RatingBar rbQualityRating;
    EditText etComment;
    TextView tvTextLength;
    Button btnAddComment;
    public static int overalRating;
    public static int recommendRating;
    public static int qualityRating;
    List<Integer> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_leggins);

        btnWriteReview = (Button) findViewById(R.id.btn_write_review);
        textExapndableLayoutReview = (ExpandableRelativeLayout) findViewById(R.id.text_Exapndable_Layout_review);
        rbOveralRating = (RatingBar) findViewById(R.id.rb_overal_rating);
        rbRecommendRating = (RatingBar) findViewById(R.id.rb_recommend_rating);
        rbQualityRating = (RatingBar) findViewById(R.id.rb_quality_rating);
        etComment = (EditText) findViewById(R.id.et_comment);
        tvTextLength = (TextView) findViewById(R.id.tv_text_length);
        btnAddComment = (Button) findViewById(R.id.btn_add_comment);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_leggins);

        requestInterface = Controller.getApi();
        items = new ArrayList<>();
        items2 = new ArrayList<>();
        items3 = new ArrayList<>();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        int size = intent.getIntExtra("size", 0);
        final float rating = intent.getFloatExtra("rating", 0);

        recyclerView = (RecyclerView) findViewById(R.id.leggins_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ProductAdapter adapter = new ProductAdapter(this, items);
        recyclerView.setAdapter(adapter);

                requestInterface.getInfo(id, getResources().getString(R.string.lang)).enqueue(new Callback <Info>() {
                @Override
                public void onResponse(Call <Info> call, Response <Info> response) {
                    Toast.makeText(YogaLeggins.this, "данные пришли", Toast.LENGTH_SHORT).show();
                    toolbar.setTitle(response.body().getTitle());
//                    setSupportActionBar(toolbar);
                    items.add(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call <Info> call, Throwable t) {
                    Toast.makeText(YogaLeggins.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView2 = (RecyclerView) findViewById(R.id.images_recycle_view);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        ProductAdapter adapter2 = new ProductAdapter(this, items2);
        recyclerView2.setAdapter(adapter2);



        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Intent intent = getIntent();
                    int id = intent.getIntExtra("id", 0);
                    final int size = intent.getIntExtra("size", 0);
                    final Response response = requestInterface.getImage(id).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0; i < size; i++){
                                items2.add(response.body());
                            }
                            recyclerView2.getAdapter().notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        recyclerView3 = (RecyclerView) findViewById(R.id.comments_recycle_view);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(layoutManager3);

        ProductAdapter adapter3 = new ProductAdapter(this, items3);
        recyclerView3.setAdapter(adapter3);


        requestInterface.getComment(id).enqueue(new Callback <List<Comment>>() {
            @Override
            public void onResponse(Call <List<Comment>> call, Response <List<Comment>> response) {
                Toast.makeText(YogaLeggins.this, "данные пришли", Toast.LENGTH_SHORT).show();
                items3.addAll(response.body());
                recyclerView3.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call <List<Comment>> call, Throwable t) {
                Toast.makeText(YogaLeggins.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, String.valueOf(t));
            }
        });

        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnWriteReview.setText(getResources().getString(R.string.hide));
                textExapndableLayoutReview.toggle();
                if (textExapndableLayoutReview.isExpanded()) {
                    btnWriteReview.setText(getResources().getString(R.string.write_a_review));
                }
            }
        });

        settings = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        rbOveralRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                overalRating = (int)rating;
                Log.d("myLogs", "OveralRating = " + String.valueOf(overalRating));

            }
        });
        rbRecommendRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                recommendRating = (int)rating;
                Log.d("myLogs", "RecommendRating = " + String.valueOf(rating));
            }
        });
        rbQualityRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                qualityRating = (int)rating;
                Log.d("myLogs", "QualityRating = " + String.valueOf(rating));
            }
        });

        Log.d("myLogs", String.valueOf(rbOveralRating.getRating()));

        TextWatcher twComment = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = etComment.getText().toString();
                int length = text.length();
                tvTextLength.setText(String.valueOf(length) + "-300");
                Log.d("myLogs", "text is " + String.valueOf(length));
            }
        };

        etComment.addTextChangedListener(twComment);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int id = intent.getIntExtra("id", 0);
                final AddComment addComment = new AddComment();
                addComment.setProductId(id);
                ratings = new ArrayList<>();
                ratings.add(0, overalRating);
                ratings.add(1, recommendRating);
                ratings.add(2, qualityRating);
                addComment.setRatings(ratings);
                addComment.setRatings(ratings);
                addComment.setText(etComment.getText().toString());
                Log.d("myLogs", "btnAddComment OveralRating = " + ratings.get(0) + ratings.get(1) + ratings.get(2));
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Intent intent = getIntent();
                            int id = intent.getIntExtra("id", 0);
                            final Response response1 = requestInterface.addComment(settings.getString("cookie", ""), addComment).execute();
                            final Response <List<Comment>>response2 = requestInterface.getComment(id).execute();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("myLogs", "Add comment: " + response1.code());
                                    Log.d("myLogs", "get(0) = " + response2.body().get(0).getText());
                                    items3.add(0, response2.body().get(0));
                                    recyclerView3.getAdapter().notifyItemInserted(0);

                                    recyclerView3.getAdapter().notifyDataSetChanged();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();
                textExapndableLayoutReview.toggle();
            }
        });
    }



}
