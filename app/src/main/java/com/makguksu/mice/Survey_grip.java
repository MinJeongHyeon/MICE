package com.makguksu.mice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Survey_grip extends AppCompatActivity {
    String grip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_grip);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = new Intent(getApplication().getApplicationContext(), Survey_grip.class);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGrip);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio_btn = (RadioButton) findViewById(checkedId);
				switch (checkedId) {
                    case R.id.radioGrip1:
                        grip = "Palm";
					    break;
                    case R.id.radioGrip2:
                        grip = "Claw";
                        break;
                    case R.id.radioGrip3:
                        grip = "Finger";
                        break;
                    case R.id.radioGrip4:
                        grip = "dk";
                        break;
				}
            }
        });


        Button goToSurvey = (Button)findViewById(R.id.goToSurvey);
        goToSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), RecommendResult.class);
                intent.putExtra("horizon", getIntent().getDoubleExtra("horizon", 0));
                intent.putExtra("vertical", getIntent().getDoubleExtra("vertical", 0));
                intent.putExtra("grip", grip);
                startActivity(intent);
            }
        });
    }
}