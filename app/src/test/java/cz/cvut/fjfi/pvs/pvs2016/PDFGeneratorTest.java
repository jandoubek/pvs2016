package cz.cvut.fjfi.pvs.pvs2016;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import cz.cvut.fjfi.pvs.pvs2016.model.Photo;
import cz.cvut.fjfi.pvs.pvs2016.util.PDFGenerator;
import junit.framework.Assert;

public class PDFGeneratorTest extends ADataWriteTest {

	@Test
	public void generateFromEmptyList() throws IOException {
		String pdfFilePath = mediaStorageDir.getAbsolutePath() + File.separator + "test.pdf";
		PDFGenerator.generatePdf(Collections.<Photo>emptyList(), pdfFilePath);
		File pdfFile = new File(pdfFilePath);
		Assert.assertFalse(pdfFile.exists());
	}
}
