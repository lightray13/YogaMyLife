package com.halfway.yogamylife;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class App extends Application {

    public static App INSTANCE;

    private int count[] = new int[60];
    private int num = -1;
    private int price = 0;
    private int item[] = new int[60];
    private int size[] = new int[60];

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(App.this, Vkontakte.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        for(int i = 0; i < 60; i++) {
            count[i] = 1;
        }
        for(int i = 0; i < 60; i++) {
            size[i] = 0;
        }
        for(int i = 0; i < 60; i++) {
            item[i] = 0;
        }
        INSTANCE = this;

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    public void setCount(int newCount, int position) {
        count[position] = newCount;
    }

    public int getCount(int position) {
        return count[position];
    }

    public void setNum(int newNum) {
        num = newNum;
    }

    public int getNum() {
        return num;
    }

    public void setPrice(int newPrice) {
        price = newPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setSize(int newSize, int position) {
        size[position] = newSize;
    }

    public int getSize(int position) {
        return size[position];
    }

    public void setItem(int newItem, int position) {
        item[position] = newItem;
    }

    public int getItem(int position) {
        return item[position];
    }

}
