package cz.cvut.fjfi.pvs.pvs2016;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import android.os.Environment;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class FileUtilsUnitTest {

	private File testFile;
	private final byte[] testData = { 0x1 };

	@Before
	public void createMediaFile() {
		Assume.assumeTrue(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), IApplicationConstants.DIRECTORY_NAME);
		this.testFile = new File(mediaStorageDir.getPath() + File.separator +
				"IMG_test");
		if (!mediaStorageDir.exists()) {
			boolean dirsCreated = mediaStorageDir.mkdirs();
			Assume.assumeTrue(dirsCreated);
		}
	}

	@Test
	public void medaStorageWriteTest() {
		boolean success = FileUtils.writeToFile(this.testFile, testData);
		assertTrue(success);
	}

	@Test
	public void mediaStorageDeleteTest() {
		Assume.assumeTrue(FileUtils.writeToFile(testFile, testData));
		boolean deleted = FileUtils.deleteFile(testFile.getAbsolutePath());
		assertTrue(deleted);
	}

	@After
	public void cleanUp() {
		testFile.delete();
	}
}
