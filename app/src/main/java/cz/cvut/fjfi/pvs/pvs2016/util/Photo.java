package cz.cvut.fjfi.pvs.pvs2016.util;

import java.util.HashSet;

public class Photo {
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
