package com.makguksu.mice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecommendationPage extends Fragment {

    ViewGroup viewGroup;
    private com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient GoogleSignInClient;
    TextView Profile_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.recommendationpage,container,false);
        Button recom_button = (Button) viewGroup.findViewById(R.id.recom_button);
        Profile_name = viewGroup.findViewById(R.id.recom_textView3);

        FirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser FirebaseUser = FirebaseAuth.getCurrentUser();
        if(FirebaseUser !=null) {

            Profile_name.setText(FirebaseUser.getDisplayName());
        }
        GoogleSignInClient = GoogleSignIn.getClient(getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);

        recom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Survey_input.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    };
}
