package cz.cvut.fjfi.pvs.pvs2016.gallery.fullscreen;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class FullscreenItemActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		Bundle photoBundle = getIntent().getExtras();
		ArrayList<Photo> photoList = photoBundle.getParcelableArrayList(IApplicationConstants.GALLERY_PHOTO_LIST_EXTRA);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreeen_gallery);
		int actualPosition = getIntent().getIntExtra(IApplicationConstants.POSITION_INTENT_EXTRA, 0);
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new FullscreenItemAdapter(this, photoList));

		pager.setCurrentItem(actualPosition);

	}
}
