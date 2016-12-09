package cz.cvut.fjfi.pvs.pvs2016;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import cz.cvut.fjfi.pvs.pvs2016.camera.CameraActivity;
import cz.cvut.fjfi.pvs.pvs2016.rearrange.RearrangementActivity;
import cz.cvut.fjfi.pvs.pvs2016.util.Photo;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
		setSupportActionBar(myToolbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.toolbar_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void takePhoto(View view) {
		Intent i = new Intent(this, RearrangementActivity.class);
		Bundle myBundle = new Bundle();
		myBundle.putParcelableArrayList(RearrangementActivity.PHOTO_LIST_PARAMETER, new ArrayList<Parcelable>(1));
		i.putExtras(myBundle);
		startActivityForResult(i, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
				Bundle bundle = data.getExtras();
				ArrayList<Photo> photoList = bundle.getParcelableArrayList(RearrangementActivity.PHOTO_LIST_PARAMETER);
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//todo handle
			}
		}
	}
}
