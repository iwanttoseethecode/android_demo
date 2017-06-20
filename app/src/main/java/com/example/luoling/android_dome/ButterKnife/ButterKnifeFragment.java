package com.example.luoling.android_dome.ButterKnife;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luoling.android_dome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ButterKnifeFragment extends Fragment {


    @BindView(R.id.dj_back)
    LinearLayout djBack;
    @BindView(R.id.match_text)
    TextView matchText;
    @BindView(R.id.match_ws)
    LinearLayout matchWs;
    @BindView(R.id.import_hp)
    LinearLayout importHp;
    @BindView(R.id.import_dw)
    LinearLayout importDw;
    @BindView(R.id.export_hp)
    LinearLayout exportHp;
    @BindView(R.id.export_dj)
    LinearLayout exportDj;
    @BindView(R.id.export_dw)
    LinearLayout exportDw;
    @BindView(R.id.export_pref)
    LinearLayout exportPref;
    @BindView(R.id.gx_data)
    LinearLayout gxData;
    @BindView(R.id.pwdlayout)
    LinearLayout pwdlayout;
    @BindView(R.id.parameter)
    LinearLayout parameter;
    @BindView(R.id.ck)
    LinearLayout ck;

    public ButterKnifeFragment() {
        // Required empty public constructor
    }

    public static ButterKnifeFragment newInstance() {
        ButterKnifeFragment fragment = new ButterKnifeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_butter_knife, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @OnClick(R.id.dj_back)
    public void onClick() {

    }

    @OnClick({R.id.gx_data, R.id.pwdlayout, R.id.parameter, R.id.ck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gx_data:
                Toast.makeText(getContext(), "11111111111111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pwdlayout:
                Toast.makeText(getActivity(), "222222222222222", Toast.LENGTH_SHORT).show();
                break;
            case R.id.parameter:
                Toast.makeText(getActivity(), "333333333333333", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ck:
                Toast.makeText(getActivity(), "44444444444444", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
