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

	public static boolean removePhoto(Photo photo) {
		return cachedPhotos.remove(photo);
	}

	public static boolean removePhotos(Collection<Photo> photoList) {
		return cachedPhotos.removeAll(photoList);
	}

	public List<Photo> getAll() {
		return cachedPhotos;
	}

	public static Set<String> getTags() {
		Set<String> allTags = new HashSet<>();
		for (Photo p : cachedPhotos) {
			allTags.addAll(p.getTags());
		}
		return allTags;
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

}
