package cz.cvut.fjfi.pvs.pvs2016.util;

import java.util.HashSet;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

	public static class Series {
		public String name;
		public int index;

		public Series(String name, int index) {
			this.name = name;
			this.index = index;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Series series = (Series) o;

			if (index != series.index) return false;
			return name.equals(series.name);
		}

		@Override
		public int hashCode() {
			int result = name.hashCode();
			result = 31 * result + index;
			return result;
		}
	}

	public String id;
	public String path;
	public HashSet<String> tags;
	public HashSet<Series> series;
	public String timestamp;

	public Photo(String id, String path, HashSet<String> tags, HashSet<Series> series, String timestamp) {
		this.id = id;
		this.path = path;
		this.tags = tags;
		this.series = series;
		this.timestamp = timestamp;
	}

	public Photo(Parcel in) {
		this.id = in.readString();
		this.path = in.readString();
		this.tags = (HashSet<String>) in.readSerializable();
		this.series = (HashSet<Series>) in.readSerializable();
		this.timestamp = in.readString();
	}

	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
		public Photo createFromParcel(Parcel in) {
			return new Photo(in);
		}

		public Photo[] newArray(int size) {
			return new Photo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(id);
		parcel.writeString(path);
		parcel.writeSerializable(tags);
		parcel.writeSerializable(series);
		parcel.writeString(timestamp);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Photo photo = (Photo) o;

		if (!id.equals(photo.id)) return false;
		if (!path.equals(photo.path)) return false;
		if (!tags.equals(photo.tags)) return false;
		if (!series.equals(photo.series)) return false;
		return timestamp.equals(photo.timestamp);

	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + path.hashCode();
		result = 31 * result + tags.hashCode();
		result = 31 * result + series.hashCode();
		result = 31 * result + timestamp.hashCode();
		return result;
	}
}
