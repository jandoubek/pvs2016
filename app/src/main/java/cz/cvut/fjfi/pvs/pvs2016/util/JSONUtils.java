package cz.cvut.fjfi.pvs.pvs2016.util;

import static cz.cvut.fjfi.pvs.pvs2016.util.FileUtils.getMetadataFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created by fnj on 6.12.16.
 */

public class JSONUtils {
	public static ArrayList<Photo> getPhotoList(ArrayList<Photo> photoList) {
		File metadataFiles[] = getMetadataFiles();
		Gson gson = new Gson();
		for (File metadataFile : metadataFiles) {
			try {
				Photo photo = gson.fromJson(new FileReader(metadataFile), Photo.class);
				photoList.add(photo);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return photoList;
	}

	public static ArrayList<Photo> getPhotoList() {
		return getPhotoList(new ArrayList<Photo>());
	}

	public static boolean createMetadataFile(Photo photo) {
		Gson gson = new Gson();
		String json = gson.toJson(photo);
		File outputFile = null;
		try {
			outputFile = FileUtils.getOutputMetadataFile(photo.path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FileUtils.writeToFile(outputFile, json.getBytes());
	}
}
