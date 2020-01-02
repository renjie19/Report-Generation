package com.tst.reports;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
public final class ReportUtil {

    public static final PdfPTable getPdfTable(int tableColumns) {
        PdfPTable table = new PdfPTable(tableColumns);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(20f);
        table.setWidthPercentage(100);
        table.setHeaderRows(1);
        return table;
    }

    public static void addTableHeaders(String[] headers, PdfPTable pdfPTable) {
        for (String header : headers) {
            pdfPTable.addCell(header);
        }
    }
}
