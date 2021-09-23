package com.makguksu.mice;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPage2 extends AppCompatActivity {

    ImageView Profile_Image;
    TextView Profile_name;
    TextView Profile_E_Mail;
    Button LogOut;
    FirebaseAuth FirebaseAuth;
    GoogleSignInClient GoogleSignInClient;

    MainActivity MA = (MainActivity) MainActivity.activity;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage2);



        //액션바 관리
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        getSupportActionBar().setTitle("마이페이지");

        Profile_Image = findViewById(R.id.Profile_Image);
        Profile_name = findViewById(R.id.Profile_Name);
        Profile_E_Mail = findViewById(R.id.Profile_E_Mail);
        LogOut = findViewById(R.id.Logout);

        FirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser FirebaseUser = FirebaseAuth.getCurrentUser();

        if(FirebaseUser !=null) {
            Glide.with(MyPage2.this)
                    .load(FirebaseUser.getPhotoUrl())
                    .into(Profile_Image);

            Profile_name.setText(FirebaseUser.getDisplayName());
            Profile_E_Mail.setText(FirebaseUser.getEmail());
        }

        GoogleSignInClient = GoogleSignIn.getClient(MyPage2.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            FirebaseAuth.signOut();

                            Intent intent = new Intent(getApplication().getApplicationContext(), StartLogin.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                            MA.finish();
                            finish();
                        }
                    }
                });
            }
        });
    }
}

