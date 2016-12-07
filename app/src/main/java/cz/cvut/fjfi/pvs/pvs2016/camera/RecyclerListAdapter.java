package cz.cvut.fjfi.pvs.pvs2016.camera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class RecyclerListAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

	private static final String[] STRINGS = new String[]{
			"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
	};

	private final List<String> mItems = new ArrayList<>();

	private final OnStartDragListener mStartDragListener;

	public RecyclerListAdapter(OnStartDragListener listener) {
		mItems.addAll(Arrays.asList(STRINGS));
		mStartDragListener = listener;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ItemViewHolder holder, int position) {
		holder.textView.setText(mItems.get(position));
		holder.handleView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN){
					mStartDragListener.onStartDrag(holder);
				}
				return false;
			}
		});
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@Override
	public void onItemMove(int fromPosition, int toPosition) {
		if (fromPosition < toPosition) {
			for (int i = fromPosition; i < toPosition; i++) {
				Collections.swap(mItems, i, i + 1);
			}
		} else {
			for (int i = fromPosition; i > toPosition; i--) {
				Collections.swap(mItems, i, i - 1);
			}
		}
		notifyItemMoved(fromPosition, toPosition);
	}

	@Override
	public void onItemDismiss(int position) {
		mItems.remove(position);
		notifyItemRemoved(position);
	}
}
