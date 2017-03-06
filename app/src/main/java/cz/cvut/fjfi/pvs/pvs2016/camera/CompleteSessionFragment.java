package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import cz.cvut.fjfi.pvs.pvs2016.PhotosStaticCache;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.TokenCompletionView;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.rearrange.RearrangementActivity;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class CompleteSessionFragment extends Fragment {

	public interface SessionCompleteListener {

		void onSettingTagsCancel();

		void onSessionTagsComplete(List<String> series, List<String> tags);
	}

	private SessionCompleteListener mCallback;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_complete_session, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getView().findViewById(R.id.btn_settingTagsCancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.onSettingTagsCancel();
			}
		});
		getView().findViewById(R.id.btn_settingTagsComplete).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TokenCompletionView tagsView = (TokenCompletionView) getView().findViewById(R.id.tagsCompletionView);
				TokenCompletionView seriesView = (TokenCompletionView) getView().findViewById(R.id.seriesCompletionView);
				mCallback.onSessionTagsComplete(seriesView.getObjects(), new ArrayList<>(new HashSet<>(tagsView.getObjects())));
			}
		});
		TokenCompletionView tagsView = (TokenCompletionView) getView().findViewById(R.id.tagsCompletionView);
		tagsView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(PhotosStaticCache.getTags())));
		TokenCompletionView seriesView = (TokenCompletionView) getView().findViewById(R.id.seriesCompletionView);
		seriesView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, PhotosStaticCache.getSeriesNames()));
		// FIXME limit series to one for now
		seriesView.setTokenLimit(1);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mCallback = (SessionCompleteListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement SessionCompleteListener!");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RearrangementActivity.REARRANGEMENT_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle photoBundle = data.getExtras();
				ArrayList<Photo> photoList = photoBundle.getParcelableArrayList(RearrangementActivity.PHOTO_LIST_PARAMETER);
				// duplicate saving to cache, need discussion about this, TODO
				for (Photo p : photoList) {
					try {
						// duplicate creation of meta-files, TODO
						JSONUtils.createMetadataFile(p);
					} catch (Exception e) {
						Log.e(this.getClass().getSimpleName(), "Cannot create metadata file for picture with path: " + p.getPath());
					}
				}
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//todo handle
			}
		}
	}

}
