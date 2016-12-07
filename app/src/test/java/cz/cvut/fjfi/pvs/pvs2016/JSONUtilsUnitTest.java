package cz.cvut.fjfi.pvs.pvs2016;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
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
	private File jsonFile;
	private ArrayList<Photo> photos;
	private String testJsonString = "[\n" +
			"    {\n" +
			"        \"id\": \"test\",\n" +
			"        \"path\": \"test.jpg\",\n" +
			"        \"tags\": [\n" +
			"            \"tag1\",\n" +
			"            \"tag2\",\n" +
			"            \"tag3\"\n" +
			"        ],\n" +
			"        \"series\": [\n" +
			"            {\n" +
			"                \"name\": \"series1\",\n" +
			"                \"index\": 2\n" +
			"            },\n" +
			"            {\n" +
			"                \"name\": \"series2\",\n" +
			"                \"index\": 42\n" +
			"            }\n" +
			"        ],\n" +
			"        \"timestamp\": \"2016-12-11T09:42\",\n" +
			"    }\n" +
			"]";

	@Before
	public void prepare() {
		Assume.assumeTrue(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));

		File mediaStorageDir = FileUtils.getMediaStorageDir();
		Assume.assumeTrue(mediaStorageDir != null);
		this.jsonFile = new File(mediaStorageDir.getPath() + File.separator +
				"IMG_19920915_004842.json");
		boolean success = FileUtils.writeToFile(this.jsonFile, testJsonString.getBytes());
		Assume.assumeTrue(success);
	}

	@Test
	public void listingAndParsingTest() {
		photos = JSONUtils.getPhotoList();
		assertTrue(photos.size() == 1);
		Photo photo = photos.get(0);
		assertTrue(photo.id.equals("test"));
		assertTrue(photo.path.equals("test.jpg"));
		HashSet<String> testTags = new HashSet<>();
		testTags.add("tag1");
		testTags.add("tag2");
		testTags.add("tag3");
		assertTrue(photo.tags.equals(testTags));
		HashSet<Photo.Series> testSeries = photo.series;
		Photo.Series series1 = new Photo.Series("series1", 2);
		Photo.Series series2 = new Photo.Series("series2", 42);
		assertTrue(testSeries.contains(series1));
		assertTrue(testSeries.contains(series2));
		assertTrue(photo.timestamp.equals("2016-12-11T09:42"));
	}

	@After
	public void cleanUp() {
		jsonFile.delete();
	}
}
