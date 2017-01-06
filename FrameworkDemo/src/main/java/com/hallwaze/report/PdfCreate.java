package com.hallwaze.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.testng.ITestResult;

import com.hallwaze.utilities.FConstant;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreate {
	static Document document;
	String fileName = "automation.pdf";
	PdfWriter writer;

	public void createPDF() {
		document = new Document();
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.open();
		try {
			Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

			Chunk chunk = new Chunk("Automation Report", font1);
			Paragraph para1 = new Paragraph(chunk);
			para1.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(para1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addImage(String imagePath) {
		Image image1 = null;
		try {
			image1 = Image.getInstance(imagePath);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			document.add(image1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeDocument() {
		document.close();
	}

	public void createStatusTable(Integer colNum, Object obj) {
		PdfPTable table = new PdfPTable(colNum);
		table.setTotalWidth(144f);
		table.setLockedWidth(true);

		table.setWidthPercentage(40);
		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		table.getDefaultCell().setBorder(0);

		if (obj instanceof LinkedHashMap<?, ?>) {
			LinkedHashMap<String, String> lhmap = (LinkedHashMap<String, String>) obj;
			table.setHorizontalAlignment(Element.ALIGN_LEFT);

			for (Map.Entry<String, String> m : lhmap.entrySet()) {
				if (m.getKey().equalsIgnoreCase("total") || m.getKey().equalsIgnoreCase("passed")
						|| m.getKey().equalsIgnoreCase("failed") || m.getKey().equalsIgnoreCase("skipped")) {
					table.addCell(new Paragraph(m.getKey()));
					table.addCell(new Paragraph(m.getValue()));
				}

			}
		}
		table.writeSelectedRows(0, -1, document.left(), document.top() - 50, writer.getDirectContentUnder());
		table = new PdfPTable(2);
		table.setTotalWidth(290f);
		table.setLockedWidth(true);

		table.setWidthPercentage(100);
		table.setSpacingBefore(1f);
		table.setSpacingAfter(1f);
		table.getDefaultCell().setBorder(0);
		if (obj instanceof LinkedHashMap<?, ?>) {
			LinkedHashMap<String, String> lhmap = (LinkedHashMap<String, String>) obj;
			table.setHorizontalAlignment(Element.ALIGN_LEFT);

			for (Map.Entry<String, String> m : lhmap.entrySet()) {
				if (!(m.getKey().equalsIgnoreCase("total") || m.getKey().equalsIgnoreCase("passed")
						|| m.getKey().equalsIgnoreCase("failed") || m.getKey().equalsIgnoreCase("skipped"))) {
					table.addCell(new Paragraph(m.getKey()));
					table.addCell(new Paragraph(m.getValue()));
				}

			}
		}

		table.writeSelectedRows(0, -1, document.left() + 200, document.top() - 50, writer.getDirectContent());

		table = new PdfPTable(3);

		try {

			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writePassData(Set<ITestResult> testsPassed) {
		try {
			document.add(Chunk.NEWLINE);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

		Chunk chunk = new Chunk("Passed Test Case", font1);

		PdfPCell cell = new PdfPCell(new Phrase(chunk));
		cell.setHorizontalAlignment(1);
		cell.setBackgroundColor(BaseColor.GREEN);

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.addCell(cell);

		for (ITestResult testResult : testsPassed) {
			table.addCell(testResult.getName());
		}

		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeFailData(Set<ITestResult> testsFailed) {
		try {
			document.add(Chunk.NEWLINE);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

		Chunk chunk = new Chunk("Failed Test Case", font1);

		PdfPCell cell = new PdfPCell(new Phrase(chunk));
		cell.setHorizontalAlignment(1);
		cell.setBackgroundColor(BaseColor.RED);

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.addCell(cell);
		String str1 = " ";

		for (ITestResult testResult : testsFailed) {
			// table.addCell(testResult.getName());
			cell = new PdfPCell(new Phrase(testResult.getName()));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			if (testResult.getThrowable().toString().length() > 0) {
				table.addCell(testResult.getThrowable().toString());
			}

			File f = new File(FConstant.ScreenShotPath+"//" + testResult.getName() + ".png");
			if (f.exists()) {

				Image img = null;
				try {
					img = Image.getInstance(f.getAbsolutePath());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PdfPCell cell1 = new PdfPCell(img, true);
				table.addCell(cell1);

			} else {
				System.out.println("fail");
			}
		}

		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void writeSkipData(Set<ITestResult> testsFailed) {
		try {
			document.add(Chunk.NEWLINE);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

		Chunk chunk = new Chunk("Skip Test Case", font1);

		PdfPCell cell = new PdfPCell(new Phrase(chunk));
		cell.setHorizontalAlignment(1);
		cell.setBackgroundColor(BaseColor.DARK_GRAY);

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.addCell(cell);
		String str1 = " ";

		for (ITestResult testResult : testsFailed) {
			cell = new PdfPCell(new Phrase(testResult.getName()));
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			if (testResult.getThrowable().toString().length() > 0) {
				table.addCell(testResult.getThrowable().toString());
			}
		}

		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

}
