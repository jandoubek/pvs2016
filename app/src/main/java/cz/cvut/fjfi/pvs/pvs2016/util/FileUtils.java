package cz.cvut.fjfi.pvs.pvs2016.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import android.os.Environment;
import android.util.Log;
import cz.cvut.fjfi.pvs.pvs2016.IApplicationConstants;
import cz.cvut.fjfi.pvs.pvs2016.Photo;

public class FileUtils {

	private static final String logIdentifier = "FileUtils";

	public static final int MEDIA_TYPE_IMAGE = 1;

	/**
	 * Create a File for saving an image or video
	 */
	public static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			// handle differently
			return null;
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

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		return mediaFile;
	}

	public static boolean deleteFile(String picturePath) {
		File file = new File(picturePath);
		return file.delete();
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
			Log.w(logIdentifier, "IO expection occured while writing to file " + file);
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

	public static boolean createMetadataJsonFile(Photo photo) {
		Gson gson = new Gson();
		String json = gson.toJson(photo);
		// FIXME path should be obtained from Photo object
		return writeToFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), photo.label + ".json"), json.getBytes());
	}
}
