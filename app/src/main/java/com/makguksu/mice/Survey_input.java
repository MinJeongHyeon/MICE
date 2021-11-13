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

public class Survey_input extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_input);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button camera = (Button)findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), Camera.class);
                startActivity(intent);
            }
        });
        Button cancel = (Button)findViewById(R.id.cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Survey_input.this);
                ad.setTitle("취소");
                ad.setMessage("홈으로 돌아가시겠습니까?");

                final EditText et = new EditText(Survey_input.this);
                ad.setView(et);

                ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ad.show();


            }
        });
    }
}