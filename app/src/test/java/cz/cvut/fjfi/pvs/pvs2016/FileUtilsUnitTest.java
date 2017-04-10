package cz.cvut.fjfi.pvs.pvs2016;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import android.os.Environment;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public class FileUtilsUnitTest {

	private File testFile;
	private final byte[] testData = { 0x1 };

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

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
	public void mediaStorageWriteTest() {
		boolean success = FileUtils.writeToFile(this.testFile, testData);
		assertTrue(success);
	}

	@Test
	public void mediaStorageDeleteTest() {
		Assume.assumeTrue(FileUtils.writeToFile(testFile, testData));
		boolean deleted = FileUtils.deleteFile(testFile.getAbsolutePath(), false);
		assertTrue(deleted);
	}

	@Test
	public void getFileTest() {
		assertThat(FileUtils.getOutputMediaFile(2), is(nullValue()));
	}

	@Test
	public void writeToCrippledFile() throws Exception{
		assertFalse(FileUtils.writeToFile(temporaryFolder.newFolder(), new byte[]{0}));
	}

	@After
	public void cleanUp() {
		testFile.delete();
	}
}
