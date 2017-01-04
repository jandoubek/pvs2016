package cz.cvut.fjfi.pvs.pvs2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class SeriesItemAdapter extends RecyclerView.Adapter<SeriesItemAdapter.SeriesViewHolder> {
	private List<Series> mFiles;
	private final Callback mCallback;
	private int[] materialColors;
	private static final Random RANDOM = new Random();
	private Context context;

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
		materialColors = viewGroup.getContext().getResources().getIntArray(R.array.material_colors);
		context = viewGroup.getContext();
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
		private Series series;

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
					createDeleteConfirmationDialog().show();
				}
			});

		}

		@Override
		public void onClick(View v) {
			mCallback.onSeriesClicked(series);
		}

		public void bind(Series item) {
			series = item;
			textView.setText(series.getName());
			setupIcon(item);
		}

		private void setupIcon(Series item) {
			icon.setInitials(true);
			icon.setInitialsNumber(1);
			icon.setLetterSize(18);
			icon.setShapeColor(materialColors[RANDOM.nextInt(materialColors.length)]);
			icon.setLetter(item.getName());
		}

		private AlertDialog createDeleteConfirmationDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			final List<Photo> photos = PhotosStaticCache.getSeriesPhotos(series);
			// FIXME lokalizace
			builder.setMessage("Do you really want to delete " + photos.size() + " photos?")
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							for (Photo p : photos) {
								FileUtils.deleteFile(p.getPath());
								PhotosStaticCache.removePhoto(p);
							}
							//							mFiles.remove(series);
							notifyDataSetChanged();
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							dialogInterface.dismiss();
						}
					});
			return builder.create();
		}
	}

}
