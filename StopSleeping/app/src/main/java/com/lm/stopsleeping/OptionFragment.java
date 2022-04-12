package com.lm.stopsleeping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OptionFragment extends Fragment {

    private View view;
    private TextView btn_alm, btn_cnt, btn_logout;

    public OptionFragment() {



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_option,container,false);
        btn_alm = view.findViewById(R.id.opt_chgalm_txt);
        btn_cnt = view.findViewById(R.id.opt_chgcnt_txt);
        btn_logout = view.findViewById(R.id.opt_logout_txt);


        // 설정의 알람변경 클릭시 페이지 이동
        btn_alm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), ChangeAlarmActivity.class);
                startActivity(intent);
            }
        });

        // 설정의 횟수변경 클릭시 페이지 이동
        btn_cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), ChangeCountActivity.class);
                startActivity(intent);
            }
        });

        // 설정의 로그아웃 클릭시 페이지 이동
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("안내");
                builder.setMessage("로그아웃하시겠습니까?");

                // 로그아웃 팝업 '예' 클릭시 로그인 페이지로 이동
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =  new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });

                // 로그아웃 팝업 '아니오' 클릭시 팝업창 내려감
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

        return view;
    }


}
