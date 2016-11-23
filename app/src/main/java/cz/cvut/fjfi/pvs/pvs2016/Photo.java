package cz.cvut.fjfi.pvs.pvs2016;

/**
 * Created by fnj on 14.11.16.
 */

public class Photo {
    public int resource_locator;
    public String label;
    public String tags[];

    public Photo(int resource_locator, String label, String tags[]) {
        super();
        this.resource_locator = resource_locator;
        this.label = label;
        this.tags = tags;
    }
}
