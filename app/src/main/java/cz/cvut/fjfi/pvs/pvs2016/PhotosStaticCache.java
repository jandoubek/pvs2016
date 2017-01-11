package cz.cvut.fjfi.pvs.pvs2016;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;

public class PhotosStaticCache {

	private static List<Photo> cachedPhotos = new ArrayList<>();

	public static boolean addPhoto(Photo photo) {
		return cachedPhotos.add(photo);
	}

	public static boolean addPhotos(Collection<Photo> photoList) {
		return cachedPhotos.addAll(photoList);
	}

	/**
	 * @return {@code true} if cache has changed, {@code false} otherwise
	 */
	public static boolean removePhoto(Photo photo) {
		int idx = findPhotoById(photo);
		if (idx != -1) {
			cachedPhotos.remove(idx);
			return true;
		}
		return false;
	}

	/**
	 * @return {@code true} if cache has changed, {@code false} otherwise
	 */
	public static boolean removePhotos(Collection<Photo> photoList) {
		boolean cacheHasChanged = false;
		for (Photo photo : photoList) {
			cacheHasChanged = cacheHasChanged || removePhoto(photo);
		}
		return cacheHasChanged;
	}

	public static List<Photo> getAll() {
		return cachedPhotos;
	}

	public static Set<String> getTags() {
		Set<String> allTags = new HashSet<>();
		for (Photo p : cachedPhotos) {
			allTags.addAll(p.getTags());
		}
		return allTags;
	}

	public static List<String> getTagsForSeries(Series series) {
		Set<String> allTags = new HashSet<>();
		List<Photo> seriesPhotos = getSeriesPhotos(series);
		for (Photo p : seriesPhotos) {
			allTags.addAll(p.getTags());
		}
		return new ArrayList<>(allTags);
	}

	public static Set<String> getSeriesNames() {
		Set<String> allSeries = new HashSet<>();
		for (Photo p : cachedPhotos) {
			for (Series s : p.getSeries()) {
				allSeries.add(s.getName());
			}
		}
		return allSeries;
	}

	public static List<Series> getSeries() {
		Set<Series> allSeries = new HashSet<>();
		for (Photo p : cachedPhotos) {
			for (Series s : p.getSeries()) {
				allSeries.add(s);
			}
		}
		return new ArrayList<>(allSeries);
	}

	public static List<Photo> getSeriesPhotos(Series series) {
		List<Photo> photoList = new ArrayList<>();
		for (Photo p : cachedPhotos) {
			if (p.getSeries().contains(series)) {
				photoList.add(p);
			}
		}
		return photoList;
	}

	public static int getLastIndexInSeries(String seriesName) {
		List<Integer> indexes = new ArrayList<>();
		Set<Series> series = getSeriesByName(seriesName);
		for (Series s : series) {
			indexes.add(s.getIndex());
		}
		return indexes.isEmpty() ? 0 : Collections.max(indexes);
	}

	private static Set<Series> getSeriesByName(String seriesName) {
		Set<Series> filteredSeries = new HashSet<>();
		for (Photo p : cachedPhotos) {
			for (Series s : p.getSeries()) {
				if (s.getName().equals(seriesName) && !filteredSeries.contains(s)) {
					filteredSeries.add(s);
				}
			}
		}
		return filteredSeries;
	}

	/**
	 * @return index of passed {@code Photo} in cache or -1 if it is not present
	 */
	private static int findPhotoById(Photo photo) {
		for (int i = 0; i < cachedPhotos.size(); i++) {
			if (cachedPhotos.get(i).getId().equals(photo.getId())) {
				return i;
			}
		}
		return -1;
	}

}
