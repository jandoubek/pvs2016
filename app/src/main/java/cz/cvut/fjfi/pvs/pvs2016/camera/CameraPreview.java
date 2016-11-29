package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private Camera camera;
	private SurfaceHolder holder;
	private Activity activity;

	public CameraPreview(Activity activity, Camera camera) {
		super(activity);
		this.camera = camera;
		this.holder = getHolder();
		this.activity = activity;
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (holder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			camera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here
		holder.setFormat(format);
		holder.setFixedSize(width, height);
		// start preview with new settings
		try {
			setCameraDisplayOrientation(activity, 0, camera);
			Camera.Parameters parameters = camera.getParameters();
			Camera.Size previewSize = parameters.getSupportedPreviewSizes().get(0);
			parameters.setPreviewSize(previewSize.width, previewSize.height);
			parameters.setRotation(90);
			camera.setParameters(parameters);
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		// release Camera preview in activity
		if (camera != null) {
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	private void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		// only for back-facing camera
		int result = (info.orientation - degrees + 360) % 360;
		camera.setDisplayOrientation(result);
	}

}
