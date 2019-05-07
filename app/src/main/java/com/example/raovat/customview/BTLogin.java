package com.example.raovat.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class BTLogin extends Button {

    public BTLogin(Context context) {
        super(context);
    }

    public BTLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                        performClick();
                        setAlpha(1f);
                        break;
                }
                return true;
            }
        });
    }

    public BTLogin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public BTLogin(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
