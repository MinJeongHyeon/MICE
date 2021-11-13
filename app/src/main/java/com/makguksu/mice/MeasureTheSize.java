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
import android.widget.TextView;

public class MeasureTheSize extends AppCompatActivity {

    TextView vertical;
    TextView horizon;
    double x = 0;
    double y = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_the_size);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        x = getIntent().getDoubleExtra("X", 0);
        y = getIntent().getDoubleExtra("Y", 0);
        x = Math.round(x);
        y = Math.round(y);

        vertical = findViewById(R.id.verticalLength);
        horizon = findViewById(R.id.horizontalLength);
        vertical.setText(Double.toString(y));
        horizon.setText(Double.toString(x));



        Button goToGrip = (Button)findViewById(R.id.goToGrip);
        goToGrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), Survey_grip.class);
                intent.putExtra("horizon", x);
                intent.putExtra("vertical", y);

                startActivity(intent);
            }
        });

        Button cancel = (Button)findViewById(R.id.cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MeasureTheSize.this);
                ad.setTitle("취소");
                ad.setMessage("홈으로 돌아가시겠습니까?");

                final EditText et = new EditText(MeasureTheSize.this);
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
    }
}