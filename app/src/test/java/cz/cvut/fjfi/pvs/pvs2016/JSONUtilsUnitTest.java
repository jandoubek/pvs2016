package cz.cvut.fjfi.pvs.pvs2016;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import android.os.Environment;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.model.Series;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;

public class JSONUtilsUnitTest {
	private File testJsonFile;
	private Photo testPhoto;
	private String testJsonString;

	@Before
	public void initializePhotoObjectAndJson() {
		Assume.assumeTrue(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
		File mediaStorageDir = FileUtils.getMediaStorageDir();
		Assume.assumeTrue(mediaStorageDir != null);
		// absolute path to image
		String photoPath = mediaStorageDir.getAbsolutePath() + "/IMG_19920915_004842.jpg";
		testJsonString = "{\"id\":\"IMG_19920915_004842\",\"path\":\"" + photoPath + "\",\"tags\":[\"tag1\",\"tag2\",\"tag3\"],\"series\":"
				+ "[{\"name\":\"series1\",\"index\":2},{\"name\":\"series2\",\"index\":42}],\"timestamp\":\"2016-12-11T09:42\"}";

		// initialize Photo object for test
		List<String> testTags = new ArrayList<>();
		testTags.addAll(Arrays.asList("tag1", "tag2", "tag3"));
		List<Series> testSeries = new ArrayList<>();
		testSeries.addAll(Arrays.asList(new Series("series1", 2), new Series("series2", 42)));
		testPhoto = new Photo("IMG_19920915_004842", photoPath, testTags, testSeries, "2016-12-11T09:42");

		// create test JSON file
		this.testJsonFile = new File(mediaStorageDir, "IMG_19920915_004842.json");
	}

	@Test
	public void gsonReadTest() throws IOException {
		// Create metadata file
		boolean success = FileUtils.writeToFile(this.testJsonFile, testJsonString.getBytes());
		Assume.assumeTrue(success);

		// Test finding and parsing it
		List<Photo> photos = JSONUtils.getPhotoList();
		assertTrue(photos.size() == 1);
		Photo photo = photos.get(0);
		assertTrue(photo.getId().equals(testPhoto.getId()));
		assertTrue(photo.getPath().equals(testPhoto.getPath()));
		assertTrue(photo.getTimestamp().equals(testPhoto.getTimestamp()));
		// TODO check series
		assertTrue(photo.getTags().containsAll(testPhoto.getTags()));
		assertTrue(testJsonFile.delete());
	}

	@Test
	public void gsonWriteReadTest() throws Exception {
		assertTrue(JSONUtils.createMetadataFile(testPhoto));
		List<Photo> photos = JSONUtils.getPhotoList();
		assertTrue(photos.size() == 1);
		Photo photo = photos.get(0);
		assertTrue(photo.getId().equals(testPhoto.getId()));
		assertTrue(photo.getPath().equals(testPhoto.getPath()));
		assertTrue(photo.getTimestamp().equals(testPhoto.getTimestamp()));
		// TODO check series
		assertTrue(photo.getTags().containsAll(testPhoto.getTags()));
		assertTrue(testJsonFile.delete());
	}

	@After
	public void cleanUp() {
		testJsonFile.delete();
	}
}
