package com.makguksu.mice;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Camera extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    static {
        System.loadLibrary("native-lib");
    }

    private int m_Camidx = 0;//front : 1, back : 0
    private CameraBridgeViewBase m_CameraView;

    private Mat matInput;
    private Mat matOutput;

    private static final int CAMERA_PERMISSION_CODE = 200;
    private static final String TAG = "opencv";

    public native void ImageProcessing(long matAddrInput, long matAddrOutput);
    public native void ImageCropping(long matAddrInput);
    public native void ImageExpansion(long matAddrInput);

    private final Semaphore writeLock = new Semaphore(1);

    public void getWriteLock() throws InterruptedException {
        writeLock.acquire();
    }

    public void releaseWriteLock() {
        writeLock.release();
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        m_CameraView = (CameraBridgeViewBase)findViewById(R.id.activity_surface_view);
        m_CameraView.setVisibility(SurfaceView.VISIBLE);
        m_CameraView.setCvCameraViewListener(this);
        m_CameraView.setCameraIndex(m_Camidx);

        Button capture = (Button)findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getWriteLock();
                    File path = new File("/storage/emulated/0/DCIM/MICE/");
                    path.mkdirs();
                    String imageName = "handImage.png";
                    File file = new File(path, imageName);
                    String filename = file.toString();
                    ImageCropping(matInput.getNativeObjAddr());
                    Imgproc.cvtColor(matInput, matInput, Imgproc.COLOR_BGR2RGB, 4);
                    ImageExpansion(matInput.getNativeObjAddr());
                    boolean ret  = Imgcodecs.imwrite( filename, matInput);
                    if ( ret ) Log.d(TAG, "SUCCESS");
                    else Log.d(TAG, "FAIL");
                    Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(Uri.fromFile(file));
                    sendBroadcast(mediaScanIntent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                releaseWriteLock();
                Intent intent = new Intent(getApplication().getApplicationContext(), Camera2.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean _Permission = true;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
                _Permission = false;
            }
        }
        if(_Permission){
            onCameraPermissionGranted();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "onResum :: OpenCV library found inside package. Using it!");
            m_LoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }



    @Override
    public void onPause()
    {
        super.onPause();
        if (m_CameraView != null)
            m_CameraView.disableView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (m_CameraView != null)
            m_CameraView.disableView();
    }

    private BaseLoaderCallback m_LoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    m_CameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    protected void onCameraPermissionGranted() {
        List<? extends CameraBridgeViewBase> cameraViews = getCameraViewList();
        if (cameraViews == null) {
            return;
        }
        for (CameraBridgeViewBase cameraBridgeViewBase: cameraViews) {
            if (cameraBridgeViewBase != null) {
                cameraBridgeViewBase.setCameraPermissionGranted();

            }
        }
    }

    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(m_CameraView);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        try {
            getWriteLock();
            matInput = inputFrame.rgba();
            matOutput = inputFrame.rgba();

            ImageProcessing(matInput.getNativeObjAddr(), matOutput.getNativeObjAddr());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        releaseWriteLock();

        return matOutput;
    }
}
