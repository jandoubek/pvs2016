package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.File;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class CameraFragment extends Fragment {

	private static final int CAMERA_PERMISSION_REQUEST_CODE = 5;

	public interface PictureCapturingListener {
		void onPictureTaken(String picturePath);
	}

	private PictureCapturingListener mCallback;
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
			FileUtils.writeToFile(pictureFile, data);
			camera.startPreview();
			mCallback.onPictureTaken(pictureFile.getPath());
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_camera, container, false);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mCallback = (PictureCapturingListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement OnPictureTakenListener!");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this.getActivity(), new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_REQUEST_CODE);
			return;
		}
		initializeCameraPreview();
		initializeCaptureButton();
	}

	@Override
	public void onPause() {
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
			Toast.makeText(this.getActivity(), R.string.camera_permission_required_toast_text, Toast.LENGTH_LONG).show();
		}
	}

	private void initializeCameraPreview() {
		camera = getCameraInstance();
		cameraPreview = new CameraPreview(getActivity(), camera);
		FrameLayout preview = (FrameLayout) getView().findViewById(R.id.cameraPreview);
		preview.addView(cameraPreview);
	}

	private void initializeCaptureButton() {
		FloatingActionButton captureButton = (FloatingActionButton) getView().findViewById(R.id.buttonCapture);
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
