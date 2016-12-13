package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {


	private final ItemTouchHelperAdapter mAdapter;

	public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
		mAdapter = adapter;
	}

	@Override
	public boolean isLongPressDragEnabled() {
		return true;
	}

	@Override
	public boolean isItemViewSwipeEnabled() {
		return true;
	}

	@Override
	public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		int dragFlags = ItemTouchHelper.UP   | ItemTouchHelper.DOWN |
				ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//		int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
		int swipeFlags = 0;
		return makeMovementFlags(dragFlags, swipeFlags);
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
			RecyclerView.ViewHolder target) {
		mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
		return true;
	}

	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
	}

	@Override
	public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
			int actionState) {
		// only want active item
		if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
			if (viewHolder instanceof ItemTouchHelperViewHolder) {
				ItemTouchHelperViewHolder itemViewHolder =
						(ItemTouchHelperViewHolder) viewHolder;
				itemViewHolder.onItemSelected();
			}
		}

		super.onSelectedChanged(viewHolder, actionState);
	}
	@Override
	public void clearView(RecyclerView recyclerView,
			RecyclerView.ViewHolder viewHolder) {
		super.clearView(recyclerView, viewHolder);

		if (viewHolder instanceof ItemTouchHelperViewHolder) {
			ItemTouchHelperViewHolder itemViewHolder =
					(ItemTouchHelperViewHolder) viewHolder;
			itemViewHolder.onItemClear();
		}
	}

}