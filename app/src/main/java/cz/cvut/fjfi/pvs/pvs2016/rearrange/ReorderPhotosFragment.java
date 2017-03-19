package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class ReorderPhotosFragment extends Fragment {

	public static final String PHOTO_LIST_PARAMETER = "photoList";

	public interface ReorderingCompleteListener {

		void reorderingCancelled();

		void reorderingBack();

		void reorderingFinished(List<Photo> rearrangedPhotos);
	}

	private ReorderingCompleteListener mCallback;
	private ArrayList<Photo> photoList;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_reoder_photos, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
		getView().findViewById(R.id.btn_backReordering).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.reorderingBack();
			}
		});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		photoList = getArguments().getParcelableArrayList(PHOTO_LIST_PARAMETER);
		final RecyclerListAdapter adapter = new RecyclerListAdapter(photoList);

		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);

		final int spanCount = getResources().getInteger(R.integer.grid_columns);
		final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
		recyclerView.setLayoutManager(layoutManager);

		ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
		ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
		mItemTouchHelper.attachToRecyclerView(recyclerView);
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
