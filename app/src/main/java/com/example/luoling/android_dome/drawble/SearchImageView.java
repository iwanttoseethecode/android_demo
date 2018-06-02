package com.example.luoling.android_dome.drawble;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SearchImageView extends android.support.v7.widget.AppCompatImageView {

    private SearchDrawable searchDrawable;

    public SearchImageView(Context context) {
        super(context);
    }

    public SearchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setImageDrawable(@Nullable SearchDrawable drawable) {
        searchDrawable = drawable;
        super.setImageDrawable(drawable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int state = searchDrawable.getmState();
                if (state == SearchDrawable.STATE_ANIM_NONE){
                    searchDrawable.startAnimation();
                }else if(state == SearchDrawable.STATE_ANIM_START){
                    searchDrawable.stopAnimation();
                }else if (state == SearchDrawable.STATE_ANIM_STOP){
                    searchDrawable.resetAnimation();
                }
                break;
        }
        return true;
    }
}
