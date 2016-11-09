package cz.cvut.fjfi.pvs.pvs2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private List<String> listItems = new ArrayList<>();
	private ArrayAdapter arrayAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
		setSupportActionBar(myToolbar);
		ListView listView = (ListView) findViewById(R.id.main_activity_list);
		arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(arrayAdapter);
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

	public void addTextToList(View view) {
		EditText editText = (EditText) findViewById(R.id.main_activity_edit_text);
		String text = editText.getText().toString();
		if (TextUtils.isEmpty(text)) {
			Toast.makeText(this, "You have to write something before pushing button!", Toast.LENGTH_SHORT).show();
			return;
		}
		listItems.add(text);
		arrayAdapter.notifyDataSetChanged();
		editText.setText("");
	}

	public void showMagicNote(View view) {
		Toast.makeText(this, "Magic note", Toast.LENGTH_SHORT).show();
	}

	public void showJDsNote(View view) {
		Toast.makeText(this, "Who is John Galt", Toast.LENGTH_SHORT).show();
	}

	public void showFNjsNote(View view) {
		Toast.makeText(this, "Make America Great Again!", Toast.LENGTH_SHORT).show();
	}
}
