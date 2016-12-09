package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.TagsCompletionView;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.Photo;

public class CompleteSessionActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_session);
	}

	public void saveSession(View v) {
		TagsCompletionView tagsView = (TagsCompletionView) findViewById(R.id.tagsTextView);
		HashSet<String> tagsSet = new HashSet<>(tagsView.getObjects());
		HashSet<Photo.Series> seriesSet = new HashSet<>();
		Photo photo = new Photo("GENERATE_SOME_ID", "PUT_ACTUAL_PATH_TO_PICTURE_HERE", tagsSet, seriesSet, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date()));
		// TODO: fix the line above, so that actual pertinent data gets stored
		if (JSONUtils.createMetadataFile(photo)) {
			Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();
		}
		finish();
	}

}
