package cz.cvut.fjfi.pvs.pvs2016.gallery;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class GalleryActivity extends FragmentActivity {

	public static final String PHOTO_LIST_PARAMETER = "photoList";

	private ArrayList<Photo> photoList;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		photoList = bundle.getParcelableArrayList(PHOTO_LIST_PARAMETER);
		setContentView(R.layout.activity_gallery);
	}

	protected ArrayList<Photo> getPhotoList() {
		return photoList;
	}
}
