package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import java.util.ArrayList;

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

public class RecyclerGridFragment extends Fragment implements OnStartDragListener {

	private ItemTouchHelper mItemTouchHelper;

	public RecyclerGridFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		RearrangementActivity activity = (RearrangementActivity) getActivity();
		ArrayList<Photo> photoList = activity.getPhotoList();
		final RecyclerListAdapter adapter = new RecyclerListAdapter(this, photoList);

		RecyclerView recyclerView = (RecyclerView) view;
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);

		final int spanCount = getResources().getInteger(R.integer.grid_columns);
		final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
		recyclerView.setLayoutManager(layoutManager);

		ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
		mItemTouchHelper = new ItemTouchHelper(callback);
		mItemTouchHelper.attachToRecyclerView(recyclerView);
	}

	@Override
	public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
		mItemTouchHelper.startDrag(viewHolder);
	}


}
