package com.makguksu.mice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MeasureTheSize extends AppCompatActivity {

    TextView vertical;
    TextView horizon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_the_size);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        double x = getIntent().getDoubleExtra("X", 0);
        double y = getIntent().getDoubleExtra("Y", 0);
        vertical = findViewById(R.id.verticalLength);
        horizon = findViewById(R.id.horizontalLength);

        vertical.setText(Double.toString(y));
        horizon.setText(Double.toString(x));



        Button goToGrip = (Button)findViewById(R.id.goToGrip);
        goToGrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), Survey_grip.class);
                startActivity(intent);
            }
        });
    }
}