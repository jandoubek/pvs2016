package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.File;
import java.util.ArrayList;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

/**
 * When starting this activity non-empty list under key {@link IApplicationConstants#PICTURES_PATHS_INTENT_EXTRA}
 * in {@link Intent#getExtras()} <b>MUST</b> be set using {@link Intent#putStringArrayListExtra(String, ArrayList)}.
 */
public class PreviewActivity extends Activity {

	private final String LOG_DESC = "PreviewActivity";
	private ArrayList<String> sessionPicturePaths;
	private Picasso picasso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sessionPicturePaths = getIntent().getStringArrayListExtra(IApplicationConstants.PICTURES_PATHS_INTENT_EXTRA);
		setContentView(R.layout.activity_preview);
		//dont want to use at this moment, todo enahnce speed
		//setUpPicasso();
		//enhanceImage();
		showPictureZoomable(getCurrentPicturePath());
	}

	private String getCurrentPicturePath() {
		return sessionPicturePaths.get(sessionPicturePaths.size() - 1);
	}

	private void showPictureZoomable(String path) {
		SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) findViewById(R.id.imageView);
		imageView.setImage(ImageSource.uri(path));
	}

	@Deprecated
	private void showPicture(String pictureFilePath) {
		Log.d(LOG_DESC, "Path of picture to preview: " + pictureFilePath);

		ImageView viewById = (ImageView) findViewById(R.id.imageView);
		picasso.load(new File(pictureFilePath)).fit().centerCrop().into(viewById, new Callback() {
			@Override
			public void onSuccess() {
				Log.d("Callback in picasso", "success");
			}

			@Override
			public void onError() {
				Log.d("Callback in picasso", "error");

			}
		});
	}

	private void setUpPicasso() {
		this.picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
			@Override
			public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
				exception.printStackTrace();
			}
		}).build();
		this.picasso.setLoggingEnabled(true);
	}

	public void discardButtonClicked(View view) {
		deleteImage();
		this.finish();
		startActivity(createActivityIntentWithExtra(CameraActivity.class));
	}

	public void nextPictureButtonClicked(View view) {
		this.finish();
		startActivity(createActivityIntentWithExtra(CameraActivity.class));
	}

	public void sessionCompleteButtonClicked(View view) {
		this.finish();
		startActivity(createActivityIntentWithExtra(CompleteSessionActivity.class));
	}

	private Intent createActivityIntentWithExtra(Class clazz) {
		Intent cameraIntent = new Intent(this, clazz);
		cameraIntent.putStringArrayListExtra(IApplicationConstants.PICTURES_PATHS_INTENT_EXTRA, sessionPicturePaths);
		return cameraIntent;
	}

	private void deleteImage() {
		String currentPicturePath = getCurrentPicturePath();
		boolean deleted = FileUtils.deleteFile(currentPicturePath, false);
		sessionPicturePaths.remove(currentPicturePath);
		if (!deleted) {
			Log.e("PreviewActivity", "Could not delete file: " + currentPicturePath);
		}
	}
}
