package cz.cvut.fjfi.pvs.pvs2016.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Photo {

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
