package cz.cvut.fjfi.pvs.pvs2016.gallery;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.R;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class GalleryGridFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		GalleryActivity activity = (GalleryActivity) getActivity();
		ArrayList<Photo> photoList = activity.getPhotoList();
		final GalleryListAdapter adapter = new GalleryListAdapter(photoList);

		RecyclerView recyclerView = (RecyclerView) view;
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);

		final int spanCount = getResources().getInteger(R.integer.gallery_grid_columns);
		final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
		recyclerView.setLayoutManager(layoutManager);
	}

}
