package cz.cvut.fjfi.pvs.pvs2016;

import java.io.File;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class ShareAsyncTask extends AsyncTask<String, Void, File> {

	private ProgressDialog dialog;
	private MainActivity activity;

	public ShareAsyncTask(MainActivity activity) {
		this.activity = activity;
		this.dialog = new ProgressDialog(activity);
	}

	@Override
	protected File doInBackground(String... params) {
		if (params.length < 1) return null;
		String seriesName = params[0];
		return FileUtils.createPdfForSharingAndGetUri(seriesName);
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage(activity.getResources().getString(R.string.share_progress_dialog));
		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected void onPostExecute(File file) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
