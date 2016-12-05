package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.io.File;

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
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class PreviewActivity extends Activity {

	private final String LOG_DESC = "PreviewActivity";
	private String picturePathPamrameter = "picturePath";
	private String picturePath;
	private Picasso picasso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.persistPicturePath();
		setContentView(R.layout.activity_preview);
		setUpPicasso();
		showPictureZoomable(this.picturePath);
	}

	private void persistPicturePath() {
		this.picturePath = getIntent().getStringExtra(picturePathPamrameter);
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
	}

	public void nextPictureButtonClicked(View view) {
		//		todo create meta files, etc.
		this.finish();
	}

	public void sessionCompleteButtonClicked(View view) {
		this.finish();
		startActivity(new Intent(this, CompleteSessionActivity.class));
	}

	private void deleteImage() {
		boolean deleted = FileUtils.deleteFile(picturePath);
		if (!deleted) {
			Log.e("PreviewActivity", "Could not delete file: " + picturePath);
		}
	}
}
