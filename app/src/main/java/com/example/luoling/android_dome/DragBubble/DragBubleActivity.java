package com.example.luoling.android_dome.DragBubble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.luoling.android_dome.R;

public class DragBubleActivity extends AppCompatActivity {

    private DragBubbleView dragBubbleView;
    private Button resetbtn;
    private LinearLayout myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_buble);
        initView();
        dragBubbleView.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                dragBubbleView.getLocationInWindow(location);
                dragBubbleView.setWindowX(location[0]);
                dragBubbleView.setWindowY(location[1]);
            }
        });
    }

    private void initView() {
        dragBubbleView = (DragBubbleView) findViewById(R.id.dragBubbleView);
        resetbtn = (Button) findViewById(R.id.resetbtn);
        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragBubbleView.reset();
            }
        });
        dragBubbleView.setDragBubbleBindParentListenter(new DragBubbleView.DragBubbleBindParentListenter() {

            @Override
            public void changeParent() {
                myLinearLayout.removeView(dragBubbleView);
                if (dragBubbleView.getParent() == null){
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//                    layoutParams.leftMargin = dragBubbleView.getWindowX();
//                    layoutParams.topMargin = dragBubbleView.getWindowY();
                    ((FrameLayout) getWindow().getDecorView()).addView(dragBubbleView,layoutParams);
                }
            }

            @Override
            public void resoreParent() {
                ((FrameLayout) getWindow().getDecorView()).removeView(dragBubbleView);
                if (dragBubbleView.getParent() == null){
                    myLinearLayout.addView(dragBubbleView);
                }
            }

            @Override
            public void removeParent() {
                ((FrameLayout) getWindow().getDecorView()).removeView(dragBubbleView);
                myLinearLayout.removeView(dragBubbleView);
            }
        });
    }


}
