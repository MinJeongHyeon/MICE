package com.makguksu.mice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MeasureTheSize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_the_size);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
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