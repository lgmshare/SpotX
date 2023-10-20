package com.firstpoli.spotx.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.firstpoli.spotx.R;

import java.util.List;

public class DigitalHorizontalWheelView extends FrameLayout {

    public ViewPager2 viewPager2;
    public GestureDetector gestureDetector;
    public DigitalWheelAdapter digitalAdapter;

    public class DigitalGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float f4 = velocityX / 10.0f;
            if (Math.abs(f4) > 800.0f) {
                f4 = f4 > 0.0F ? 800.0f : -800.0f;
            }
            viewPager2.beginFakeDrag();
            viewPager2.fakeDragBy(f4);
            return isFakeDragging();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            viewPager2.beginFakeDrag();
            viewPager2.fakeDragBy(-distanceX);
            return true;
        }
    }

    public DigitalHorizontalWheelView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DigitalHorizontalWheelView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public DigitalHorizontalWheelView(@NonNull Context context, @Nullable AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_digital_horizontal_wheel, this, true);
        this.viewPager2 = findViewById(R.id.view_pager);
        this.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        this.digitalAdapter = new DigitalWheelAdapter();
        this.viewPager2.setAdapter(digitalAdapter);
        this.viewPager2.setOffscreenPageLimit(5);
        this.viewPager2.setUserInputEnabled(true);
        this.viewPager2.setPageTransformer((page, position) -> {
            page.setTranslationX((-(((float) viewPager2.getHeight()) * 0.25f)) * position);
            page.setScaleX(1.0f - (Math.abs(position) * 0.5f));
            page.setScaleY(1.0f - (Math.abs(position) * 0.5f));

        });
        this.gestureDetector = new GestureDetector(context, new DigitalGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean z = event.getAction() == 1;
        if (this.gestureDetector.onTouchEvent(event) || !z) {
            return true;
        }
        return isFakeDragging();
    }

    public final boolean isFakeDragging() {
        if (!this.viewPager2.isFakeDragging()) {
            return true;
        }
        this.viewPager2.endFakeDrag();
        return true;
    }

    public String getSelectedData() {
        int currentItem = this.viewPager2.getCurrentItem();
        List<String> list = this.digitalAdapter.getItems();
        if (list.size() != 0 && currentItem < list.size()) {
            return list.get(currentItem);
        }
        return null;
    }

    public void setCurrentPosition(int position) {
        this.viewPager2.setCurrentItem(position, false);
    }

    public void setWheelData(List<String> list) {
        this.digitalAdapter.addAll(list);
    }

}
