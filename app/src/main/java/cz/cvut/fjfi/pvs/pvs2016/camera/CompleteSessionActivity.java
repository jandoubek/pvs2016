package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.PhotosStaticCache;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.TokenCompletionView;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;
import cz.cvut.fjfi.pvs.pvs2016.rearrange.RearrangementActivity;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class CompleteSessionActivity extends Activity {

	private ArrayList<String> sessionPicturePaths;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_session);
		sessionPicturePaths = getIntent().getStringArrayListExtra(IApplicationConstants.PICTURES_PATHS_INTENT_EXTRA);
		TokenCompletionView tagsView = (TokenCompletionView) findViewById(R.id.tagsCompletionView);
		tagsView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(PhotosStaticCache.getTags())));
		TokenCompletionView seriesView = (TokenCompletionView) findViewById(R.id.seriesCompletionView);
		seriesView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(PhotosStaticCache.getSeriesNames())));
	}

	public void saveSession(View v) {
		TokenCompletionView tagsView = (TokenCompletionView) findViewById(R.id.tagsCompletionView);
		TokenCompletionView seriesView = (TokenCompletionView) findViewById(R.id.seriesCompletionView);
		Set<String> tagsSet = new HashSet<>(tagsView.getObjects());
		// TODO handle adding to more series
		Series s = new Series(seriesView.getObjects().isEmpty() ? seriesView.getObjects().get(0) : "");
		int actualSeriesSize = PhotosStaticCache.getLastIndexInSeries(s.getName());
		// list of Photo objects to be passed to reordering activity
		ArrayList<Photo> photoList = new ArrayList<>();
		for (int i = 0; i < sessionPicturePaths.size(); i++) {
			s.setIndex(actualSeriesSize + i);
			Photo photo = new Photo(Photo.generateId(), sessionPicturePaths.get(i), tagsSet, new HashSet<>(Arrays.asList(s)), Photo.generateTimestamp());
			try {
				if (JSONUtils.createMetadataFile(photo)) {
					photoList.add(photo);
					PhotosStaticCache.addPhoto(photo);
				}
			} catch (Exception e) {
				Log.e(this.getClass().getSimpleName(), "Cannot create metadata file for picture with path: " + sessionPicturePaths.get(i));
			}
		}
		startActivityForResult(creteIntentWithArrayListBundle(photoList), RearrangementActivity.REARRANGEMENT_REQUEST_CODE);
		Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();
	}

	private Intent creteIntentWithArrayListBundle(ArrayList list) {
		Intent reorderAndCompleteIntent = new Intent(this, RearrangementActivity.class);
		Bundle photoBundle = new Bundle();
		photoBundle.putParcelableArrayList(RearrangementActivity.PHOTO_LIST_PARAMETER, list);
		reorderAndCompleteIntent.putExtras(photoBundle);
		return reorderAndCompleteIntent;
	}

	public void cancelSession(View view) {
		for (String path : sessionPicturePaths) {
			// TODO check if all files were deleted, do not ignore return value
			FileUtils.deleteFile(path);
		}
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RearrangementActivity.REARRANGEMENT_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle photoBundle = data.getExtras();
				ArrayList<Photo> photoList = photoBundle.getParcelableArrayList(RearrangementActivity.PHOTO_LIST_PARAMETER);
				// duplicit saving to cache, need discussion about this, TODO
				refreshCache(photoList);
				for (Photo p : photoList) {
					try {
						// duplicit creation of meta-files, TODO
						JSONUtils.createMetadataFile(p);
					} catch (Exception e) {
						Log.e(this.getClass().getSimpleName(), "Cannot create metadata file for picture with path: " + p.getPath());
					}
				}
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//todo handle
			}
		}
		finish();
	}

	private void refreshCache(ArrayList<Photo> photoList) {
		PhotosStaticCache.removePhotos(photoList);
		PhotosStaticCache.addPhotos(photoList);
	}

}
