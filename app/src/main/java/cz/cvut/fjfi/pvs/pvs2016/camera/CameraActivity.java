package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class CameraActivity extends Activity {

	private final CameraActivity self = this;

	private Camera camera;
	private CameraPreview cameraPreview;

	private PictureCallback pictureCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d("CameraActivity", "JPEG picture created.");
			File pictureFile = FileUtils.getOutputMediaFile(FileUtils.MEDIA_TYPE_IMAGE);
			if (pictureFile == null) {
				return;
			}
			Intent previewIntent = new Intent(self, PreviewActivity.class);
			previewIntent.putExtra("picturePath", pictureFile.getPath());
			self.startActivity(previewIntent);
			FileUtils.writeToFile(pictureFile, data);
			camera.startPreview();
			self.finish();
		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
	}

	@Override
	protected void onResume() {
		super.onResume();
		camera = getCameraInstance();
		cameraPreview = new CameraPreview(this, camera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
		preview.addView(cameraPreview);
		initializeCaptureButton();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (camera != null) {
			camera.stopPreview();
			camera.setPreviewCallback(null);
			cameraPreview.getHolder().removeCallback(cameraPreview);
			camera.release();
			camera = null;
		}
	}

	private void initializeCaptureButton() {
		FloatingActionButton captureButton = (FloatingActionButton) findViewById(R.id.buttonCapture);
		captureButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						camera.takePicture(null, null, pictureCallback);
					}
				}
		);
	}

	private Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
			// get Camera parameters
			Camera.Parameters params = c.getParameters();
			// set the focus mode
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			// set Camera parameters
			c.setParameters(params);
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
			// show dialog
		}
		return c; // returns null if camera is unavailable
	}

}
