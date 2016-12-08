package cz.cvut.fjfi.pvs.pvs2016.util;

import static org.bytedeco.javacpp.opencv_core.CV_8UC3;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.Scalar;
import static org.bytedeco.javacpp.opencv_core.Size;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import java.io.File;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ImageUtilsTest {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Test
	public void linearTransformation() throws Exception {
		Mat source = new Mat( 1, 1, CV_8UC3, new Scalar( 1, 20, 100, 4));

		Mat transformed = ImageUtils.linearTransformation(source, 2, 3);

		assertThat(transformed.data().get(), is((byte) 5));
		assertThat(transformed.data().get(1), is((byte) 43));
		assertThat(transformed.data().get(2), is((byte) -53));

	}

	@Test
	public void writeReadTest() throws Exception {
		Mat source = new Mat( 1, 1, CV_8UC3, new Scalar( 1, 20, 100, 4));
		String filename = testFolder.getRoot().getPath() + "test.png";
		assertTrue(ImageUtils.saveMatToFile(source, filename));

		Mat loaded = ImageUtils.loadFileToMat(new File(filename));

		assertThat(loaded.data().get(), is(equalTo(source.data().get())));
		assertThat(loaded.data().get(1), is(equalTo(source.data().get(1))));
		assertThat(loaded.data().get(2), is(equalTo(source.data().get(2))));

	}

}