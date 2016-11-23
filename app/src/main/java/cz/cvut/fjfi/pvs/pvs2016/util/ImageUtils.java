package cz.cvut.fjfi.pvs.pvs2016.util;

import java.io.File;

import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.Size;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.blur;

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
    public static void saveMatToFile(Mat picture, File file) {
        imwrite(file.getAbsolutePath(), picture);
    }

    /**
     * Saves Mat object to picture file. Selects image type based on the file name extension.
     *
     * @param picture  Mat representation of picture
     * @param filePath path to file to be saved
     */
    public static void saveMatToFile(Mat picture, String filePath) {
        imwrite(filePath, picture);
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

}
