package cz.cvut.fjfi.pvs.pvs2016.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static cz.cvut.fjfi.pvs.pvs2016.util.FileUtils.getMetadataFiles;

/**
 * Created by fnj on 6.12.16.
 */

public class JSONUtils {
    private static JSONArray getMetadataRootArray(File jsonFile) {
        JSONParser parser = new JSONParser();
        JSONArray rootArray = null;
        try {
            rootArray = (JSONArray) parser.parse(new FileReader(jsonFile));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return rootArray;
    }

    public static ArrayList<Photo> getPhotoList(ArrayList<Photo> photoList) {
        File metadataFiles[] = getMetadataFiles();
        for (File metadataFile : metadataFiles) {
            JSONArray jsonArray = getMetadataRootArray(metadataFile);
            Iterator<JSONObject> objectIterator = jsonArray.iterator();
            while (objectIterator.hasNext()) photoList.add(new Photo(objectIterator.next()));
        }
        return photoList;
    }

    public static ArrayList<Photo> getPhotoList() {
        return getPhotoList(new ArrayList<Photo>());
    }
}
