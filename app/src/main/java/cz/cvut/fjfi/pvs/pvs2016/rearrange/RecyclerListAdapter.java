package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;

public class RecyclerListAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

	private List<Photo> mItems = new ArrayList<>();

	private final OnStartDragListener mStartDragListener;

	private Context self;

	private Picasso picasso;

	private int screenWidth;

	private double ratio;

	public RecyclerListAdapter(OnStartDragListener listener, ArrayList<Photo> data) {
		mItems = data;
		mStartDragListener = listener;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		self = parent.getContext();
		picasso = Picasso.with(self);
		View view = LayoutInflater.from(self).inflate(R.layout.item_main, parent, false);
		Point screenDimensions = getScreenDimensions(self);
		screenWidth = screenDimensions.x;
		ratio = screenDimensions.x/screenDimensions.y;
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ItemViewHolder holder, int position) {
		File imageFile = new File(mItems.get(position).getPath());
		picasso.load(imageFile).resize(countWidth(), countHeight()).centerInside().into(holder.imageView);
	}

//	todo move these 3 to helper maybe?
	private int countWidth() {
		int columns = self.getResources().getInteger(R.integer.grid_columns);
		int margin = (int) (self.getResources().getDimension(R.dimen.img_margin) / self.getResources().getDisplayMetrics().density);
		return screenWidth/columns - 2 * margin;
	}

	private int countHeight() {
		return (int) (countWidth()/ratio);
	}

	private Point getScreenDimensions(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@Override
	public void onItemMove(int fromPosition, int toPosition) {
		if (fromPosition < toPosition) {
			for (int i = fromPosition; i < toPosition; i++) {
				swapSeriesIndex(mItems, i, i + 1);
				Collections.swap(mItems, i, i + 1);
			}
		} else {
			for (int i = fromPosition; i > toPosition; i--) {
				swapSeriesIndex(mItems, i, i - 1);
				Collections.swap(mItems, i, i - 1);
			}
		}
		notifyItemMoved(fromPosition, toPosition);
	}

	private void swapSeriesIndex(List<Photo> items, int fromPosition, int toPosition) {
		//		FIXME relying on at least one series
		try {
			Series fromSeries = items.get(fromPosition).getSeries().iterator().next();
			Series toSeries = items.get(toPosition).getSeries().iterator().next();
			int tmp = fromSeries.getIndex();
			fromSeries.setIndex(toSeries.getIndex());
			toSeries.setIndex(tmp);
		} catch (NoSuchElementException e) {
			//			todo handle
			e.printStackTrace();
		}
	}

	@Override
	public void onItemDismiss(int position) {
		mItems.remove(position);
		notifyItemRemoved(position);
	}
}
