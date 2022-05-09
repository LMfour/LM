package com.lm.stopsleeping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class OptionFragment extends Fragment {

    private View view;
    private TextView btn_alm, btn_cnt, btn_logout, btn_sucession;
    private TextView nickName, email;

    public OptionFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_option,container,false);
        btn_alm = view.findViewById(R.id.opt_chgalm_txt);
        btn_cnt = view.findViewById(R.id.opt_chgcnt_txt);
        btn_logout = view.findViewById(R.id.opt_logout_txt);
        btn_sucession = view.findViewById(R.id.opt_sucession_txt);

        nickName = view.findViewById(R.id.menu_nick_txt);
        email = view.findViewById(R.id.menu_email_txt);



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

                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Intent intent =  new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                return null;
                            }
                        });

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

        // 설정의 회원탈퇴 클릭시 페이지 이동
        btn_sucession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("안내");
                builder.setMessage("회원탈퇴하시겠습니까?");

                // 로그아웃 팝업 '예' 클릭시 로그인 페이지로 이동
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Intent intent =  new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                return null;
                            }
                        });
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

    // 로그인이 되어있는지 안되어있는지 확인 후 개인정보 보여줌
    private void updateInfoKakao() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    email.setText(user.getKakaoAccount().getEmail());
                } else {

                }

                return null;
            }
        });

    }


}
