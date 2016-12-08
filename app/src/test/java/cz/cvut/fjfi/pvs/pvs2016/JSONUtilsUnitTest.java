package cz.cvut.fjfi.pvs.pvs2016;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import android.os.Environment;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.JSONUtils;
import cz.cvut.fjfi.pvs.pvs2016.util.Photo;

/**
 * Created by fnj on 7.12.16.
 */

public class JSONUtilsUnitTest {
	private File testJsonFile;
	private Photo testPhoto;
	private String testJsonString = "{\"id\":\"IMG_19920915_004842\",\"path\":\"IMG_19920915_004842.jpg\",\"tags\":[\"tag1\",\"tag2\",\"tag3\"],\"series\":"
			+ "[{\"name\":\"series1\",\"index\":2},{\"name\":\"series2\",\"index\":42}],\"timestamp\":\"2016-12-11T09:42\"}";

	@Before
	public void createTestPhoto() {
		HashSet<String> testTags = new HashSet<>();
		testTags.addAll(Arrays.asList("tag1", "tag2", "tag3"));
		HashSet<Photo.Series> testSeries = new HashSet<>();
		testSeries.addAll(Arrays.asList(new Photo.Series("series1", 2), new Photo.Series("series2", 42)));
		testPhoto = new Photo("IMG_19920915_004842", "IMG_19920915_004842.jpg", testTags, testSeries, "2016-12-11T09:42");
	}

	@Before
	public void createTestJsonFile() {
		Assume.assumeTrue(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
		File mediaStorageDir = FileUtils.getMediaStorageDir();
		Assume.assumeTrue(mediaStorageDir != null);
		this.testJsonFile = new File(mediaStorageDir, "IMG_19920915_004842.json");
	}

	@Test
	public void gsonReadTest() {
		// Create metadata file
		boolean success = FileUtils.writeToFile(this.testJsonFile, testJsonString.getBytes());
		Assume.assumeTrue(success);

		// Test finding and parsing it
		ArrayList<Photo> photos = JSONUtils.getPhotoList();
		assertTrue(photos.size() == 1);
		Photo photo = photos.get(0);
		assertTrue(photo.equals(testPhoto));
		assertTrue(testJsonFile.delete());
	}

	@Test
	public void gsonWriteReadTest() {
		JSONUtils.createMetadataFile(testPhoto);
		ArrayList<Photo> photos = JSONUtils.getPhotoList();
		assertTrue(photos.size() == 1);
		Photo photo = photos.get(0);
		assertTrue(photo.equals(testPhoto));
		assertTrue(testJsonFile.delete());
	}

	@After
	public void cleanUp() {
		testJsonFile.delete();
	}
}
