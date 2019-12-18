package com.tst.test2;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import sun.jvm.hotspot.debugger.Page;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public abstract class PdfReport {

    private PdfWriter pdfWriter;
    private Document document;
    private ByteArrayOutputStream baos;
    private final Rectangle size;

    public PdfReport(Rectangle size) {
        this.size = size;
    }

    public byte[] generate() {
        initReport();
        reportHeader();
        reportBody();
        reportFooter();
        document.close();
        pdfWriter.close();
        return baos.toByteArray();
    }

    private void initReport() {
        baos = new ByteArrayOutputStream();
        document = new Document(size);
        try {
            pdfWriter = PdfWriter.getInstance(document, baos);
            document.open();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    protected abstract void reportHeader();

    protected abstract void reportBody();

    protected abstract void reportFooter();


    protected PdfWriter getPdfWriter() {
        return pdfWriter;
    }

    protected Document getDocument() {
        return document;
    }

    protected Paragraph getDefaultHeader(String reportTitle) {
        try {
            SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat date = new SimpleDateFormat("MM/dd/YYYY");
            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);
            header.add(new Paragraph());

            Image image = Image.getInstance("logo.jpg");
            image.scaleAbsolute(100f, 80f);
            image.setAbsolutePosition(size.getLeft(30), size.getTop(100));
            header.add(image);

            Paragraph runDate = new Paragraph("Run Date : " + date.format(new Date()), new Font(Font.FontFamily.HELVETICA, 8));
            runDate.setAlignment(Element.ALIGN_RIGHT);
            header.add(runDate);

            Paragraph runTime = new Paragraph("Run Time: " + time.format(new Date()), new Font(Font.FontFamily.HELVETICA, 8));
            runTime.setAlignment(Element.ALIGN_RIGHT);
            header.add(runTime);

            Paragraph companyName = new Paragraph("PATHLAND WATER SERVICES\nPathland Address\nPathland Tin Number\n Pathland Contact Number");
            companyName.setAlignment(Element.ALIGN_CENTER);
            header.add(companyName);
            header.add(new Paragraph());
            header.add(new Paragraph());
            Paragraph pageTitle1 = new Paragraph(reportTitle);
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
