package com.makguksu.mice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Survey_grip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_grip);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button goToSurvey = (Button)findViewById(R.id.goToSurvey);
        goToSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), Survey.class);
                startActivity(intent);
            }
        });
    }
}