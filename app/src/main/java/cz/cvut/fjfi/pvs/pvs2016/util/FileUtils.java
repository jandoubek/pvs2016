package cz.cvut.fjfi.pvs.pvs2016.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;

public class FileUtils {

	private static final String logIdentifier = "FileUtils";

	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final String imageExtension = "jpg";
	private static final Pattern imageFilenamePattern = Pattern.compile("(IMG_\\d{8}_\\d{6})\\." + imageExtension);

	private static final Pattern JSONFilenamePattern = Pattern.compile("(IMG_\\d{8}_\\d{6})\\.json");
	public static final FilenameFilter JSONFilenameFilter = new FilenameFilter() {
		public boolean accept(File directory, String fileName) {
			if (!directory.equals(getMediaStorageDir())) return false;
			Matcher m = JSONFilenamePattern.matcher(fileName);
			return m.matches();
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

	public static File[] getMetadataFiles() {
		File mediaStorageDir = getMediaStorageDir();
		if (mediaStorageDir == null) return null;
		return mediaStorageDir.listFiles(JSONFilenameFilter);
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
			fileExtension = imageExtension;
			break;
		default:
			return null;
		}

		File mediaFile = new File(mediaStorageDir, fileName + "." + fileExtension);
		return mediaFile;
	}

	public static File getOutputMetadataFile(String imageFilePath) throws Exception {
		Matcher m = imageFilenamePattern.matcher(imageFilePath);
		if (!m.matches()) throw new Exception("Image file path does not match pattern.");
		String fileName = m.group(1) + ".json";
		return new File(getMediaStorageDir(), fileName);
	}

	public static boolean deleteFile(String picturePath) {
		File file = new File(picturePath);
		return file.delete(); // TODO: smazat i metadata
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
}
