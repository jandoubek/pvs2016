package cz.cvut.fjfi.pvs.pvs2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;
import junit.framework.Assert;

public class PhotoStaticCacheTest {

	@Before
	public void checkUp() {
		initializationTest();
		Assume.assumeTrue(PhotosStaticCache.getAll().isEmpty());
	}

	@After
	public void cleanUp() {
		if (!PhotosStaticCache.getAll().isEmpty()) {
			List<Photo> all = PhotosStaticCache.getAll();
			PhotosStaticCache.removePhotos(all);
		}
	}

	@Ignore("Cache needs to be synchronized first.")
	@Test
	public void cacheAddRemoveTest() {
		Photo photo = new Photo("1", "path1", Collections.<String>emptySet(), Collections.<Series>emptySet(), "timeStamp1");
		PhotosStaticCache.addPhoto(photo);
		Assert.assertTrue(PhotosStaticCache.getAll().size() == 1);
		PhotosStaticCache.removePhoto(photo);
		Assert.assertTrue(PhotosStaticCache.getAll().isEmpty());
	}

	@Ignore("Cache needs to be synchronized first.")
	@Test
	public void cacheAddRemoveCollectionTest() {
		List<Photo> photos = createPhotosList();

		// adding photos
		boolean changed = PhotosStaticCache.addPhotos(photos);
		Assert.assertTrue(changed);

		// getting photos
		Assert.assertTrue(PhotosStaticCache.getAll().size() == photos.size());

		// removing photos
		changed = PhotosStaticCache.removePhotos(photos);
		Assert.assertTrue(changed);
		changed = PhotosStaticCache.removePhotos(photos);
		Assert.assertFalse(changed);

		Assert.assertTrue(PhotosStaticCache.getAll().isEmpty());

	}

	@Ignore("Cache needs to be synchronized first.")
	@Test
	public void cacheTagsTest() {
		List<Photo> photos = createPhotosList();
		PhotosStaticCache.addPhotos(photos);
		// 2 tags per photo
		Assert.assertTrue(PhotosStaticCache.getTags().size() == photos.size() * 2);

		// 2 tags per photo, half of photos in one series, other half in another series
		Assert.assertTrue(PhotosStaticCache.getTagsForSeries("Series0").size() == photos.size());
		Assert.assertTrue(PhotosStaticCache.getTagsForSeries("Series1").size() == photos.size());
	}

	@Ignore("Cache needs to be synchronized first.")
	@Test
	public void cacheSeriesTest() {
		List<Photo> photos = createPhotosList();
		PhotosStaticCache.addPhotos(photos);

		// 2 series in cache
		Assert.assertTrue(PhotosStaticCache.getSeriesNames().size() == 2);
		Assert.assertTrue(PhotosStaticCache.getSeriesPhotos("Series0").size() == 10);
	}

	private List<Photo> createPhotosList() {
		List<Photo> photos = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Set<String> tags = new HashSet<>();
			tags.add(i + "tag 1");
			tags.add(i + "tag 2");

			Series s = new Series("Series" + i / 10);
			Set<Series> series = new HashSet<>();
			series.add(s);
			photos.add(new Photo("id" + i, "path" + i, tags, series, "timeStamp" + i));
		}
		return photos;
	}

	private void initializationTest() {
		List<Photo> all = PhotosStaticCache.getAll();
		Assert.assertNotNull(all);
		Set<String> tags = PhotosStaticCache.getTags();
		Assert.assertNotNull(tags);
		List<String> seriesNames = PhotosStaticCache.getSeriesNames();
		Assert.assertNotNull(seriesNames);
	}
}
