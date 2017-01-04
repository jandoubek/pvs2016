package cz.cvut.fjfi.pvs.pvs2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;

public class SeriesItemAdapter extends RecyclerView.Adapter<SeriesItemAdapter.SeriesViewHolder> {
	private List<Series> mFiles;
	private final Callback mCallback;
	private Context self;
	private int[] materialColors;
	private static final Random RANDOM = new Random();

	public void setFiles(List<Series> files) {
		mFiles = Collections.unmodifiableList(new ArrayList<>(files));
		notifyDataSetChanged();
	}

	public interface Callback {
		void onSeriesClicked(Series series);
	}

	public SeriesItemAdapter(Callback callback) {
		mCallback = callback;
	}

	@Override
	public SeriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		self = viewGroup.getContext();
		materialColors = self.getResources().getIntArray(R.array.material_colors);
		Context context = viewGroup.getContext();
		View view = LayoutInflater.from(context).inflate(R.layout.item_series, viewGroup, false);
		return new SeriesViewHolder(view);
	}

	@Override
	public void onBindViewHolder(SeriesViewHolder seriesViewHolder, int i) {
		seriesViewHolder.bind(mFiles.get(i));
		seriesViewHolder.itemView.setLongClickable(true);
	}

	@Override
	public long getItemId(int position) {
		return mFiles.get(position).getName().hashCode();
	}

	@Override
	public int getItemCount() {
		return mFiles == null ? 0 : mFiles.size();
	}

	public class SeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private final TextView textView;
		private final Button deleteButton;
		private MaterialLetterIcon icon;
		private Series mItem;

		public SeriesViewHolder(final View itemView) {
			super(itemView);
			icon = (MaterialLetterIcon) itemView.findViewById(R.id.series_letter_image);
			textView = (TextView) itemView.findViewById(R.id.series_text);
			deleteButton = (Button) itemView.findViewById(R.id.series_delete_button);
			textView.setOnClickListener(this);
			icon.setOnClickListener(this);

			deleteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//					confirm dialog maybe and delete series
					Toast.makeText(itemView.getContext(), "TODO deleting series", Toast.LENGTH_SHORT).show();
				}
			});

		}

		@Override
		public void onClick(View v) {
			mCallback.onSeriesClicked(mItem);
		}

		public void bind(Series item) {
			mItem = item;
			textView.setText(mItem.getName());
			setupIcon(item);
		}

		private void setupIcon(Series item) {
			icon.setInitials(true);
			icon.setInitialsNumber(1);
			icon.setLetterSize(18);
			icon.setShapeColor(materialColors[RANDOM.nextInt(materialColors.length)]);
			icon.setLetter(item.getName());
		}
	}
}
