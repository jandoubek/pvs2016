package cz.cvut.fjfi.pvs.pvs2016.camera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class MainFragment extends Fragment implements OnStartDragListener{

	private ItemTouchHelper mItemTouchHelper;

	public MainFragment() {
//		no code
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		RecyclerListAdapter adapter = new RecyclerListAdapter(this);
		recyclerView.setAdapter(adapter);

		ItemTouchHelper.Callback callback =	new SimpleItemTouchHelperCallback(adapter);
		mItemTouchHelper = new ItemTouchHelper(callback);
		mItemTouchHelper.attachToRecyclerView(recyclerView);
	}

	@Override
	public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
		mItemTouchHelper.startDrag(viewHolder);
	}

}
