package cz.cvut.fjfi.pvs.pvs2016;

import com.tokenautocomplete.TokenCompleteTextView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TokenCompletionView extends TokenCompleteTextView<String> {

	public TokenCompletionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setSplitChar(new char[] {',', ' ', ';'});
		setThreshold(1);
		allowDuplicates(false);
	}

	@Override
	protected View getViewForObject(String tag) {
		LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		TextView view = (TextView) l.inflate(R.layout.completion_view_token, (ViewGroup) getParent(), false);
		view.setText(tag);
		return view;
	}

	@Override
	protected String defaultObject(String completionText) {
		return completionText;
	}
}
