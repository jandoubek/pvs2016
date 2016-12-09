package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.util.Photo;

public class RecyclerListAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

	private static final String[] STRINGS = new String[]{
			"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
	};

//	private final List<Photo> mItems = new ArrayList<>();
	private final List<String> mItems = new ArrayList<>();

	private final OnStartDragListener mStartDragListener;

	private Context self;

	private Picasso picasso;

	public RecyclerListAdapter(OnStartDragListener listener, ArrayList<Photo> data) {
		mItems.addAll(Arrays.asList(STRINGS));
////		FIXME lost refference to list, need to return sorted list back somehow
//		mItems.addAll(data);
		mStartDragListener = listener;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		self = parent.getContext();
		picasso = Picasso.with(self);
		View view = LayoutInflater.from(self).inflate(R.layout.item_main, parent, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ItemViewHolder holder, int position) {
		picasso.load("http://i.imgur.com/DvpvklR.png").into(holder.imageView);
//		picasso.load(mItems.get(position).getPath()).into(holder.imageView);
		holder.imageView.setBackgroundColor(Color.GREEN);
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
