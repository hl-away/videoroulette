package com.vr;

import android.content.Context;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * User: hl-away
 * Date: 11.09.13
 * Time: 0:30
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private boolean needChangeCamera = false;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.mCamera = camera;

        this.mSurfaceHolder = this.getHolder();
        this.mSurfaceHolder.addCallback(this);
        this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            // left blank for now
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
                               int width, int height) {
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            // intentionally left blank for a test
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
    {
        new SendToServer().execute(paramArrayOfByte);
    }

    class SendToServer extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... arrayOfByte) {

            return(null);
        }
    }
}
