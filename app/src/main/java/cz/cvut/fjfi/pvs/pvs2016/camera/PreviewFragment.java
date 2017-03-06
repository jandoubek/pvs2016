package cz.cvut.fjfi.pvs.pvs2016.camera;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class PreviewFragment extends Fragment {

	public interface PreviewFinishedListener {
		void discardPicture();

		void takeNextPicture();

		void setTagsToSession();
	}

	private PreviewFinishedListener mCallback;
	private String picturePath;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_preview, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getView().findViewById(R.id.btn_discard).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.discardPicture();
			}
		});
		getView().findViewById(R.id.btn_nextPicture).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.takeNextPicture();
			}
		});
		getView().findViewById(R.id.btn_continueToTags).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCallback.setTagsToSession();
			}
		});
		getView().setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					mCallback.discardPicture();
					return true;
				}
				return false;
			}
		});
		picturePath = getArguments().getString(IApplicationConstants.PICTURE_PATH_ARGUMENT_KEY);
		SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) getView().findViewById(R.id.imageView);
		imageView.setImage(ImageSource.uri(picturePath));
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mCallback = (PreviewFinishedListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement PreviewFinishedListener!");
		}
	}

}
