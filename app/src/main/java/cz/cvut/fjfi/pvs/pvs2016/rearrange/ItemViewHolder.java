package cz.cvut.fjfi.pvs.pvs2016.rearrange;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

	public final ImageView imageView;

	public ItemViewHolder(View itemView) {
		super(itemView);
		imageView = (ImageView) itemView.findViewById(R.id.photoContainer);
	}

	@Override
	public void onItemSelected() {
		itemView.setBackgroundColor(Color.LTGRAY);
	}

	@Override
	public void onItemClear() {
		itemView.setBackgroundColor(0);
	}
}
