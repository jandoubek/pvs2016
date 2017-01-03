package cz.cvut.fjfi.pvs.pvs2016.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtils {

	/**
	 * @param columns number of columns on screen
	 * @param margin  of every object within grid in dp
	 */
	public static int computeWidthForColumnsWithMargin(Context context, int columns, float margin) {
		float marginInPixels = margin / context.getResources().getDisplayMetrics().density;
		int screenWidth = getScreenWidth(context);
		return (int) (screenWidth / columns - 2 * marginInPixels);
	}

	/**
	 * @param columns number of columns on screen
	 * @param margin  of every object within grid in dp
	 */
	public static int computeHeightForColumnsWithMargin(Context context, int columns, float margin, String filePath) {
		int width = computeWidthForColumnsWithMargin(context, columns, margin);
		float imageRatio = getImageRatio(filePath);
//		bitmap decoder returning width and height in different order, does not look, if the image is turned aroud... need to solve
//		imageRatio = 1/imageRatio;
		return (int) (width / imageRatio);
	}

	public static float getImageRatio(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		return ((float) width) / height;
	}

	public static double getScreenRatio(Context context) {
		Point screenDimensions = getScreenDimensions(context);
		return screenDimensions.x / screenDimensions.y;
	}

	public static int getScreenWidth(Context context) {
		return (int) (getScreenDimensions(context).x * context.getResources().getDisplayMetrics().density);
	}

	public static int getScreenHeight(Context context) {
		return (int) (getScreenDimensions(context).y * context.getResources().getDisplayMetrics().density);
	}

	private static Point getScreenDimensions(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}
}
