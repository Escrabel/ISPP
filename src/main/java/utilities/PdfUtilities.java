package utilities;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Actor;
import domain.Message;
import domain.MessageFolder;
import domain.Tattoo;

public class PdfUtilities {
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public static ByteArrayOutputStream generatePdfCustomer(Actor a) throws DocumentException {

		// Nuevo Documento
		Document document = new Document();
		// Tipo de datos de salida
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, bout);
		document.open();

		// Título del documento y capítulo
		Anchor anchor = new Anchor("My data", catFont);
		anchor.setName("First Chapter");
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);
		Paragraph subPara = new Paragraph(" ");

		// Información personal
		subPara = new Paragraph("Personal information", subFont);
		addEmptyLine(subPara, 1);
		Section subCatPart = catPart.addSection(subPara);
		addPersonalInformation(subCatPart, a);
		// Messages
		subPara = new Paragraph("Messages", subFont);
		subCatPart = catPart.addSection(subPara);
		addEmptyLine(subPara, 2);
		addBoxesAndMessages(subCatPart, a.getMessageFolders());

		document.add(catPart);
		addMetaData(document);

		document.close();

		return bout;
	}

	public static ByteArrayOutputStream generatePdfTattooist(Actor a, Collection<Tattoo> tattoos)
			throws DocumentException {

		// Nuevo Documento
		Document document = new Document();
		// Tipo de datos de salida
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, bout);
		document.open();

		// Título del documento y capítulo
		Anchor anchor = new Anchor("My data", catFont);
		anchor.setName("First Chapter");
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);
		Paragraph subPara = new Paragraph(" ");

		// Información personal
		subPara = new Paragraph("Personal information", subFont);
		addEmptyLine(subPara, 1);
		Section subCatPart = catPart.addSection(subPara);
		addPersonalInformation(subCatPart, a);
		// Messages
		subPara = new Paragraph("Messages", subFont);
		subCatPart = catPart.addSection(subPara);
		addEmptyLine(subPara, 2);
		addBoxesAndMessages(subCatPart, a.getMessageFolders());
		// Tattoo
		subPara = new Paragraph("Tattoos", subFont);
		subCatPart = catPart.addSection(subPara);
		addEmptyLine(subPara, 2);
		addTattoos(subCatPart, tattoos);

		document.add(catPart);
		addMetaData(document);

		document.close();

		return bout;
	}

	private static void addTattoos(Section subCatPart, Collection<Tattoo> tattoos) {
		PdfPTable table = new PdfPTable(5);

		PdfPCell c1 = new PdfPCell(new Phrase("Ticker"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Name"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Description"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Price"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Date Upload"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setHeaderRows(1);

		for (Tattoo p : tattoos) {
			table.addCell(p.getTicker());
			table.addCell(p.getName());
			table.addCell(p.getDescription());
			table.addCell(Double.toString(p.getPrice()));
			table.addCell(p.getDateUpload().toString());

		}

		table.setWidthPercentage(100);

		subCatPart.add(table);

	}

	private static void addPersonalInformation(Section subCatPart, Actor a) {
		subCatPart.add(new Paragraph(" "));
		subCatPart.add(new Paragraph("Name: " + a.getName()));
		subCatPart.add(new Paragraph("Surname: " + a.getSurName()));
		subCatPart.add(new Paragraph("Postal Address: " + a.getPostalAddress()));
		subCatPart.add(new Paragraph("Email: " + a.getEmail()));
		subCatPart.add(new Paragraph("Phone number: " + a.getPhone()));
		subCatPart.add(new Paragraph("Country: " + a.getPais()));
		subCatPart.add(new Paragraph("City: " + a.getCiudad()));

	}

	private static void addBoxesAndMessages(Section subCatPart, Collection<MessageFolder> boxes)
			throws BadElementException {
		PdfPTable table;
		for (MessageFolder b : boxes) {
			subCatPart.add(new Paragraph("Name Box: " + b.getName()));
			subCatPart.add(new Paragraph(" "));

			table = new PdfPTable(5);
			PdfPCell c1 = new PdfPCell(new Phrase("Date"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Subject"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Body"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Sender"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Receiver"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			for (Message m : b.getMessages()) {

				table.addCell(m.getDate().toString());
				table.addCell(m.getSubject());
				table.addCell(m.getBody());
				table.addCell(m.getActorSender().getName() + " " + m.getActorSender().getSurName() + " "
						+ m.getActorSender().getSurName());
				table.addCell(m.getActorReceiver().getName() + " " + m.getActorReceiver().getSurName() + " "
						+ m.getActorSender().getSurName());

				table.setHeaderRows(1);
			}
			table.setWidthPercentage(100);

			subCatPart.add(table);
			subCatPart.add(new Paragraph(" "));

		}

	}

	private static void addMetaData(Document document) {
		document.addTitle("My Data");
		document.addAuthor("Acme-Tattoo");
		document.addCreator("Acme-Tattoo");
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
