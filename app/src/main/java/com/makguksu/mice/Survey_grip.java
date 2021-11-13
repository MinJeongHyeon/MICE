package com.makguksu.mice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        Button cancel = (Button)findViewById(R.id.cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Survey_grip.this);
                ad.setTitle("취소");
                ad.setMessage("홈으로 돌아가시겠습니까?");

                final EditText et = new EditText(Survey_grip.this);
                ad.setView(et);

                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                ad.show();


            }
        });

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