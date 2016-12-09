package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.Photo;

public class RearrangementActivity extends FragmentActivity {

	public static final String PHOTO_LIST_PARAMETER = "photoList";

	private ArrayList<Photo> photoList;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		photoList = bundle.getParcelableArrayList(PHOTO_LIST_PARAMETER);

		setContentView(R.layout.activity_rearrange);

		// RETURNING RESULT
//		Intent returnIntent = new Intent();
//		Bundle resultPhotoListBundle = new Bundle();
//		resultPhotoListBundle.putParcelableArrayList(PHOTO_LIST_PARAMETER, photoList);
//		returnIntent.putExtras(bundle);
//		setResult(Activity.RESULT_OK, returnIntent);
//		finish();
	}

	protected ArrayList<Photo> getPhotoList(){
		return photoList;
	}
}
