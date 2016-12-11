package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
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
		// TODO pass photos from current session to activity taking care of reordering them
		//		Intent reorderAndCompleteIntent = new Intent(this, ACTIVITY_NAME.class);
		//		reorderAndCompleteIntent.putParcelableArrayListExtra("extra_name", photoList);
		//		startActivity(reorderAndCompleteIntent);
		Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();
		finish();
	}

	public void cancelSession(View view) {
		for (String path : sessionPicturePaths) {
			// TODO check if all files were deleted, do not ignore return value
			FileUtils.deleteFile(path);
		}
		finish();
	}

}
