package cz.cvut.fjfi.pvs.pvs2016;

import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import cz.cvut.fjfi.pvs.pvs2016.camera.CameraActivity;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class MainActivity extends AppCompatActivity {

	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
		setSupportActionBar(myToolbar);

		// initialize photos cache
		try {
			PhotosStaticCache.addPhotos(JSONUtils.getPhotoList());
		} catch (IOException e) {
			// FIXME what we want to do when loading metadata files fails? Quit app, continue like nothing happens or notify user?
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		MenuItem shareItem = menu.findItem(R.id.toolbar_share);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.toolbar_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		case R.id.toolbar_share:
			Uri uriToImage = FileUtils.createPdfForSharingAndGetUri();
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
			shareIntent.setType("application/pdf");
			if (mShareActionProvider != null) {
				mShareActionProvider.setShareIntent(shareIntent);
			}
			startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_title)));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void takePhoto(View view) {
		startActivity(new Intent(this, CameraActivity.class));
	}
}
