package com.vr;

import android.app.Activity;
import android.os.Bundle;

import android.hardware.Camera;
import android.widget.LinearLayout;

public class VRActivity extends Activity {
    private Camera mCamera;
    private Camera mCamera2;
    private CameraPreview mUserCameraPreview;
    private CameraPreview mPartnerCameraPreview;
    private int width;
    private int height;

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
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            if(mPartnerCameraPreview == null) {
                mPartnerCameraPreview = new CameraPreview(this, mCamera);
                mPartnerCameraPreview.getHolder().setFixedSize(width/2, height);
            }
        }
        /*if(mCamera2 == null) {
            mCamera2 = Camera.open();
            if(mUserCameraPreview == null) {
                mUserCameraPreview = new CameraPreview(this, mCamera2);
                mUserCameraPreview.getHolder().setFixedSize(width/2, height);
            }
        }*/
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (mCamera != null)
        {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
