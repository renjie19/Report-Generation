package com.tst.reports;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public abstract class PdfReport {

    private PdfWriter pdfWriter;
    private Document document;
    private ByteArrayOutputStream baos;

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
        document = new Document();
        try {
            pdfWriter = PdfWriter.getInstance(document, baos);
            document.open();
        } catch (DocumentException e) {
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
}
