package cz.cvut.fjfi.pvs.pvs2016.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import android.util.Log;
import cz.cvut.fjfi.pvs.pvs2016.model.Photo;

public class PDFGenerator {

	/**
	 * Create PDF file from images represented by passed {@code photoList}.
	 *
	 * @param filename absolute path to resulting PDF file (e.g. "storage/emulated/0/LectureNotes/test.pdf")
	 */
	public static void generatePdf(List<Photo> photoList, String filename) throws IOException {
		if (photoList == null || photoList.isEmpty()) {
			return;
		}
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(filename));
			doc.setMargins(0, 0, 0, 0);
			doc.open();
			for (Photo p : photoList) {
				Image img = Image.getInstance(p.getPath());
				float scale = ((doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin()) / img.getWidth()) * 100;
				img.scalePercent(scale);
				img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
				doc.add(img);
			}
		} catch (DocumentException e) {
			Log.i(PDFGenerator.class.getSimpleName(), e.getMessage());
		} catch (IOException e) {
			throw new IOException("Exception during loading images for pdf file.", e);
		} finally {
			doc.close();
		}
	}

}