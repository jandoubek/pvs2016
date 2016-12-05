package cz.cvut.fjfi.pvs.pvs2016.util;

import static org.bytedeco.javacpp.opencv_core.CV_32F;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.Size;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.blur;
import static org.bytedeco.javacpp.opencv_imgproc.filter2D;

import java.io.File;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.opencv_core;

public class ImageUtils {

	//***** example methods ******

	/**
	 * Loads image to Mat objects. Detects picture type form file name
	 *
	 * @param file Image file
	 * @return Mat object image representation
	 */
	public static Mat loadFileToMat(File file) {
		return imread(file.getAbsolutePath());
	}

	/**
	 * Saves Mat object to picture file. Selects image type based on the file name extension.
	 *
	 * @param picture Mat representation of picture
	 * @param file    saved to this file
	 */
	public static boolean saveMatToFile(Mat picture, File file) {
		return imwrite(file.getAbsolutePath(), picture);
	}

	/**
	 * Saves Mat object to picture file. Selects image type based on the file name extension.
	 *
	 * @param picture  Mat representation of picture
	 * @param filePath path to file to be saved
	 */
	public static boolean saveMatToFile(Mat picture, String filePath) {
		return imwrite(filePath, picture);
	}

	/**
	 * Performs blur with Gaussian filter
	 *
	 * @param source     source Mat image
	 * @param kernelSize
	 * @return new Mat image after blur
	 */
	public static Mat gaussianBlur(Mat source, Size kernelSize) {
		Mat destination = new Mat();
		blur(source, destination, kernelSize);
		return destination;
	}

	/**
	 * Loads image as black and white.
	 *
	 * @param file to load
	 * @return B&W image
	 */
	public static boolean transformToBw(File file) {
		Mat grayScale = imread(file.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
		return saveMatToFile(grayScale, file);
	}

	/**
	 * Performs linear transformation to the picture pixels new(x) = alpha * old(x) + beta.
	 *
	 * @param source source image
	 * @param alpha  adjusting contrast
	 * @param beta   adjusting brightness
	 * @return transformed image
	 */
	public static Mat linearTransformation(Mat source, double alpha, int beta) {
		Mat res = Mat.zeros(source.size(), source.type()).asMat();
		source.convertTo(res, -1, alpha, beta);
		return res;
	}

	/**
	 * Applies matrix filter to enhance sharpness of picture
	 *
	 * @param source source image
	 * @return sharpened image
	 */
	public static Mat sharpenFilter(Mat source) {
		Mat res = Mat.zeros(source.size(), source.type()).asMat();

		Mat kernel = new Mat(3, 3, CV_32F, new opencv_core.Scalar(0));
		FloatIndexer indexer = kernel.createIndexer();
		indexer.put(1, 1, 3);
		indexer.put(0, 1, -1);
		indexer.put(2, 1, -1);
		indexer.put(1, 0, -1);
		indexer.put(1, 2, -1);

		filter2D(source, res, source.depth(), kernel);
		return res;
	}

}
