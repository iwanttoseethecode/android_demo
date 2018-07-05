package com.example.luoling.android_dome.MyWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.luoling.android_dome.R;

public class MyWindowActivity extends AppCompatActivity {

    private FrameLayout frame;
    private WindowManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_window);
        addWindow();
    }

    public void addWindow(){
        startWindow();
    }

    private void startWindow() {
        wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        frame = new PopupDecorView(this);
        frame.setBackgroundColor(Color.GREEN);
        frame.setLayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Button bt = new Button(this);
        bt.setLayoutParams(new FrameLayout.LayoutParams(200, 200));
        bt.setText("holy shit");
        bt.setGravity(Gravity.CENTER);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });
        //可以新建一个 WindowManager.LayoutParams lp  = new WindowManager.LayoutParams();
//        WindowManager.LayoutParams p = createPopupLayoutParams(lp);
        WindowManager.LayoutParams p = createPopupLayoutParams(frame.getWindowToken());
        frame.addView(bt);
        wm.addView(frame, p);
//		this.getWindow().getDecorView().setBackgroundDrawable(null);
    }

    private WindowManager.LayoutParams createPopupLayoutParams(IBinder windowToken) {
        final WindowManager.LayoutParams p = new WindowManager.LayoutParams();

        // These gravity settings put the view at the top left corner of the
        // screen. The view is then positioned to the appropriate location by
        // setting the x and y offsets to match the anchor's bottom-left
        // corner.
        p.gravity = Gravity.CENTER;
//	     p.flags = computeFlags(p.flags);
        p.type =  WindowManager.LayoutParams.TYPE_BASE_APPLICATION;
        p.token = windowToken;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
        p.windowAnimations = 0;
        p.format = PixelFormat.TRANSLUCENT;


        // Used for debugging.
        p.setTitle("PopupWindow:" + Integer.toHexString(hashCode()));
        p.width = 700;
        p.height = 1000;
        return p;
    }

    private int computeFlags(int curFlags) {
        curFlags &= ~(
                WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM |
                        WindowManager.LayoutParams.FLAG_SPLIT_TOUCH);
        curFlags |= WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES;
        return curFlags;
    }



    private void dismiss() {
        wm.removeView(frame);

    }


    private class PopupDecorView extends FrameLayout {

        public PopupDecorView(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(event);
                }

                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null) {
                        state.startTracking(event, this);
                    }
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    final KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null && state.isTracking(event) && !event.isCanceled()) {
                        dismiss();
                        return true;
                    }
                }
                return super.dispatchKeyEvent(event);
            } else {
                return super.dispatchKeyEvent(event);
            }
        }


        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
//	            if (mTouchInterceptor != null && mTouchInterceptor.onTouch(this, ev)) {
//	                return true;
//	            }
            return super.dispatchTouchEvent(ev);
        }
    }


}
