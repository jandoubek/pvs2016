package cz.cvut.fjfi.pvs.pvs2016.gallery.fullscreen;

import java.util.ArrayList;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class FullscreenItemAdapter extends PagerAdapter {

	ArrayList<Photo> photos;

	Context self;

	Picasso picasso;

	FullscreenItemAdapter(Context activity, ArrayList<Photo> photoArrayList) {
		this.self = activity;
		this.photos = photoArrayList;
		this.picasso = Picasso.with(activity);
	}

	@Override
	public int getCount() {
		return this.photos.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (object) == view;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		LayoutInflater layoutInflater = (LayoutInflater) self.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = layoutInflater.inflate(R.layout.item_gallery_fullscreen, container, false);

		SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) viewLayout.findViewById(R.id.image_fullscreen);
		String imagePath = this.photos.get(position).getPath();
		imageView.setImage(ImageSource.uri(imagePath));
		((ViewPager) container).addView(viewLayout);
		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);
	}
}
