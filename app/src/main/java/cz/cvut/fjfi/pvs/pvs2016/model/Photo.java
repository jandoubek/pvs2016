package cz.cvut.fjfi.pvs.pvs2016.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

	private String id;
	private String path;
	private Set<String> tags;
	private Set<Series> series;
	private String timestamp;

	public Photo(String id, String path, Set<String> tags, Set<Series> series, String timestamp) {
		this.id = id;
		this.path = path;
		this.tags = tags;
		this.series = series;
		this.timestamp = timestamp;
	}

	public Photo(Parcel in) {
		this.id = in.readString();
		this.path = in.readString();
		this.tags = (Set<String>) in.readSerializable();
		this.series = (Set<Series>) in.readSerializable();
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
		parcel.writeSerializable((Serializable) tags);
		parcel.writeSerializable((Serializable) series);
		parcel.writeString(timestamp);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return absolute path to image
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path absolute path to image
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<Series> getSeries() {
		return series;
	}

	public void setSeries(Set<Series> series) {
		this.series = series;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public static String generateId() {
		return "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}

	public static String generateTimestamp() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());
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
