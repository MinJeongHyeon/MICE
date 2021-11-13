package com.makguksu.mice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class RecommendResult extends AppCompatActivity {

    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommended_mouse);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button cancel = (Button)findViewById(R.id.cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(RecommendResult.this);
                ad.setTitle("취소");
                ad.setMessage("홈으로 돌아가시겠습니까?");

                final EditText et = new EditText(RecommendResult.this);
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

        Button goToFirst = (Button)findViewById(R.id.goToFirst);
        goToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), Survey_input.class);
                startActivity(intent);
            }
        });

        double horizon = getIntent().getDoubleExtra("horizon", 0);
        double vertical = getIntent().getDoubleExtra("vertical", 0);
        String grip = getIntent().getStringExtra("grip");

        dataList = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference mouseRef = db.collection("mouse");
        mouseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> tempMap = document.getData();
                        tempMap.put("point", "0");
                        dataList.add(tempMap);
                    }

                    for (Map<String, Object> listMap : dataList){
                        if(grip.equals("Palm")) {
                            String heightSt = listMap.get("height").toString();
                            heightSt = heightSt.replace("mm", "");
                            double height = Double.parseDouble(heightSt);
                            String weightSt = listMap.get("weight").toString();
                            if (weightSt.equals("")) weightSt = "0g";
                            weightSt = weightSt.replace("g", "");
                            double weight = Double.parseDouble(weightSt);

                            if (height >= 40 && weight >= 85) {
                                int point = Integer.parseInt(listMap.get("point").toString());
                                point += 100;
                                listMap.put("point", point);
                            }
                        }/*
                        if(grip.equals("Claw")) {
                            String heightSt = document.getData().get("height").toString();
                            heightSt = heightSt.replace("mm", "");
                            double height = Double.parseDouble(heightSt);
                            String weightSt = document.getData().get("weight").toString();
                            if (weightSt.equals("")) weightSt = "0g";
                            weightSt = weightSt.replace("g", "");
                            double weight = Double.parseDouble(weightSt);
                            if (height <= 40 && height >= 30 && weight <= 104 && weight >= 80) {
                                dataList.add(document.getData());
                            }
                        }
                        if(grip.equals("Finger")) {
                            String heightSt = document.getData().get("height").toString();
                            heightSt = heightSt.replace("mm", "");
                            double height = Double.parseDouble(heightSt);
                            String weightSt = document.getData().get("weight").toString();
                            if (weightSt.equals("")) weightSt = "0g";
                            weightSt = weightSt.replace("g", "");
                            double weight = Double.parseDouble(weightSt);
                            if (height <= 40 && weight <= 95 && weight != 0) {
                                dataList.add(document.getData());
                            }
                        }*/
                    }
                    RecyclerViewCreate();
                }
            }
        });
    }
    private void RecyclerViewCreate() {
        recyclerView = findViewById(R.id.mouse_list);
        recyclerAdapter = new RecyclerAdapter(RecommendResult.this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }


}