package com.example.mycuteapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewCheckBox extends androidx.appcompat.widget.AppCompatCheckBox {
    boolean toDelete = false;

    public NewCheckBox(@NonNull Context context) {
        super(context);

        this.setTextColor(MainActivity.maincolor);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        MainActivity.maincolor,
                        MainActivity.maincolor
                });

        this.setButtonTintList(colorStateList);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setTextSize(MainActivity.textSize);
        this.setTextColor(MainActivity.maincolor);
    }

    public NewCheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NewCheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
