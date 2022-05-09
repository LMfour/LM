package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class ModifyInfoActivity extends AppCompatActivity {

    ImageButton btn_back;
    private TextView nickName, email, sex, age, birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        nickName = findViewById(R.id.info_name_txt);
        email = findViewById(R.id.info_id_txt);
        sex = findViewById(R.id.info_sex_txt);
        age = findViewById(R.id.info_age_txt);
        birth = findViewById(R.id.info_birth_txt);

        btn_back = findViewById(R.id.men_back_btn);

        // 뒤로가기 버튼 클릭시 메뉴 페이지로 이동
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ModifyInfoActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        updateKakaoLogin();
    }

    // 로그인이 되어있는지 안되어있는지 확인 후 button 처리
    private void updateKakaoLogin() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    email.setText(user.getKakaoAccount().getEmail());

                    if(user.getKakaoAccount().getGender().toString() == "MALE"){
                        sex.setText("남");
                    } else if(user.getKakaoAccount().getAgeRange().toString() == "FEMALE"){
                        sex.setText("여");
                    }
                    age.setText(user.getKakaoAccount().getAgeRange().toString().substring(4,6)+"대");
                    birth.setText(user.getKakaoAccount().getBirthday().substring(0,2)+"월 "+user.getKakaoAccount().getBirthday().substring(2,4)+"일");

                } else {
                    nickName.setText(null);
                    email.setText(null);
                    birth.setText(null);
                }

                return null;
            }
        });

    }
}