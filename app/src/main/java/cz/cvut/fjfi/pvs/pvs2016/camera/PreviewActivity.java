package cz.cvut.fjfi.pvs.pvs2016.camera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import cz.cvut.fjfi.pvs.pvs2016.R;

public class PreviewActivity extends Activity {

    private final String LOG_DESC = "PreviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_DESC, "I am here!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        String picturePath = getIntent().getStringExtra("picturePath");
        Log.d(LOG_DESC, "Path of picture to preview: " + picturePath);

        ImageView viewById = (ImageView) findViewById(R.id.imageView);
//        TODO set image correctly and move dialog from CameraAcitvity here
//        viewById.setImageURI(URI.fromFile(new File(picturePath)));
        this.finish();
    }
}
