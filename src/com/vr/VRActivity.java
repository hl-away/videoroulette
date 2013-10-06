package com.vr;

import android.app.Activity;
import android.os.Bundle;

import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class VRActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mUserCameraPreview;
    private CameraPreview mPartnerCameraPreview;
    private int width;
    private int height;
    private int currentCameraID = 0;

    private void initCamera() {
        if(mCamera == null || currentCameraID == Camera.CameraInfo.CAMERA_FACING_BACK) {
            if(mCamera != null) {
                //mCamera.stopPreview();
                mCamera.release();
            }
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            currentCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            //mCamera.stopPreview();
            mCamera.release();
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            currentCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        mUserCameraPreview = new CameraPreview(this, mCamera);
        Camera.Size size = mCamera.getParameters().getPictureSize();
        int cameraWidth = size.width;
        int cameraHeight = size.height;
        mUserCameraPreview.getHolder().setFixedSize(cameraWidth, cameraHeight);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();
        onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.GeneralLayout);
        if(mPartnerCameraPreview != null) {
            layout.addView(mPartnerCameraPreview);
        }
        if(mUserCameraPreview != null) {
            layout.addView(mUserCameraPreview);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(mCamera == null) {
            initCamera();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initCamera();
        return false;
    }
}
