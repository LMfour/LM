package com.lm.stopsleeping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MypageFragment extends Fragment {

    private View view;
    private TextView btn_info, btn_chk;

    public MypageFragment() {



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage,container,false);
        btn_chk = view.findViewById(R.id.myp_chk_txt);
        btn_info = view.findViewById(R.id.myp_info_txt);


        // 마이페이지의 졸음운전 날짜 확인 클릭시 페이지 이동
        btn_chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), CheckActivity.class);
                startActivity(intent);
            }
        });

        // 마이페이지의 개인정보 수정 클릭시 페이지 이동
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ModifyInfoActivity.class);
                startActivity(intent);
            }
        });

        return view;

        //return inflater.inflate(R.layout.fragment_mypage, container, false);
    }
}
