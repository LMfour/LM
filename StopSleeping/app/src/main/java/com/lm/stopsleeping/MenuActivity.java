package com.lm.stopsleeping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView menu_btn, option_btn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_btn = findViewById(R.id.menu_my_btn);
        option_btn = findViewById(R.id.menu_opt_btn);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MypageFragment myFragment = new MypageFragment();
                transaction.replace(R.id.frame, myFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        option_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                OptionFragment optFragment = new OptionFragment();
                transaction.replace(R.id.frame, optFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });





    }
}