package cz.cvut.fjfi.pvs.pvs2016.camera;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.Photo;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.TagsCompletionView;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class CompleteSessionActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_session);
	}

	public void saveSession(View v) {
		TagsCompletionView tagsView = (TagsCompletionView) findViewById(R.id.tagsTextView);
		Photo photo = new Photo(3, "bla", tagsView.getObjects().toArray(new String[tagsView.getObjects().size()]));
		if (FileUtils.createMetadataJsonFile(photo)) {
			Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();
		}
		finish();
	}

}
