package com.example.luoling.android_dome.CustomDrawerLayout.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

public class MyDrawerFrameLayout extends FrameLayout {

    private MyDrawerSliderMenu myDrawerSliderMenu;
    private MyDrawerBgView myDrawerBgView;

    public MyDrawerFrameLayout(@NonNull Context context,MyDrawerSliderMenu myDrawerSliderMenu) {
        super(context);
        init(myDrawerSliderMenu);
    }

    private void init(MyDrawerSliderMenu myDrawerSliderMenu) {
        this.myDrawerSliderMenu = myDrawerSliderMenu;
        //把当前容器的宽高设置成和myDrawerSliderMenu一样
        setLayoutParams(myDrawerSliderMenu.getLayoutParams());

        myDrawerBgView = new MyDrawerBgView(getContext());
        addView(myDrawerBgView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        myDrawerBgView.setColor(myDrawerSliderMenu.getBackground());
        myDrawerSliderMenu.setBackgroundColor(Color.TRANSPARENT);
        addView(myDrawerSliderMenu,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }

    public void setTouchY(float y,float slideoffset){
        myDrawerBgView.setTouchY(y,slideoffset);
        myDrawerSliderMenu.setTouchY(y,slideoffset);
    }

    public void onMoveUp() {
        myDrawerSliderMenu.onMotionUp();
        myDrawerBgView.onMoveUp();
    }
}
