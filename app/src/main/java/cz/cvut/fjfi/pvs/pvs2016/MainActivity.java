package cz.cvut.fjfi.pvs.pvs2016;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import cz.cvut.fjfi.pvs.pvs2016.camera.CameraActivity;
import cz.cvut.fjfi.pvs.pvs2016.gallery.GalleryActivity;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

	private ShareActionProvider mShareActionProvider;

	private Context self;

	private SeriesItemAdapter seriesItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		self = this;
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

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_new);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(self, CameraActivity.class));
			}
		});

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.series_list);
		seriesItemAdapter = new SeriesItemAdapter(new SeriesItemAdapter.Callback() {
			@Override
			public void onSeriesClicked(String series) {
				Intent galleryIntent = new Intent(self, GalleryActivity.class);
				galleryIntent.putExtras(createBundleWithSeriesPhotoList(new ArrayList(PhotosStaticCache.getSeriesPhotos(series))));
				startActivity(galleryIntent);
			}
		});
		loadSeriesToAdapter();

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(seriesItemAdapter);
	}

	//	move to util maybe?
	private Bundle createBundleWithSeriesPhotoList(ArrayList list) {
		Bundle photoBundle = new Bundle();
		photoBundle.putParcelableArrayList(IApplicationConstants.GALLERY_PHOTO_LIST_EXTRA, list);
		return photoBundle;
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadSeriesToAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);

		MenuItem shareItem = menu.findItem(R.id.toolbar_share);
		mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

		final MenuItem searchItem = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(this);

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

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		seriesItemAdapter.getFilter().filter(newText);
		return true;
	}

	private void loadSeriesToAdapter() {
		seriesItemAdapter.setFiles(PhotosStaticCache.getSeriesNames());
	}

}
