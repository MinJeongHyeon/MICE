package com.makguksu.mice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class Camera2 extends AppCompatActivity implements View.OnTouchListener {

    private ImageView handImage;
    ImageView drag1;
    ImageView drag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        handImage = (ImageView) findViewById(R.id.handImage);

        drag1 = (ImageView)findViewById(R.id.drag1);
        drag1.setOnTouchListener(this);
        drag2 = (ImageView)findViewById(R.id.drag2);
        drag2.setOnTouchListener(this);

        try {
            String path = getExternalFilesDir(null)+"/handImage.png";
            handImage.setImageURI(Uri.parse(path));
        }catch (Exception e){
            e.printStackTrace();
        }

        Button recapture = (Button)findViewById(R.id.recapture);
        recapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Camera2.this);
                ad.setTitle("재촬영");
                ad.setMessage("재촬영 하시겠습니까? (현재 촬영한 이미지는 삭제됩니다.)");

                final EditText et = new EditText(Camera2.this);
                ad.setView(et);

                ad.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication().getApplicationContext(), Camera.class);
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

        Button cancel = (Button)findViewById(R.id.cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Camera2.this);
                ad.setTitle("취소");
                ad.setMessage("홈으로 돌아가시겠습니까?");

                final EditText et = new EditText(Camera2.this);
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

        Button goToMeasure = (Button)findViewById(R.id.goToMeasure);
        goToMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication().getApplicationContext(), MeasureTheSize.class);

                float x = drag1.getX()-drag2.getX();
                if (x<0) x *= -1;
                float y= drag1.getY()-drag2.getY();
                if (y<0) y *= -1;
                double cardLength = 0;
                if (x>=y) cardLength =Math.sqrt((x*x)-(y*y));
                if (y>x) cardLength =Math.sqrt((x*x)-(y*y));
                double mmPerValue = 54/cardLength; // 1 좌표값 당 mm

                double handHorizon = handImage.getWidth()* mmPerValue;
                double handVertical = handImage.getHeight()* mmPerValue;

                intent.putExtra("X", handHorizon);
                intent.putExtra("Y", handVertical);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){

        int parentWidth = ((ViewGroup)v.getParent()).getWidth();    // 부모 View 의 Width
        int parentHeight = ((ViewGroup)v.getParent()).getHeight();    // 부모 View 의 Height

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            // 뷰 누름
            float oldXvalue = event.getX();
            float oldYvalue = event.getY();
            Log.d("viewTest", "oldXvalue : "+ oldXvalue + " oldYvalue : " + oldYvalue);    // View 내부에서 터치한 지점의 상대 좌표값.
            Log.d("viewTest", "v.getX() : "+v.getX());    // View 의 좌측 상단이 되는 지점의 절대 좌표값.
            Log.d("viewTest", "RawX : " + event.getRawX() +" RawY : " + event.getRawY());    // View 를 터치한 지점의 절대 좌표값.
            Log.d("viewTest", "v.getHeight : " + v.getHeight() + " v.getWidth : " + v.getWidth());    // View 의 Width, Height

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            // 뷰 이동 중
            v.setX(v.getX() + (event.getX()) - (v.getWidth()/2));
            v.setY(v.getY() + (event.getY()) - (v.getHeight()/2));

        }else if(event.getAction() == MotionEvent.ACTION_UP){
            // 뷰에서 손을 뗌

            if(v.getX() < 0){
                v.setX(0);
            }else if((v.getX() + v.getWidth()) > parentWidth){
                v.setX(parentWidth - v.getWidth());
            }

            if(v.getY() < 0){
                v.setY(0);
            }else if((v.getY() + v.getHeight()) > parentHeight){
                v.setY(parentHeight - v.getHeight());
            }

        }
        return true;
    }


}