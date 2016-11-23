package cz.cvut.fjfi.pvs.pvs2016.camera;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.opencv_imgproc.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bytedeco.javacpp.opencv_core.BORDER_DEFAULT;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.blur;

public class CameraActivity extends Activity implements ICaptureDialogListener {

	private final CameraActivity self = this;

	private Camera camera;
	private CameraPreview cameraPreview;

	private PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d("CameraActivity", "JPEG picture created.");
			File pictureFile = FileUtils.getOutputMediaFile(FileUtils.MEDIA_TYPE_IMAGE);
			if (pictureFile == null) {
				return;
			}
			try {
				Intent previewIntent = new Intent(self, PreviewActivity.class);
				previewIntent.putExtra("picturePath", pictureFile.getPath());
				self.startActivity(previewIntent);

				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();

				DialogFragment dialog = new AfterCaptureDialog();
				dialog.show(getFragmentManager(), "actionDialog");
			} catch (FileNotFoundException e) {
				//
			} catch (IOException e) {
				//
			}
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
		ensureHiddenStatusBar();
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

	private void ensureHiddenStatusBar() {
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
	}

	private void initializeCaptureButton() {
		Button captureButton = (Button) findViewById(R.id.buttonCapture);
		captureButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						camera.takePicture(null, null, mPicture);
					}
				}
		);
	}

	private Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
			// show dialog
		}
		return c; // returns null if camera is unavailable
	}

	@Override
	public void onContinueClick() {
	}

	@Override
	public void onCancelClick() {

	}

	@Override
	public void onSaveClick() {

	}
}
