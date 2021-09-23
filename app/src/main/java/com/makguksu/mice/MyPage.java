package com.makguksu.mice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyPage extends Fragment {


    private FirebaseAuth FirebaseAuth;
    private GoogleSignInClient GoogleSignInClient;

    TextView Profile_name;
    TextView Profile_E_Mail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mypage, container, false);


        Button mypage = (androidx.appcompat.widget.AppCompatButton) v.findViewById(R.id.enter);
        Button Q_and_A = (androidx.appcompat.widget.AppCompatButton) v.findViewById(R.id.cancle);
        Button Inquiry = (androidx.appcompat.widget.AppCompatButton) v.findViewById(R.id.Inquiry);
        Button Customer_Service = (androidx.appcompat.widget.AppCompatButton) v.findViewById(R.id.Customer_Service);
        Button Announcement = (androidx.appcompat.widget.AppCompatButton) v.findViewById(R.id.Announcement);

        Profile_name = v.findViewById(R.id.profile_name);
        Profile_E_Mail = v.findViewById(R.id.profile_e_mail);

        FirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser FirebaseUser = FirebaseAuth.getCurrentUser();

        if(FirebaseUser !=null) {

            Profile_name.setText(FirebaseUser.getDisplayName());
            Profile_E_Mail.setText(FirebaseUser.getEmail());
        }

        GoogleSignInClient = GoogleSignIn.getClient(getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);



        //마이페이지 접속 버튼
        mypage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MyPage2.class);
                startActivity(intent);
            }
        });

        //Q&A 버튼
        Q_and_A.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Q_and_A.class);
                startActivity(intent);
            }
        });

        //1대1 상담 버튼
        Inquiry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Inquiry.class);
                startActivity(intent);
            }
        });

        //고객센터 버튼
        Customer_Service.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Customer_Service.class);
                startActivity(intent);
            }
        });

        //공지사항/이벤트 버튼
        Announcement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Announcement.class);
                startActivity(intent);

            }
        });


        return v;
    }
}