package cz.cvut.fjfi.pvs.pvs2016;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TableActivity extends AppCompatActivity {

    private Photo[] testPhotos = new Photo[]{new Photo(R.mipmap.ic_launcher, "label1", new String[]{"tag1A", "tag1B"}),
            new Photo(R.mipmap.ic_launcher, "label2", new String[]{"tag2A", "tag2B", "tag2C"})};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initTable();
    }

    private TableLayout initTable() {
        TableLayout tl = (TableLayout) findViewById(R.id.table_layout);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
        for (Photo photo : testPhotos) {
            TableRow tr = new TableRow(this);

            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(R.drawable.cell_shape);
            iv.setLayoutParams(params);
            Drawable d = ContextCompat.getDrawable(getApplicationContext(), photo.resource_locator);
            iv.setImageDrawable(d);
            tr.addView(iv);

            TableCellTextView label = new TableCellTextView(this);
            label.setText(photo.label);
            tr.addView(label);

            TableCellTextView tags = new TableCellTextView(this);
            tags.setText(TextUtils.join(", ", photo.tags));
            tr.addView(tags);

            tl.addView(tr);
        }
        return tl;
    }

}
