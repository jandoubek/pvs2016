package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class ReorderPhotosFragment extends Fragment {

	public static final String PHOTO_LIST_PARAMETER = "photoList";

	public interface ReorderingCompleteListener {

		void reorderingCancelled();

		void reorderingFinished(List<Photo> rearrangedPhotos);
	}

	private ReorderingCompleteListener mCallback;
	private ArrayList<Photo> photoList;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_reoder_photos, container, true);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		photoList = getArguments().getParcelableArrayList(PHOTO_LIST_PARAMETER);
		getView().findViewById(R.id.btn_finishReordering).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.reorderingFinished(photoList);
			}
		});
		getView().findViewById(R.id.btn_cancelReordering).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.reorderingCancelled();
			}
		});
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mCallback = (ReorderingCompleteListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement RearrangementCompleteListener!");
		}
	}

	protected ArrayList<Photo> getPhotoList() {
		return photoList;
	}

}
