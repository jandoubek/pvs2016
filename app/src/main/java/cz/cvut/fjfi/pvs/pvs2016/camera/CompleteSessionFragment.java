package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import cz.cvut.fjfi.pvs.pvs2016.PhotosStaticCache;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.TokenCompletionView;

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

}
