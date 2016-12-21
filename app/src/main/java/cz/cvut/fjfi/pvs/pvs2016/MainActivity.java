package cz.cvut.fjfi.pvs.pvs2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.camera.CameraActivity;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class MainActivity extends AppCompatActivity {

	private static ListView list_View;
	private static EditText editText;
	private static ArrayAdapter<String> adapter;
	ArrayList<String> nameArr = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
		setSupportActionBar(myToolbar);
		listView();
		// initialize photos cache
		try

		{
			PhotosStaticCache.addPhotos(JSONUtils.getPhotoList());
		} catch (
				IOException e
				)

		{
			// FIXME what we want to do when loading metadata files fails? Quit app, continue like nothing happens or notify user?
			e.printStackTrace();
		}
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
		startActivity(new Intent(this, CameraActivity.class));
	}

	public void listView() {
		Set<String> setOfSeries = PhotosStaticCache.getSeriesNames();
		ArrayList<String> arrOfName = new ArrayList<String>();
		nameArr.addAll(setOfSeries);
		nameArr = new ArrayList<>(arrOfName);

		adapter = new ArrayAdapter<String>(this, R.layout.name_list, nameArr);
		list_View = (ListView) findViewById(R.id.listView1);
		list_View.setAdapter(adapter);
		editText = (EditText) findViewById(R.id.txtsearch);
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int i1, int i2) {
				if (s.toString().equals("")) {
					listView();
				} else {
					searchItem(s.toString());
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
		list_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object listItem = list_View.getItemAtPosition(position);
				String value = (String) list_View.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, "Position: " + position + "Value: " + value, Toast.LENGTH_LONG).show();
			}
		});

	}

	public void searchItem(String textToSearch) {
		String[] items = nameArr.toArray(new String[0]);
		for (String item : items) {
			if (!item.contains(textToSearch)) {
				nameArr.remove(item);
			}
		}
		adapter.notifyDataSetChanged();
	}
}
