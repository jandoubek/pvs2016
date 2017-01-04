package cz.cvut.fjfi.pvs.pvs2016.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.PhotosStaticCache;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class FileUtils {

	private static final String logIdentifier = "FileUtils";

	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final String IMAGE_EXTENSION = ".jpg";

	private static final FilenameFilter JSON_FILENAME_FILTER = new FilenameFilter() {

		public boolean accept(File directory, String fileName) {
			if (!directory.equals(getMediaStorageDir())) return false;
			return fileName.toLowerCase().endsWith(".json");
		}

	};

	@Nullable
	public static File getMediaStorageDir() {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// handle differently
		}

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IApplicationConstants.DIRECTORY_NAME);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		return mediaStorageDir;
	}

	public static List<File> getMetadataFiles() {
		File mediaStorageDir = getMediaStorageDir();
		if (mediaStorageDir == null) return new ArrayList<>();
		File[] files = mediaStorageDir.listFiles(JSON_FILENAME_FILTER);
		if (files == null) {
			return new ArrayList<>();
		} else {
			return Arrays.asList(files);
		}
	}

	/**
	 * Create a File for saving an image or video
	 */
	@Nullable
	public static File getOutputMediaFile(int type) {
		File mediaStorageDir = getMediaStorageDir();
		if (mediaStorageDir == null) return null;

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = "IMG_" + timeStamp;
		String fileExtension;
		switch (type) {
		case MEDIA_TYPE_IMAGE:
			fileExtension = IMAGE_EXTENSION;
			break;
		default:
			return null;
		}

		File mediaFile = new File(mediaStorageDir, fileName + fileExtension);
		return mediaFile;
	}

	public static File getOutputMetadataFile(String imageFilePath) throws Exception {
		// TODO handle saving metadata to different place than pictures
		int i = imageFilePath.lastIndexOf(IMAGE_EXTENSION);
		String metadataPath = imageFilePath.substring(0, i) + ".json";
		return new File(metadataPath);
	}

	public static boolean deleteFile(String picturePath) {
		File photoFile = new File(picturePath);
		File metadataFile = new File(picturePath.replace(".jpg", ".json"));
		return photoFile.delete() && metadataFile.delete();
	}

	public static boolean writeToFile(File file, byte[] data) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
		} catch (FileNotFoundException e) {
			Log.w(logIdentifier, "Provided file to write operation was not found");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.w(logIdentifier, "IO exception occurred while writing to file " + file);
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// close quietly
				}
			}
		}
		return true;
	}

	public static Uri createPdfForSharingAndGetUri() {
		File mediaStorageDir = getMediaStorageDir();
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = "sharePDF_" + timeStamp + ".pdf";
		File file = new File(mediaStorageDir, fileName);
		List<Photo> photoList = PhotosStaticCache.getAll();
		try {
			PDFGenerator.generatePdf(photoList, file.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Uri.fromFile(file);
	}
}
