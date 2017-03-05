package cz.cvut.fjfi.pvs.pvs2016;

import java.io.File;

import org.junit.Assume;
import org.junit.Before;

import android.os.Environment;
import cz.cvut.fjfi.pvs.pvs2016.util.FileUtils;

public abstract class ADataWriteTest {

	public File mediaStorageDir;

	@Before
	public void setup() {
		initMediaStorageDir();
	}

	private void initMediaStorageDir() {
		Assume.assumeTrue(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
		mediaStorageDir = FileUtils.getMediaStorageDir();
		Assume.assumeTrue(mediaStorageDir != null);
	}

}
