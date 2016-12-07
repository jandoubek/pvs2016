package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private Camera camera;
	private SurfaceHolder holder;
	private Activity activity;
	private Camera.Size previewSize;
	private List<Camera.Size> supportedPreviewSizes;

	public CameraPreview(Activity activity, Camera camera) {
		super(activity);
		this.camera = camera;
		this.holder = getHolder();
		this.activity = activity;
		this.supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		setMeasuredDimension(width, height);
		if (camera != null) {
			previewSize = getOptimalPreviewSize(supportedPreviewSizes, width, height);
		}
	}

	private void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(previewSize.width, previewSize.height);

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
		default:
			break;
		}
		// only for back-facing camera
		int result = (info.orientation - degrees + 360) % 360;
		// set proper orientation to camera
		parameters.setRotation(result);
		camera.setParameters(parameters);
		camera.setDisplayOrientation(result);
	}

	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) h / w;

		if (sizes == null) {
			return null;
		}

		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;
		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
				continue;
			}
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

}
