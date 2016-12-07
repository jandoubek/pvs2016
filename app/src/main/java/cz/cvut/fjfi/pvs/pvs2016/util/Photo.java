package cz.cvut.fjfi.pvs.pvs2016.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.Iterator;

public class Photo {
    public static class Series {
        public String name;
        public int index;

        public Series(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Series))
                return false;
            if (obj == this)
                return true;
            Series rhs = (Series) obj;
            return (rhs.index == this.index && rhs.name.equals(this.name));
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + name.hashCode();
            result = 31 * result + index;
            return result;
        }

    }

    public String id;
    public String path;
    public HashSet<String> tags;
    public HashSet<Series> series;
    public String timestamp;

    public Photo(JSONObject jsonObject) {
        super();
        id = (String) jsonObject.get("id");
        path = (String) jsonObject.get("path");
        tags = new HashSet<>();
        JSONArray tagsArray = (JSONArray) jsonObject.get("tags");
        Iterator<String> tagsIterator = tagsArray.iterator();
        while (tagsIterator.hasNext()) tags.add(tagsIterator.next());
        series = new HashSet<>();
        JSONArray seriesArray = (JSONArray) jsonObject.get("series");
        Iterator<JSONObject> seriesIterator = seriesArray.iterator();
        while (seriesIterator.hasNext()) {
            JSONObject seriesObject = seriesIterator.next();
            String name = (String) seriesObject.get("name");
            long longIndex = (long) seriesObject.get("index");
            int index = (int) longIndex;
            series.add(new Series(name, index));
        }
        timestamp = (String) jsonObject.get("timestamp");
    }
}
