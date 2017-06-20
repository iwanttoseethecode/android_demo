package com.example.luoling.android_dome.chapter5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShadowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SparseArray<View> viewSparseArray;
    @BindView(R.id.selectSpinner)
    Spinner selectSpinner;
    @BindView(R.id.shaderView)
    ShaderView shaderView;
    @BindView(R.id.linearGradientView)
    LinearGradientView linearGradientView;
    @BindView(R.id.radialGradientView)
    RadialGradientView radialGradientView;
    @BindView(R.id.fiveChessView)
    FiveChessView fiveChessView;
    @BindView(R.id.sweepGradientView)
    SweepGradientView sweepGradientView;
    @BindView(R.id.bitmapShaderView)
    BitmapShaderView bitmapShaderView;
    @BindView(R.id.composeShaderView)
    ComposeShaderView composeShaderView;
    @BindView(R.id.gradientAndMatrixView)
    GradientAndMatrixView gradientAndMatrixView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        selectSpinner.setOnItemSelectedListener(this);

        viewSparseArray = new SparseArray<View>();
        viewSparseArray.put(1, shaderView);
        viewSparseArray.put(2, linearGradientView);
        viewSparseArray.put(3, radialGradientView);
        viewSparseArray.put(4, fiveChessView);
        viewSparseArray.put(5, sweepGradientView);
        viewSparseArray.put(6, bitmapShaderView);
        viewSparseArray.put(7, composeShaderView);
        viewSparseArray.put(8, gradientAndMatrixView);
    }

    public void setVisibility(int n) {
        for (int i = 1; i <= viewSparseArray.size(); i++) {
            if (i == n) {
                viewSparseArray.get(i).setVisibility(View.VISIBLE);
                continue;
            }
            viewSparseArray.get(i).setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        if (name.equals("阴影显示")) {
            setVisibility(1);
        } else if (name.equals("线性渐变")) {
            setVisibility(2);
        } else if (name.equals("径向渐变")) {
            setVisibility(3);
        } else if (name.equals("五子棋盘")) {
            setVisibility(4);
        } else if (name.equals("扫描渐变")) {
            setVisibility(5);
        } else if (name.equals("位图渐变")) {
            setVisibility(6);
        } else if (name.equals("混合渐变")) {
            setVisibility(7);
        } else if (name.equals("渐变和矩阵")){
            setVisibility(8);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
