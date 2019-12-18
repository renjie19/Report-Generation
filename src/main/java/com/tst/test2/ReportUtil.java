package com.tst.test2;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import sun.font.FontFamily;

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
            pdfPTable.addCell(new Phrase(header));
        }
    }

    public static void setColumnWidths(String[] headers, PdfPTable pdfPTable) {
        try {
            int[] widths = new int[headers.length];
            for (int x = 0; x < headers.length; x++) {
                widths[x] = headers[x].length();
            }
            pdfPTable.setWidths(widths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
