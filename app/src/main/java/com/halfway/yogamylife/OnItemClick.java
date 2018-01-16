package com.halfway.yogamylife;

public interface OnItemClick {
    void onClickPlus (int position, int price);
    void onClickMinus (int position, int price, int count);
    void onDeleteClick(int position, int price);
}

