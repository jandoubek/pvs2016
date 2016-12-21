package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.File;
import java.util.ArrayList;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class CameraActivity extends Activity {

	private static final int CAMERA_PERMISSION_REQUEST_CODE = 5;
	private final CameraActivity self = this;

	private Camera camera;
	private CameraPreview cameraPreview;
	private ArrayList<String> sessionPicturePaths;

	private PictureCallback pictureCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d("CameraActivity", "JPEG picture created.");
			File pictureFile = FileUtils.getOutputMediaFile(FileUtils.MEDIA_TYPE_IMAGE);
			if (pictureFile == null) {
				return;
			}
			Intent previewIntent = new Intent(self, PreviewActivity.class);
			sessionPicturePaths.add(pictureFile.getPath());
			previewIntent.putStringArrayListExtra(IApplicationConstants.PICTURES_PATHS_INTENT_EXTRA, sessionPicturePaths);
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
		if (getIntent().getExtras() != null) {
			sessionPicturePaths = getIntent().getExtras().getStringArrayList(IApplicationConstants.PICTURES_PATHS_INTENT_EXTRA);
		}
		if (sessionPicturePaths == null) {
			sessionPicturePaths = new ArrayList<>();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_REQUEST_CODE);
			return;
		}
		initializeCameraPreview();
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

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			initializeCameraPreview();
		} else {
			Toast.makeText(this, "Without permission for using camera is this application useless for you!", Toast.LENGTH_LONG).show();
		}
	}

	private void initializeCameraPreview() {
		camera = getCameraInstance();
		cameraPreview = new CameraPreview(this, camera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
		preview.addView(cameraPreview);
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
