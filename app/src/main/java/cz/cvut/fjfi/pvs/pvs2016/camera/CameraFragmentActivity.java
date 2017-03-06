package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.PhotosStaticCache;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;
import cz.cvut.fjfi.pvs.pvs2016.rearrange.ReorderPhotosFragment;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class CameraFragmentActivity extends Activity implements CameraFragment.PictureCapturingListener, PreviewFragment.PreviewFinishedListener,
		CompleteSessionFragment.SessionCompleteListener, ReorderPhotosFragment.ReorderingCompleteListener {

	private ArrayList<String> sessionPicturePaths = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_camera);
		if (findViewById(R.id.camera_activity_container) != null) {
			// to avoid overlapping fragments
			if (savedInstanceState != null) {
				return;
			}
			CameraFragment cameraFragment = new CameraFragment();
			getFragmentManager().beginTransaction().add(R.id.camera_activity_container, cameraFragment).commit();
		}
	}

	@Override
	public void onPictureTaken(String picturePath) {
		sessionPicturePaths.add(picturePath);
		Bundle bundle = new Bundle();
		bundle.putString(IApplicationConstants.PICTURE_PATH_ARGUMENT_KEY, picturePath);
		PreviewFragment previewFragment = new PreviewFragment();
		previewFragment.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.camera_activity_container, previewFragment);
		transaction.addToBackStack("preview");
		transaction.commit();
	}

	@Override
	public void discardPicture() {
		String pictureToRemove = sessionPicturePaths.remove(sessionPicturePaths.size() - 1);
		boolean deleted = FileUtils.deleteFile(pictureToRemove, false);
		if (!deleted) {
			Log.e("CameraFragmentActivity", "Could not delete file: " + pictureToRemove);
		}
		showCameraFragment();
	}

	@Override
	public void takeNextPicture() {
		showCameraFragment();
	}

	@Override
	public void setTagsToSession() {
		android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(IApplicationConstants.SESSION_PICTURES_PATHS_ARGUMENT_KEY, sessionPicturePaths);
		CompleteSessionFragment completeSessionFragment = new CompleteSessionFragment();
		completeSessionFragment.setArguments(bundle);
		transaction.replace(R.id.camera_activity_container, completeSessionFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void showCameraFragment() {
		getFragmentManager().popBackStack();
	}

	@Override
	public void onSettingTagsCancel() {
		for (String path : sessionPicturePaths) {
			// TODO check if all files were deleted, do not ignore return value
			FileUtils.deleteFile(path, false);
		}
		showCameraFragment();
	}

	@Override
	public void onSessionTagsComplete(List<String> series, List<String> tags) {
		// TODO handle adding to more series
		Series s = new Series(!series.isEmpty() ? series.get(0) : "");
		int actualSeriesSize = PhotosStaticCache.getLastIndexInSeries(s.getName());
		// list of Photo objects to be passed to reordering activity
		ArrayList<Photo> photoList = new ArrayList<>();
		for (int i = 0; i < sessionPicturePaths.size(); i++) {
			s.setIndex(actualSeriesSize + i);
			Photo photo = new Photo(Photo.generateId(), sessionPicturePaths.get(i), tags, Arrays.asList(s), Photo.generateTimestamp());
			try {
				if (JSONUtils.createMetadataFile(photo)) {
					photoList.add(photo);
					PhotosStaticCache.addPhoto(photo);
				}
			} catch (Exception e) {
				Log.e(this.getClass().getSimpleName(), "Cannot create metadata file for picture with path: " + sessionPicturePaths.get(i));
			}
		}
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(ReorderPhotosFragment.PHOTO_LIST_PARAMETER, photoList);
		ReorderPhotosFragment reorderPhotosFragment = new ReorderPhotosFragment();
		reorderPhotosFragment.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.camera_activity_container, reorderPhotosFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void reorderingCancelled() {
		getFragmentManager().popBackStack();
	}

	@Override
	public void reorderingFinished(List<Photo> rearrangedPhotos) {
		// FIXME check whether the order has changed
		for (Photo p : rearrangedPhotos) {
			try {
				// duplicate creation of meta-files, TODO
				JSONUtils.createMetadataFile(p);
			} catch (Exception e) {
				Log.e(this.getClass().getSimpleName(), "Cannot create metadata file for picture with path: " + p.getPath());
			}
		}
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		FragmentManager fragmentManager = getFragmentManager();
		int stackEntryCount = fragmentManager.getBackStackEntryCount();
		if (stackEntryCount > 0) {
			FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(stackEntryCount - 1);
			// FIXME same logic as in else clause
			if (backStackEntry.getName().equals("preview")) {
				deletePictures(sessionPicturePaths);
				sessionPicturePaths.clear();
			}
			fragmentManager.popBackStack();
		} else {
			deletePictures(sessionPicturePaths);
			sessionPicturePaths.clear();
			finish();
		}
	}

	private void deletePictures(List<String> photoPaths) {
		for (String path : photoPaths) {
			boolean deleted = FileUtils.deleteFile(path, false);
			if (!deleted) {
				Log.e("CameraFragmentActivity", "Could not delete file: " + path);
			}
		}
	}

}
