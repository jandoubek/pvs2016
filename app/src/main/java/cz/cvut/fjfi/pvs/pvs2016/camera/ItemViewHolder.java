package cz.cvut.fjfi.pvs.pvs2016.camera;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

	public final TextView textView;

	public final ImageView handleView;

	public ItemViewHolder(View itemView) {
		super(itemView);
		textView = (TextView) itemView.findViewById(R.id.text);
		handleView = (ImageView) itemView.findViewById(R.id.handle);
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
