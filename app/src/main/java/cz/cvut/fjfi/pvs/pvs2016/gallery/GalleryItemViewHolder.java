package cz.cvut.fjfi.pvs.pvs2016.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class GalleryItemViewHolder extends RecyclerView.ViewHolder {

	public final ImageView imageView;

	public GalleryItemViewHolder(View itemView) {
		super(itemView);
		imageView = (ImageView) itemView.findViewById(R.id.galeryPhotoContainer);
	}
}
