package cz.cvut.fjfi.pvs.pvs2016.gallery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.gallery.fullscreen.FullscreenItemActivity;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.util.DisplayUtils;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryItemViewHolder> {

	private List<Photo> photoItems;

	private Context self;

	private Picasso picasso;

	public GalleryListAdapter(ArrayList<Photo> data) {
		photoItems = data;
	}

	@Override
	public GalleryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		self = parent.getContext();
		picasso = Picasso.with(self);
		View view = LayoutInflater.from(self).inflate(R.layout.item_gallery, parent, false);
		return new GalleryItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final GalleryItemViewHolder holder, final int position) {
		String filePath = photoItems.get(position).getPath();
		File imageFile = new File(filePath);

		int columns = self.getResources().getInteger(R.integer.gallery_grid_columns);
		float imageMargin = self.getResources().getDimension(R.dimen.gallery_margin);
		int imageWidth = DisplayUtils.computeWidthForColumnsWithMargin(self, columns, imageMargin);
		int imageHeight = DisplayUtils.computeHeightForColumnsWithMargin(self, columns, imageMargin, filePath);
		holder.imageView.setOnClickListener(new OnImageClickListener(position));
		picasso.load(imageFile).resize(imageWidth, imageHeight).into(holder.imageView);

	}

	@Override
	public int getItemCount() {
		return photoItems.size();
	}

	class OnImageClickListener implements View.OnClickListener {

		private int postion;

		public OnImageClickListener(int position) {
			this.postion = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(self, FullscreenItemActivity.class);
			intent.putExtra(IApplicationConstants.POSITION_INTENT_EXTRA, postion);

			Bundle photoBundle = new Bundle();
			photoBundle.putParcelableArrayList(IApplicationConstants.GALLERY_PHOTO_LIST_EXTRA, new ArrayList<Parcelable>(photoItems));
			intent.putExtras(photoBundle);

			self.startActivity(intent);
		}
	}
}

