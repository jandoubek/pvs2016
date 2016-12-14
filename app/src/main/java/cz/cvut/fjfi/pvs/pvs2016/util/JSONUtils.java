package cz.cvut.fjfi.pvs.pvs2016.util;

import static cz.cvut.fjfi.pvs.pvs2016.util.FileUtils.getMetadataFiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class JSONUtils {

	public static List<Photo> getPhotoList() throws IOException {
		List<Photo> photoList = new ArrayList<>();
		List<File> metadataFiles = getMetadataFiles();
		Gson gson = new Gson();
		for (File metadataFile : metadataFiles) {
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(metadataFile);
				Photo photo = gson.fromJson(fileReader, Photo.class);
				photoList.add(photo);
			} finally {
				if (fileReader != null) {
					fileReader.close();
				}
			}
		}
		return photoList;
	}

	public static boolean createMetadataFile(Photo photo) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(photo);
		File outputFile = FileUtils.getOutputMetadataFile(photo.getPath());
		return FileUtils.writeToFile(outputFile, json.getBytes());
	}
}
